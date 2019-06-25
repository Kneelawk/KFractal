package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;

public interface IBoolValue extends IEngineValue {
	boolean getValue() throws FractalEngineException;
}
