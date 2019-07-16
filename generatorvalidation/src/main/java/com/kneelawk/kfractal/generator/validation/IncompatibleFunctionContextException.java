package com.kneelawk.kfractal.generator.validation;

public class IncompatibleFunctionContextException extends FractalIRValidationException {
	public IncompatibleFunctionContextException() {
	}

	public IncompatibleFunctionContextException(String message) {
		super(message);
	}

	public IncompatibleFunctionContextException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncompatibleFunctionContextException(Throwable cause) {
		super(cause);
	}
}
