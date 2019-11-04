package com.kneelawk.kfractal.generator.api.ir;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IProceduralValue extends IValue {
    <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException;
}
