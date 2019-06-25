package com.kneelawk.kfractal.generator.api.engine.value;

import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;

public interface IIntValue extends IEngineValue {
	int getValue() throws FractalEngineException;
}
