package kr.pe.withwind.exception;

public class WindCommonException extends Exception {
	private static final long serialVersionUID = -3354969764559375644L;
	
	private String errCode = "-1";

	public WindCommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public WindCommonException(String message) {
		super(message);
	}
	
	public WindCommonException(String message, Throwable cause, String errCode) {
		super(message, cause);
		this.errCode = errCode;
	}

	public WindCommonException(String message, String errCode) {
		super(message);
		this.errCode = errCode;
	}
	
	public String getErrCode() {
		return this.errCode;
	}
}