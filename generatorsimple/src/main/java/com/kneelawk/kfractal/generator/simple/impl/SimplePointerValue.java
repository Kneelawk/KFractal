package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.engine.value.IPointerValue;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;

/**
 * Created by Kneelawk on 7/17/19.
 */
public class SimplePointerValue implements IPointerValue {
    private IEngineValue referencedData;

    public SimplePointerValue(IEngineValue referencedData) {
        this.referencedData = referencedData;
    }

    @Override
    public IEngineValue getReferencedData() {
        return referencedData;
    }

    @Override
    public void setReferencedData(IEngineValue referencedData) {
        this.referencedData = referencedData;
    }

    @Override
    public ValueType getDataType() throws FractalException {
        return referencedData.getType();
    }

    @Override
    public ValueType getType() throws FractalException {
        return ValueTypes.POINTER(referencedData.getType());
    }
}
