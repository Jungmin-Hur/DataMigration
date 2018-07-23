package main.common.exception;

public class FileException extends Exception {
	private static final long serialVersionUID = 1L;
	private String code;
	
	public FileException(String msg) {
		super(msg);
	}

	public FileException(String msg, String code) {
		super(msg + code);
		this.code = code;
	}
	
	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append("[FileException]");
		sb.append(super.getMessage());
		
		return sb.toString();
	}
}
