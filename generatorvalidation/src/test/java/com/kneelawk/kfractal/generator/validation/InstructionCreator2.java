package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;

public interface InstructionCreator2 {
    IProceduralValue create(IProceduralValue left, IProceduralValue right);
}
