package com.kneelawk.kfractal.generator.validation;

public class CyclicTypeLookupException extends FractalIRValidationException {
    public CyclicTypeLookupException() {
    }

    public CyclicTypeLookupException(String message) {
        super(message);
    }

    public CyclicTypeLookupException(String message, Throwable cause) {
        super(message, cause);
    }

    public CyclicTypeLookupException(Throwable cause) {
        super(cause);
    }
}
