package com.kneelawk.kfractal.generator.api.engine;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValueFactory;
import com.kneelawk.kfractal.generator.api.engine.value.IFunctionValue;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

import java.util.List;

public interface IProgramEngine {
    void initialize(Program program) throws FractalException;

    IEngineValueFactory getValueFactory() throws FractalException;

    ValueType getGlobalValueType(int index) throws FractalException;

    IEngineValue getGlobalValue(int index) throws FractalException;

    void setGlobalValue(int index, IEngineValue value) throws FractalException;

    ValueTypes.FunctionType getFunctionSignature(int index) throws FractalException;

    IFunctionValue getFunction(int index, List<IEngineValue> contextValues) throws FractalException;
}
