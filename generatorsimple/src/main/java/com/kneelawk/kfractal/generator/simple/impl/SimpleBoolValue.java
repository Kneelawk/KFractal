package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.IBoolValue;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

/**
 * Created by Kneelawk on 7/17/19.
 */
public class SimpleBoolValue implements IBoolValue {
    private final boolean value;

    public SimpleBoolValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean getValue() throws FractalEngineException {
        return value;
    }

    @Override
    public ValueType getType() throws FractalEngineException {
        return ValueTypes.BOOL;
    }
}
