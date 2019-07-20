package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

import java.util.List;

public interface IFunctionValue extends IEngineValue {
    ValueTypes.FunctionType getSignature() throws FractalEngineException;

    IEngineValue invoke(List<IEngineValue> arguments) throws FractalEngineException;
}
