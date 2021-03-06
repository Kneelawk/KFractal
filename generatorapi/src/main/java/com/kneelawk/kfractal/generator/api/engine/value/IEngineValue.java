package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.ValueType;

public interface IEngineValue {
    ValueType getType() throws FractalException;
}
