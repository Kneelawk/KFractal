package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.ir.ValueType;

public interface IPointerValue extends IEngineValue {
	ValueType getDataType();

	IEngineValue getReferencedData();

	void setReferencedData(IEngineValue data);
}
