package com.kneelawk.kfractal.generator.validation;

public class IllegalVariableTypeException extends FractalIRValidationException {
	public IllegalVariableTypeException() {
	}

	public IllegalVariableTypeException(String message) {
		super(message);
	}

	public IllegalVariableTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalVariableTypeException(Throwable cause) {
		super(cause);
	}
}
