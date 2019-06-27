package com.kneelawk.kfractal.generator.validation;

public class IncompatibleVariableAttributeException extends FractalIRValidationException {
	public IncompatibleVariableAttributeException() {
	}

	public IncompatibleVariableAttributeException(String message) {
		super(message);
	}

	public IncompatibleVariableAttributeException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncompatibleVariableAttributeException(Throwable cause) {
		super(cause);
	}
}
