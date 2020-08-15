package br.com.asv.model.exceptions;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ServiceException(String message) {
		super(message);
	}
	public ServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
