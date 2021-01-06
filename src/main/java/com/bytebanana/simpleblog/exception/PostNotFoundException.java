package com.bytebanana.simpleblog.exception;

public class PostNotFoundException extends RuntimeException {

	public PostNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public PostNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public PostNotFoundException(String message) {
		super(message);

	}

	public PostNotFoundException(Throwable cause) {
		super(cause);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7320226702883163692L;

}
