package com.kneelawk.kfractal.generator.api.engine;

import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValueFactory;
import com.kneelawk.kfractal.generator.api.engine.value.IFunctionValue;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

import java.util.function.Supplier;

public interface IProgramEngine {
	void initialize(Program program);

	IEngineValueFactory getValueFactory();

	ValueType getGlobalValueType(String name);

	IEngineValue getGlobalValue(String name);

	void setGlobalValue(String name, IEngineValue value);

	ValueTypes.FunctionType getFunctionSignature(String name);

	IFunctionValue getFunction(String name, IEngineValue[] contextValues);
}
