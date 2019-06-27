package com.kneelawk.kfractal.generator.validation;

public class NameConflictException extends FractalIRValidationException {
	public NameConflictException() {
	}

	public NameConflictException(String message) {
		super(message);
	}

	public NameConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public NameConflictException(Throwable cause) {
		super(cause);
	}
}
