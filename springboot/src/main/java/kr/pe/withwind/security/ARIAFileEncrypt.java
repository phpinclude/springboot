package kr.pe.withwind.security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ARIAFileEncrypt {

	private static final String keyStr = "82b8dfd75345af249fe7da1a989ef8f1";
	
	/**
	 * 16 바이트 이하 파일은 암호화 안됨
	 * @param sourceFileName
	 * @param targetFileName
	 * @throws Exception
	 */
	public static void doEncrypt(String sourceFileName, String targetFileName) throws Exception {
		try {
			ARIAEngine aEngine = new ARIAEngine(keyStr.length() * 8);
			aEngine.setKey(keyStr.getBytes());
			
			InputStream input = null;
			OutputStream output = null;
			try {
				input = new BufferedInputStream(new FileInputStream(sourceFileName));
				output = new BufferedOutputStream(new FileOutputStream(targetFileName));
				
				byte[] buffer = new byte[16];
				int readByte = 0;
				while ((readByte = input.read(buffer)) != -1) {
					if (readByte == 16) {
						output.write(aEngine.encrypt(buffer, 0));
					}else {
						output.write(buffer,0,readByte);
					}
				}
			} finally {
				if (output != null) {
					try { output.close(); } catch(IOException ie) {}
				}
				if (input != null) {
					try { input.close(); } catch(IOException ie) {}
				}
			}
			
		}catch(Exception e) {
			throw e;
		}
	}
	
	public static void doDecrypt(String sourceFileName, String targetFileName) throws Exception {
		try {
			ARIAEngine aEngine = new ARIAEngine(keyStr.length() * 8);
			aEngine.setKey(keyStr.getBytes());
			
			InputStream input = null;
			OutputStream output = null;
			try {
				input = new BufferedInputStream(new FileInputStream(sourceFileName));
				output = new BufferedOutputStream(new FileOutputStream(targetFileName));
				
				byte[] buffer = new byte[16];
				int readByte = 0;
				
				while ((readByte = input.read(buffer)) != -1) {
					if (readByte == 16) {
						output.write(aEngine.decrypt(buffer, 0));
					}else {
						output.write(buffer,0,readByte);
					}
				}
			} finally {
				if (output != null) {
					try { output.close(); } catch(IOException ie) {}
				}
				if (input != null) {
					try { input.close(); } catch(IOException ie) {}
				}
			}

			
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 16바이트 이하도 암호화됨 (파일 크기가 16의 나머지 + 8바이트 만큼 커짐)
	 * 16바이트 블럭 단위로 암호화 하고 8바이트 해더를 추가하여 블럭 사이즈와 마지막 블럭의 실제 크기를 기록한다. 
	 * @param sourceFileName
	 * @param targetFileName
	 * @throws Exception
	 */
	public static void doEncryptAdd(String sourceFileName, String targetFileName) throws Exception {
		try {
			ARIAEngine aEngine = new ARIAEngine(keyStr.length() * 8);
			aEngine.setKey(keyStr.getBytes());
			
			InputStream input = null;
			OutputStream output = null;
			try {
				input = new BufferedInputStream(new FileInputStream(sourceFileName));
				output = new BufferedOutputStream(new FileOutputStream(targetFileName));
				
				byte[] buffer = new byte[16];
				
				int inputSize = input.available();
				int blockSize = (int) Math.ceil(inputSize / 16f);
				int restSize = inputSize % 16;
				
				output.write(intToBytes(blockSize));
				output.write(intToBytes(restSize));

				while ((input.read(buffer)) != -1) {
					output.write(aEngine.encrypt(buffer, 0));
				}
			} finally {
				if (output != null) {
					try { output.close(); } catch(IOException ie) {}
				}
				if (input != null) {
					try { input.close(); } catch(IOException ie) {}
				}
			}
			
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 8바이트 해더를 읽어 블럭 사이즈 만큼 복호화하고 마지막 블럭은 실제 파일의 마지막 블럭 크기만큼만 기록한다.
	 * @param sourceFileName
	 * @param targetFileName
	 * @throws Exception
	 */
	public static void doDecryptAdd(String sourceFileName, String targetFileName) throws Exception {
		try {
			ARIAEngine aEngine = new ARIAEngine(keyStr.length() * 8);
			aEngine.setKey(keyStr.getBytes());
			
			InputStream input = null;
			OutputStream output = null;
			try {
				input = new BufferedInputStream(new FileInputStream(sourceFileName));
				output = new BufferedOutputStream(new FileOutputStream(targetFileName));
				
				byte[] sizeBuffer = new byte[4];
				input.read(sizeBuffer);
				int blockSize = bytesToInt(sizeBuffer);
				input.read(sizeBuffer);
				int restSize = bytesToInt(sizeBuffer);
				
				byte[] buffer = new byte[16];
				
				int blockCnt = 0;
				while ((input.read(buffer)) != -1) {
					blockCnt++;
					
					if (blockCnt == blockSize) {
						output.write(aEngine.decrypt(buffer, 0),0,restSize);
					}else {
						output.write(aEngine.decrypt(buffer, 0));
					}
				}
			} finally {
				if (output != null) {
					try { output.close(); } catch(IOException ie) {}
				}
				if (input != null) {
					try { input.close(); } catch(IOException ie) {}
				}
			}

			
		}catch(Exception e) {
			throw e;
		}
	}
	
	public static byte[] intToBytes(int param) {
		byte[] byteArray = new byte[4];
		
		byteArray[0] = (byte)(param >> 24);
		byteArray[1] = (byte)(param >> 16);
		byteArray[2] = (byte)(param >> 8);
		byteArray[3] = (byte)(param);
		
		return byteArray;
	}
	
	public static int bytesToInt(byte[] param) {
		return ((((int)param[0] & 0xff) << 24) |
				(((int)param[1] & 0xff) << 16) |
				(((int)param[2] & 0xff) << 8) |
				(((int)param[3] & 0xff)));
	}
	
	public static void main (String[] arg) throws Exception {
		ARIAFileEncrypt.doEncryptAdd("C:/dev/연계보고 일일양식(20180928)_v2.xlsx", "C:/dev/sample.enc");
		ARIAFileEncrypt.doDecryptAdd("C:/dev/sample.enc", "C:/dev/decrypt.xlsx");
	}
}