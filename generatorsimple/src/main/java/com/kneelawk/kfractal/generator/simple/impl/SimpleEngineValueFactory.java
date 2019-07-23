package com.kneelawk.kfractal.generator.simple.impl;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.*;
import org.apache.commons.math3.complex.Complex;

import java.util.List;

/**
 * Created by Kneelawk on 7/18/19.
 */
public class SimpleEngineValueFactory implements IEngineValueFactory {
    private final SimpleProgramEngine engine;

    public SimpleEngineValueFactory(SimpleProgramEngine engine) {
        this.engine = engine;
    }

    @Override
    public IBoolValue newBool(boolean b) throws FractalEngineException {
        return new SimpleBoolValue(b);
    }

    @Override
    public IIntValue newInt(int i) throws FractalEngineException {
        return new SimpleIntValue(i);
    }

    @Override
    public IRealValue newReal(double d) throws FractalEngineException {
        return new SimpleRealValue(d);
    }

    @Override
    public IComplexValue newComplex(Complex complex) throws FractalEngineException {
        return new SimpleComplexValue(complex);
    }

    @Override
    public IFunctionValue newFunction(int index, List<IEngineValue> contextValues) throws FractalEngineException {
        return engine.getFunction(index, contextValues);
    }

    @Override
    public IFunctionValue nullFunction() throws FractalEngineException {
        return SimpleNullFunctionValue.INSTANCE;
    }

    @Override
    public IPointerValue newPointer(IEngineValue referencedData) throws FractalEngineException {
        return new SimplePointerValue(referencedData);
    }

    @Override
    public IPointerValue nullPointer() throws FractalEngineException {
        return SimpleNullPointerValue.INSTANCE;
    }
}
