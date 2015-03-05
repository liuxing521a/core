package org.itas.core;

public class IllegaException extends RuntimeException {
	
	private static final long serialVersionUID = 8089161970865572779L;

	public IllegaException() {
		super();
	}

	public IllegaException(String message) {
		super(message);
	}

	public IllegaException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegaException(Throwable cause) {
		super(cause);
	}
}
