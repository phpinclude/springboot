package kr.pe.withwind.common;

import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.pe.withwind.exception.WindCommonException;
import kr.pe.withwind.security.WindSecurity;

/**
 * PropertiesConfiguration 를 랩핑하여 사이트에 맞도록 사용한다.
 * Spring Bean에 singleton으로 등록 하고 필요한 곳에서 사용토록 한다.
 * 프로그램에서 사용하는 기본 설정의 키값을 상수로 등록되어있다.
 * @author smpark
 *
 */
@Component
public class WindConfigure {
	
	private static final Logger logger = LoggerFactory.getLogger(WindConfigure.class);
	
	/** Property key : 데이터베이스 uri */
	public static final String SYSTEM_DB_URI = "system.db.uri";
	/** Property key : 데이터베이스 사용자 이름 */
	public static final String SYSTEM_DB_ID = "system.db.username";
	/** Property key : 데이터베이스 사용자 패스워드 */
	public static final String SYSTEM_DB_PW = "system.db.password";
	/** Property key : 메일전송(SMTP) 아이디 */
	public static final String SYSTEM_MAIL_ID = "system.mail.id";
	/** Property key : 메일전송(SMTP) 패스워드 */
	public static final String SYSTEM_MAIL_PW = "system.mail.pw";
	/** Property key : 메일전송(SMTP) 접속포트 */
	public static final String SYSTEM_MAIL_PORT = "system.mail.port";
	/** Property key : 메일전송(SMTP) 접속 호스트명 */
	public static final String SYSTEM_MAIL_HOST = "system.mail.host";
	/** Property key : 메일전송시 sender mail 주소 */
	public static final String SYSTEM_MAIL_SENDER = "system.mail.sender";
	/** Property key : 사이트 첨부파일 디렉토리 */
	public static final String ATTACH_FILE_DIR = "attach.file.dir";
	/** Property key : 사이트 테마파일 디렉토리 */
	public static final String THEME_FILE_DIR = "theme.file.dir";
	/** Property key : 사이트 관리자 시퀀스 번호 */
	public static final String USER_ADMIN_SEQ = "user.admin.seq"; // 기본 1
	/** Property key : 사이트 방문자 시퀀스 번호 */
	public static final String USER_GUEST_SEQ = "user.guest.seq"; // 기본 2
	/** Property key : 사이트 회원 기본 등급 */
	public static final String USER_LEVEL_DEFAULT = "user.level.default"; // 기본 100
	/** Property key : 사이트 기본 타이틀 */
	public static final String WEB_TITLE = "view.title";
	/** Property key : 사이트 메인 메시지 */
	public static final String MAIN_MESSAGE = "view.main.message";
	/** Property key : Multicast IP */
	public static final String MULTICAST_IP = "multicast.ip";
	/** Property key : Multicast PORT */
	public static final String MULTICAST_PORT = "multicast.port";
	
	
	/** Property key : 공공 API 서비스키 */
	public static final String PROP_NM_SERVICE_KEY = "api.open.service.key";
	
	public final String[] DEFAULT_KEYS = {
			SYSTEM_DB_URI, SYSTEM_DB_ID, SYSTEM_DB_PW ,SYSTEM_MAIL_ID
			, SYSTEM_MAIL_PW, SYSTEM_MAIL_PORT, SYSTEM_MAIL_HOST, ATTACH_FILE_DIR
			, THEME_FILE_DIR, USER_ADMIN_SEQ, USER_GUEST_SEQ, USER_LEVEL_DEFAULT
			, WEB_TITLE, MAIN_MESSAGE, MULTICAST_IP, MULTICAST_PORT
	};
	
	private PropertiesConfiguration pc;
	
	public WindConfigure() throws ConfigurationException {
	    
	    String fileNm = System.getProperty("siteConf.properties");
	    
	    if (StringUtils.isEmpty(fileNm)) {
	        fileNm = "site.properties"; 
	    }
	    
		pc = new PropertiesConfiguration(fileNm);
		pc.setAutoSave(true);
	}

	public void setProperty(String key, String value) throws WindCommonException {
		
		if (key.startsWith("system.")) {
			try {
				value = "[encrypt]" + WindSecurity.aesEncrypt(value);
			} catch (Exception e) {
				throw new WindCommonException("환경변수 암호화 오류", e);
			}
		}
		
		pc.setProperty(key, value);
	}

	public void clearProperty(String key) throws WindCommonException {
		if (key.startsWith("system.")) throw new WindCommonException("환경변수 암호화 오류", new Exception(key + "프로퍼티는 삭제불가합니다."));
		pc.clearProperty(key);
	}

	public Iterator<String> getKeys() {
		return pc.getKeys();
	}

	public String getString(String keyName) throws WindCommonException {
		
		String reValue = pc.getString(keyName);
		
		if (keyName.startsWith("system.")) {
			if (!reValue.startsWith("[encrypt]")) {
				try {
					String aesValue = "[encrypt]" + WindSecurity.aesEncrypt(reValue);
					pc.setProperty(keyName, aesValue);
				}catch(Exception e) {
					throw new WindCommonException("환경변수 암호화 오류", e);
				}
			}
			reValue = pc.getString(keyName);
		}
		
		return reValue;
	}
	
	public String getStrWithAes(String keyName) throws WindCommonException {
		String reValue = pc.getString(keyName);
		try {
			if (reValue.startsWith("[encrypt]")) {
				reValue = WindSecurity.aesDecrypt(reValue.substring(9));
			}else {
				String aesValue = "[encrypt]" + WindSecurity.aesEncrypt(reValue);
				pc.setProperty(keyName, aesValue);
			}
		} catch (Exception e) {
			throw new WindCommonException("환경변수 복호화 오류", e);
		}
		return reValue;
	}
	
	public String getString(String keyName, String defaultValue) {
		if (StringUtils.isEmpty(pc.getString(keyName))) {
			pc.setProperty(keyName,defaultValue);
		}
		return pc.getString(keyName, defaultValue);
	}
	
	public int getNonUserSeq() {
		int reValue = 2;
		
		try {
			reValue = pc.getInt(WindConfigure.USER_GUEST_SEQ,2);
		}catch(Exception e) {
			logger.error(WindConfigure.USER_GUEST_SEQ + " 값이 잘못되어 있습니다.", e);
		}
		return reValue;
	}
	
	public int getAdminUserSeq() {
		int reValue = 1;
		
		try {
			reValue = pc.getInt(WindConfigure.USER_ADMIN_SEQ,1);
		}catch(Exception e) {
			logger.error(WindConfigure.USER_ADMIN_SEQ + " 값이 잘못되어 있습니다.", e);
		}
		return reValue;
	}

	public int getDefaultUserLevel() {
		int reValue = 100;
		
		try {
			reValue = pc.getInt(WindConfigure.USER_LEVEL_DEFAULT,100);
		}catch(Exception e) {
			logger.error(WindConfigure.USER_LEVEL_DEFAULT + " 값이 잘못되어 있습니다.", e);
		}
		return reValue;
	}
}
