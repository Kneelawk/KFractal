package com.kneelawk.kfractal.generator.api.ir.constant;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInput;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInputVisitor;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;

public class NullPointer implements IProceduralValue, IPhiInput {
    public static final NullPointer INSTANCE = new NullPointer();

    private NullPointer() {
    }

    @Override
    public <R> R accept(IPhiInputVisitor<R> visitor) throws FractalException {
        return visitor.visitNullPointer();
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitNullPointer();
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitNullPointer();
    }

    @Override
    public String toString() {
        return "NullPointer";
    }
}
