package com.kneelawk.kfractal.generator.api.engine;

import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValueFactory;
import com.kneelawk.kfractal.generator.api.engine.value.IFunctionValue;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

public interface IProgramEngine {
	void initialize(Program program) throws FractalEngineException;

	IEngineValueFactory getValueFactory() throws FractalEngineException;

	ValueType getGlobalValueType(String name) throws FractalEngineException;

	IEngineValue getGlobalValue(String name) throws FractalEngineException;

	void setGlobalValue(String name, IEngineValue value) throws FractalEngineException;

	ValueTypes.FunctionType getFunctionSignature(String name) throws FractalEngineException;

	IFunctionValue getFunction(String name, IEngineValue[] contextValues) throws FractalEngineException;
}
