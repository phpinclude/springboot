package kr.pe.withwind.exception;

public class InstallException extends Exception {
	
	private static final long serialVersionUID = 9073379343030561587L;

	public InstallException(String msg, Exception e) {
		super(msg, e);
	}
}