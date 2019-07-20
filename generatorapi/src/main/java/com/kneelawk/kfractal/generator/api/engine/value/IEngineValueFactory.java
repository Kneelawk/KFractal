package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import org.apache.commons.math3.complex.Complex;

public interface IEngineValueFactory {
    IBoolValue newBool(boolean b) throws FractalEngineException;

    IIntValue newInt(int i) throws FractalEngineException;

    IRealValue newReal(double d) throws FractalEngineException;

    IComplexValue newComplex(Complex complex) throws FractalEngineException;

    IFunctionValue newFunction(String name, IEngineValue[] contextValues) throws FractalEngineException;

    IFunctionValue nullFunction() throws FractalEngineException;

    IPointerValue newPointer(IEngineValue referencedData) throws FractalEngineException;

    IPointerValue nullPointer() throws FractalEngineException;
}
