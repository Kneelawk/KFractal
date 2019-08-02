package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.complex.Complex;

/**
 * Created by Kneelawk on 5/27/19.
 */
public class ComplexConstant implements IComplexInstructionInput {
    private Complex value;

    private ComplexConstant(Complex value) {
        this.value = value;
    }

    public Complex getValue() {
        return value;
    }

    @Override
    public <R> R acceptComplex(IComplexInstructionInputVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexConstant(this);
    }

    @Override
    public <R> R acceptNotVoid(INotVoidInstructionInputVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexConstant(this);
    }

    @Override
    public <R> R accept(IInstructionInputVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexConstant(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("value", value)
                .toString();
    }

    public static ComplexConstant create(Complex value) {
        if (value == null)
            throw new NullPointerException("Value cannot be null");
        return new ComplexConstant(value);
    }

    public static class Builder {
        private Complex value;

        public Builder() {
        }

        public Builder(Complex value) {
            this.value = value;
        }

        public ComplexConstant build() {
            if (value == null)
                throw new IllegalStateException("No value specified");
            return new ComplexConstant(value);
        }

        public Complex getValue() {
            return value;
        }

        public Builder setValue(Complex value) {
            this.value = value;
            return this;
        }
    }
}
