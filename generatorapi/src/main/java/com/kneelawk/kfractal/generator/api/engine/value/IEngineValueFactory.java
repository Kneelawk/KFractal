package com.kneelawk.kfractal.generator.api.engine.value;

import org.apache.commons.math3.complex.Complex;

public interface IEngineValueFactory {
	IBoolValue newBool(boolean b);

	IIntValue newInt(int i);

	IRealValue newReal(double d);

	IComplexValue newComplex(Complex complex);

	IFunctionValue newFunction(String name, IEngineValue[] contextValues);

	IPointerValue newPointer(IEngineValue referencedData);

	IPointerValue nullPointer();
}
