package com.isea.basic;

/**
 * 停止下载异常
 * 
 * @author liuzh
 *
 */
public class StopException extends Exception {
	private static final long serialVersionUID = -4802869170818170735L;

	public StopException() {
		super();
	}

	public StopException(String message, Throwable cause) {
		super(message, cause);
	}

	public StopException(String message) {
		super(message);
	}

	public StopException(Throwable cause) {
		super(cause);
	}
	
}
