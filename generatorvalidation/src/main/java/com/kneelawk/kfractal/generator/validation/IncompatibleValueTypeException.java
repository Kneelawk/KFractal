package com.kneelawk.kfractal.generator.validation;

public class IncompatibleValueTypeException extends FractalIRValidationException {
    public IncompatibleValueTypeException() {
    }

    public IncompatibleValueTypeException(String message) {
        super(message);
    }

    public IncompatibleValueTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncompatibleValueTypeException(Throwable cause) {
        super(cause);
    }
}
