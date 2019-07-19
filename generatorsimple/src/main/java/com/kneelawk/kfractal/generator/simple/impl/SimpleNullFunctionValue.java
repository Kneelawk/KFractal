package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.engine.value.IFunctionValue;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

/**
 * Created by Kneelawk on 7/18/19.
 */
public class SimpleNullFunctionValue implements IFunctionValue {
    public static final SimpleNullFunctionValue INSTANCE = new SimpleNullFunctionValue();

    private SimpleNullFunctionValue() {
    }

    @Override
    public ValueTypes.FunctionType getSignature() throws FractalEngineException {
        return ValueTypes.toFunction(ValueTypes.NULL_FUNCTION);
    }

    @Override
    public IEngineValue invoke(IEngineValue[] arguments) throws FractalEngineException {
        throw new FractalEngineException("The null function cannot be invoked");
    }

    @Override
    public ValueType getType() throws FractalEngineException {
        return ValueTypes.NULL_FUNCTION;
    }
}
