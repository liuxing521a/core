package org.itas.core;

public class IllnessException extends RuntimeException {
	
	private static final long serialVersionUID = 8089161970865572779L;

	public IllnessException() {
		super();
	}

	public IllnessException(String message) {
		super(message);
	}

	public IllnessException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllnessException(Throwable cause) {
		super(cause);
	}
}
