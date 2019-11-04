package com.kneelawk.kfractal.generator.api.ir;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IValue {
    <R> R accept(IValueVisitor<R> visitor) throws FractalException;
}
