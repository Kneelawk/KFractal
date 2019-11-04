package com.kneelawk.kfractal.generator.validation;

public class CyclicProceduralInstructionException extends FractalIRValidationException {
    public CyclicProceduralInstructionException() {
    }

    public CyclicProceduralInstructionException(String message) {
        super(message);
    }

    public CyclicProceduralInstructionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CyclicProceduralInstructionException(Throwable cause) {
        super(cause);
    }
}
