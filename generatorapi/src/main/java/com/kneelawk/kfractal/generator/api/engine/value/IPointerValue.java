package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.ValueType;

public interface IPointerValue extends IEngineValue {
    ValueType getDataType() throws FractalException;

    IEngineValue getReferencedData() throws FractalException;

    void setReferencedData(IEngineValue data) throws FractalException;
}
