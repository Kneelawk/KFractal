package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IRealValue extends IEngineValue {
    double getValue() throws FractalException;
}
