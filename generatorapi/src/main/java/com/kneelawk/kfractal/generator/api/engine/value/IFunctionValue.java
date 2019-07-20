package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

import java.util.List;

public interface IFunctionValue extends IEngineValue {
    ValueTypes.FunctionType getSignature() throws FractalException;

    IEngineValue invoke(List<IEngineValue> arguments) throws FractalException;
}
