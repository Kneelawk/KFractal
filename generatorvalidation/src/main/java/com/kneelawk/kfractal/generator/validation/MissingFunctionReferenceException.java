package com.kneelawk.kfractal.generator.validation;

public class MissingFunctionReferenceException extends FractalIRValidationException {
    public MissingFunctionReferenceException() {
    }

    public MissingFunctionReferenceException(String message) {
        super(message);
    }

    public MissingFunctionReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingFunctionReferenceException(Throwable cause) {
        super(cause);
    }
}
