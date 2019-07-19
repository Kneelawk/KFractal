package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.IIntValue;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

/**
 * Created by Kneelawk on 7/17/19.
 */
public class SimpleIntValue implements IIntValue {
    private final int value;

    public SimpleIntValue(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public ValueType getType() throws FractalEngineException {
        return ValueTypes.INT;
    }
}
