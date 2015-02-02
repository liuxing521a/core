package net.itas.core.exception;

public class FieldNotConfigException extends RuntimeException {
	
	private static final long serialVersionUID = -5078365276466487872L;

	public FieldNotConfigException() {
		super();
	}

	public FieldNotConfigException(String message) {
		super(message);
	}

	public FieldNotConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldNotConfigException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public FieldNotConfigException fillInStackTrace() {
		return this;
	}
}
