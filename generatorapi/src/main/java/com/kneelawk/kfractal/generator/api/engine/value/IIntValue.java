package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IIntValue extends IEngineValue {
    int getValue() throws FractalException;
}
