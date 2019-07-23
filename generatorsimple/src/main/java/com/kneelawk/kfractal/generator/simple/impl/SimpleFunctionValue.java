package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.engine.value.IFunctionValue;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

import java.util.List;
import java.util.Objects;

/**
 * Created by Kneelawk on 7/17/19.
 */
public class SimpleFunctionValue implements IFunctionValue {
    private final SimpleProgramEngine engine;
    private final int index;
    private final List<IEngineValue> contextVariables;

    public SimpleFunctionValue(SimpleProgramEngine engine, int index,
                               List<IEngineValue> contextVariables) {
        this.engine = engine;
        this.index = index;
        this.contextVariables = contextVariables;
    }

    @Override
    public ValueTypes.FunctionType getSignature() throws FractalEngineException {
        return engine.getFunctionSignature(index);
    }

    @Override
    public IEngineValue invoke(List<IEngineValue> arguments) throws FractalException {
        return engine.invokeFunction(index, contextVariables, arguments);
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
        return index == that.index &&
                contextVariables.equals(that.contextVariables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, contextVariables);
    }
}
