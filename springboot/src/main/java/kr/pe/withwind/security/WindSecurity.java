package kr.pe.withwind.security;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 해쉬 및 암호화 함수를 모아둔 Utils성클래스
 * @author smpark
 *
 */
public class WindSecurity{

	private static String key = "82b8dfd75345af249fe7da1a989ef8f17818c0";
	private static String iv = "";
    private static Key keySpec;
    
    /** 
     * AES256 암호화 keySpec 생성
     * @throws UnsupportedEncodingException
     */
    private static void initKeySpec() throws UnsupportedEncodingException {
    	iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if(len > keyBytes.length){
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        keySpec = new SecretKeySpec(keyBytes, "AES");
    }
    
    /**
     * AES256으로 암호화 한다.
     * @param str 암호화 할 문자열
     * @return
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String aesEncrypt(String str) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
    	
    	if (keySpec == null) initKeySpec();
    	
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));
        return enStr;
    }
    

    /**
     * AES256으로 암호화된 문자열을 복호화한다.
     * @param str 암호화된 문자열
     * @return 복호화 한 문자열
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws UnsupportedEncodingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String aesDecrypt(String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    	
    	if (keySpec == null) initKeySpec();
    	
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), "UTF-8");
    }
    
	/**
	 * 주어진 문자열을 SHA-256 해쉬 문자열로 반환 
	 * @param rawStr
	 * @return 해쉬된 문자열 Hex String임
	 */
	public static String encode(String rawStr){
		return WindSecurity.encode(rawStr,"SHA-256");
	}
	
	/**
	 * 주어진 문자열을 주어진 알고리즘으로 해쉬한 문자열을 반환 
	 * @param rawStr 대상 문자열
	 * @param alg 대상 알고리즘
	 * @return 해쉬된 문자열 Hex String임
	 */
	public static String encode(String rawStr, String alg){
		
        try{
             MessageDigest digest = MessageDigest.getInstance(alg);
            byte[] hash = digest.digest(rawStr.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            return null;
        }
	}
	
	/**
	 * MD5 해쉬코드 반환
	 * @param str
	 * @return
	 */
	public static String getMD5(String str){
		String MD5 = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(str.getBytes()); 
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			MD5 = null; 
		}
		return MD5;
	}
	
	/**
	 * MD5 해쉬코드 반환
	 * @param bytes
	 * @return
	 */
	public static String getMD5(byte[] bytes){
		String MD5 = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(bytes); 
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			MD5 = null; 
		}
		return MD5;
	}

	
	public static void main(String[] arg) throws Exception {
//	    #system.db.username = [encrypt]5s4Wkh7QbPiplu8uO3Mw9w==
//	            #system.db.password = [encrypt]5s4Wkh7QbPiplu8uO3Mw9w==
		System.out.println(WindSecurity.aesDecrypt("5s4Wkh7QbPiplu8uO3Mw9w=="));
	}
}
