package com.kneelawk.kfractal.generator.api.ir.phi;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;

public interface IPhiInput extends IValue {
    <R> R accept(IPhiInputVisitor<R> visitor) throws FractalException;
}
