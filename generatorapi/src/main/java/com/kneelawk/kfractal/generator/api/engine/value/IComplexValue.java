package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import org.apache.commons.math3.complex.Complex;

public interface IComplexValue extends IEngineValue {
	Complex getValue() throws FractalEngineException;
}
