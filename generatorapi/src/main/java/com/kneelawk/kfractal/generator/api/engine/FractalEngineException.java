package com.kneelawk.kfractal.generator.api.engine;

import com.kneelawk.kfractal.generator.api.FractalException;

public class FractalEngineException extends FractalException {
    public FractalEngineException() {
    }

    public FractalEngineException(String message) {
        super(message);
    }

    public FractalEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public FractalEngineException(Throwable cause) {
        super(cause);
    }
}
