package com.kneelawk.kfractal.generator.api.ir.attribute;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IGlobalAttribute {
    IGlobalAttribute PREALLOCATED = Preallocated.INSTANCE;

    <R> R accept(IGlobalAttributeVisitor<R> visitor) throws FractalException;
}
