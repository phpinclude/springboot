package kr.pe.withwind.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 멀티 캐스트로 데이터싱크를 맞추기 위해서 만들어졌다.
 * 
 * @author smpark
 *
 */
public class MultiCastSender{
	
	private static final Logger logger = LoggerFactory.getLogger(MultiCastSender.class);
	
	private WindConfigure windConfigure;
	private String sendId;
	private String multiCastIp;
	private int multiCastPort;
	private MulticastSocket ms;
	private InetAddress ia;
	
	public MultiCastSender(WindConfigure configure) {
		this.windConfigure = configure;
		this.sendId = String.valueOf(System.currentTimeMillis());
		this.multiCastIp = windConfigure.getString(WindConfigure.MULTICAST_IP, "224.0.0.1");
		this.multiCastPort = Integer.parseInt(windConfigure.getString(WindConfigure.MULTICAST_PORT, "22400"));
		
		try {
			ms = new MulticastSocket();
			ia = InetAddress.getByName(multiCastIp);
		}catch (IOException e) {
			logger.error("multicast create falue !!");
		}
	}
	
	protected String getSendId() {
		return this.sendId;
	}
	
	protected String getMultiCastIp() {
		return this.multiCastIp;
	}
	
	protected int getMultiCastPort() {
		return this.multiCastPort;
	}

	public void sendData(String msg) {
		try {
			String sendMessage = sendId + msg;
			DatagramPacket data = new DatagramPacket(sendMessage.getBytes(), sendMessage.getBytes().length, ia, multiCastPort);
			ms.send(data);
			logger.debug("multicast message send ok ["+sendMessage+"]");
		}catch (IOException e) {
			logger.error("multicast message send falue!! ["+msg+"]");
		}
	}
	
	public void destroy() {
		if (ms != null) ms.close();
	}
}