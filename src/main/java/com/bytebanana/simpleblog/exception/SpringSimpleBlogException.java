package com.bytebanana.simpleblog.exception;

public class SpringSimpleBlogException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4110492165622093001L;

	public SpringSimpleBlogException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public SpringSimpleBlogException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public SpringSimpleBlogException(String message) {
		super(message);
		
	}

	public SpringSimpleBlogException(Throwable cause) {
		super(cause);
		
	}

}
