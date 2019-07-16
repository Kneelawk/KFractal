package com.kneelawk.kfractal.generator.api.ir;

import com.kneelawk.kfractal.generator.api.FractalException;

public class FractalIRException extends FractalException {
    public FractalIRException() {
    }

    public FractalIRException(String message) {
        super(message);
    }

    public FractalIRException(String message, Throwable cause) {
        super(message, cause);
    }

    public FractalIRException(Throwable cause) {
        super(cause);
    }
}
