package com.kneelawk.kfractal.generator.api.ir.attribute;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IAttribute {
    IAttribute PREALLOCATED = Preallocated.INSTANCE;

    <R> R accept(IAttributeVisitor<R> visitor) throws FractalException;
}
