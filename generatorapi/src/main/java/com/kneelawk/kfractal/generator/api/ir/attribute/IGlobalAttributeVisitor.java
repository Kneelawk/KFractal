package com.kneelawk.kfractal.generator.api.ir.attribute;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IGlobalAttributeVisitor<R> {
    R visitPreallocated() throws FractalException;
}
