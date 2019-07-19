package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.ValueType;

/**
 * Created by Kneelawk on 7/17/19.
 */
public class ValueContainer {
    private final ValueType type;
    private IEngineValue value;

    public ValueContainer(ValueType type, IEngineValue value) {
        this.type = type;
        this.value = value;
    }

    public ValueType getType() {
        return type;
    }

    public IEngineValue getValue() {
        return value;
    }

    public void setValue(IEngineValue value) {
        this.value = value;
    }
}
