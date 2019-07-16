package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

public interface IFunctionValue extends IEngineValue {
    ValueTypes.FunctionType getSignature() throws FractalEngineException;

    IEngineValue invoke(IEngineValue[] arguments) throws FractalEngineException;
}
