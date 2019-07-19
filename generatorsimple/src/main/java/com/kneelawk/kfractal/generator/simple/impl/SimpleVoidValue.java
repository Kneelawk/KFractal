package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

/**
 * Created by Kneelawk on 7/18/19.
 */
public class SimpleVoidValue implements IEngineValue {
    public static final SimpleVoidValue INSTANCE = new SimpleVoidValue();

    private SimpleVoidValue() {
    }

    @Override
    public ValueType getType() throws FractalEngineException {
        return ValueTypes.VOID;
    }
}
