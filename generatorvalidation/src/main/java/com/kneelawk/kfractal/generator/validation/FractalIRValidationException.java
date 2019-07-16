package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;

public class FractalIRValidationException extends FractalIRException {
    public FractalIRValidationException() {
    }

    public FractalIRValidationException(String message) {
        super(message);
    }

    public FractalIRValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FractalIRValidationException(Throwable cause) {
        super(cause);
    }
}
