package com.kneelawk.kfractal.generator.validation;

public class MissingVariableReferenceException extends FractalIRValidationException {
	public MissingVariableReferenceException() {
	}

	public MissingVariableReferenceException(String message) {
		super(message);
	}

	public MissingVariableReferenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingVariableReferenceException(Throwable cause) {
		super(cause);
	}
}
