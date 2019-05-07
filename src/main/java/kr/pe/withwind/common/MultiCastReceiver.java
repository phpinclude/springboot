package kr.pe.withwind.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiCastReceiver extends Thread{
	
	private static final Logger logger = LoggerFactory.getLogger(MultiCastReceiver.class);
	
	private MultiCastSender mSender;
	private boolean isRun;
	private ReceiverJob[] jobArr;
	private MulticastSocket ms = null;
	
	public MultiCastReceiver(MultiCastSender mSender) {
		this.mSender = mSender;
		isRun = true;
		start();
	}

	@Override
	public void run() {
		
		try {
			ms = new MulticastSocket(mSender.getMultiCastPort());
			ms.joinGroup(InetAddress.getByName(mSender.getMultiCastIp()));
		}catch(Exception e) {return;}
		
		try {
			while (isRun) {
				byte[] by = new byte[65508];
				DatagramPacket data = new DatagramPacket(by, by.length);
				ms.receive(data);

				InetAddress ia = data.getAddress(); //
				String str = new String(data.getData()).trim();
				logger.debug(ia.getHostAddress() + " data receive ==> " + str);
				
				if (str.startsWith(mSender.getSendId())) {
					logger.debug("It's my send data!!");
				}else {
					boradCast(str.substring(13));
				}
			}
			
		}catch(IOException e) {
			if (isRun) logger.error("receiver stop error !!",e);
			else
				logger.debug("receiver stop !!");
		}finally {
			if (ms != null) ms.close();
		}
	}
	
	private void boradCast(String substring) {
		for (ReceiverJob job : jobArr) {
			if (job.getClass().getName().equals(substring)) {
				
				logger.debug(job.getClass().getName() + " --> receiverJobExcute call !!");
				job.receiverJobExcute();
			}
		}
	}

	public void destroy() {
		
		try {
			if (ms != null) ms.close();
		}catch(Exception e) {
			logger.debug("bean 자원 해제 중 에러", e);
		}
		
		isRun = false;
		if (!isInterrupted()) interrupt();
	}
	
	public void setManager(ReceiverJob[] jobArr) {
		this.jobArr = jobArr;
	}
	
	public interface ReceiverJob {
		public void receiverJobExcute();
	}
}