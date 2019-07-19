package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.engine.value.IPointerValue;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

/**
 * Created by Kneelawk on 7/18/19.
 */
public class SimpleNullPointerValue implements IPointerValue {
    public static final SimpleNullPointerValue INSTANCE = new SimpleNullPointerValue();

    private SimpleNullPointerValue() {
    }

    @Override
    public ValueType getDataType() throws FractalEngineException {
        return ValueTypes.VOID;
    }

    @Override
    public IEngineValue getReferencedData() throws FractalEngineException {
        throw new FractalEngineException("Cannot get the referenced value of the null pointer");
    }

    @Override
    public void setReferencedData(IEngineValue data) throws FractalEngineException {
        throw new FractalEngineException("Cannot set the referenced value of the null pointer");
    }

    @Override
    public ValueType getType() throws FractalEngineException {
        return ValueTypes.NULL_POINTER;
    }
}
