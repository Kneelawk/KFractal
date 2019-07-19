package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.engine.value.IFunctionValue;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Kneelawk on 7/17/19.
 */
public class SimpleFunctionValue implements IFunctionValue {
    private final SimpleProgramEngine engine;
    private final String name;
    private final IEngineValue[] contextVariables;

    public SimpleFunctionValue(SimpleProgramEngine engine, String name,
                               IEngineValue[] contextVariables) {
        this.engine = engine;
        this.name = name;
        this.contextVariables = contextVariables;
    }

    @Override
    public ValueTypes.FunctionType getSignature() throws FractalEngineException {
        return engine.getFunctionSignature(name);
    }

    @Override
    public IEngineValue invoke(IEngineValue[] arguments) throws FractalEngineException {
        return engine.invokeFunction(name, contextVariables, arguments);
    }

    @Override
    public ValueType getType() throws FractalEngineException {
        return getSignature();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleFunctionValue that = (SimpleFunctionValue) o;
        return name.equals(that.name) &&
                Arrays.equals(contextVariables, that.contextVariables);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(contextVariables);
        return result;
    }
}
