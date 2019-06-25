package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.ir.ValueType;

public interface IPointerValue extends IEngineValue {
	ValueType getDataType() throws FractalEngineException;

	IEngineValue getReferencedData() throws FractalEngineException;

	void setReferencedData(IEngineValue data) throws FractalEngineException;
}
