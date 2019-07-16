package com.kneelawk.kfractal.generator.validation;

public class IncompatibleFunctionArgumentException extends FractalIRValidationException {
    public IncompatibleFunctionArgumentException() {
    }

    public IncompatibleFunctionArgumentException(String message) {
        super(message);
    }

    public IncompatibleFunctionArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncompatibleFunctionArgumentException(Throwable cause) {
        super(cause);
    }
}
