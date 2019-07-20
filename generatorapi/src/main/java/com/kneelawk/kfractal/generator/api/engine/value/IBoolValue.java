package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IBoolValue extends IEngineValue {
    boolean getValue() throws FractalException;
}
