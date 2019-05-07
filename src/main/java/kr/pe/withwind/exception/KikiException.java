package kr.pe.withwind.exception;

public class KikiException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String errCode = "-1";

	public KikiException(String message, Throwable cause) {
		super(message, cause);
	}

	public KikiException(String message) {
		super(message);
	}
	
	public KikiException(String message, Throwable cause, String errCode) {
		super(message, cause);
		this.errCode = errCode;
	}

	public KikiException(String message, String errCode) {
		super(message);
		this.errCode = errCode;
	}
	
	public String getErrCode() {
		return this.errCode;
	}
}