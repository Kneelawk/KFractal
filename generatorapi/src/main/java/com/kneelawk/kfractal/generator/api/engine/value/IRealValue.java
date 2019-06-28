package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;

public interface IRealValue extends IEngineValue {
	double getValue() throws FractalEngineException;
}
