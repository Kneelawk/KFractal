package com.kneelawk.kfractal.generator.validation;

public class IllegalVariableAttributeException extends FractalIRValidationException {
    public IllegalVariableAttributeException() {
    }

    public IllegalVariableAttributeException(String message) {
        super(message);
    }

    public IllegalVariableAttributeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalVariableAttributeException(Throwable cause) {
        super(cause);
    }
}
