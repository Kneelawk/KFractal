package com.kneelawk.kfractal.generator.simple;

import com.kneelawk.kfractal.generator.api.engine.IProgramEngine;
import com.kneelawk.kfractal.generator.simple.impl.SimpleProgramEngine;

/**
 * Created by Kneelawk on 7/18/19.
 */
public class SimpleProgramEngineFactory {
    public IProgramEngine newProgramEngine() {
        return new SimpleProgramEngine();
    }
}
