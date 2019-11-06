package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.FractalException;
import org.apache.commons.math3.complex.Complex;

import java.util.List;

public interface IEngineValueFactory {
    IBoolValue newBool(boolean b) throws FractalException;

    IIntValue newInt(int i) throws FractalException;

    IRealValue newReal(double d) throws FractalException;

    IComplexValue newComplex(Complex complex) throws FractalException;

    IFunctionValue newFunction(String name, List<IEngineValue> contextValues) throws FractalException;

    IFunctionValue nullFunction() throws FractalException;

    IPointerValue newPointer(IEngineValue referencedData) throws FractalException;

    IPointerValue nullPointer() throws FractalException;
}
