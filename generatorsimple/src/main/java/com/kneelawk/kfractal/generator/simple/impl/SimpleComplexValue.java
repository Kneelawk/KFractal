package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.IComplexValue;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import org.apache.commons.math3.complex.Complex;

/**
 * Created by Kneelawk on 7/17/19.
 */
public class SimpleComplexValue implements IComplexValue {
    private final Complex value;

    public SimpleComplexValue(Complex value) {
        this.value = value;
    }

    @Override
    public Complex getValue() {
        return value;
    }

    @Override
    public ValueType getType() throws FractalEngineException {
        return ValueTypes.COMPLEX;
    }
}
