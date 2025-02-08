package com.example.demo.exceptions;

public class PayPalServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public PayPalServiceException() {
        super();
    }

    public PayPalServiceException(String message) {
        super(message);
    }

    public PayPalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayPalServiceException(Throwable cause) {
        super(cause);
    }
}
