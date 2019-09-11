package com.kneelawk.kfractal.generator.api.ir.phi;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IPhiInput {
    <R> R accept(IPhiInputVisitor<R> visitor) throws FractalException;
}
