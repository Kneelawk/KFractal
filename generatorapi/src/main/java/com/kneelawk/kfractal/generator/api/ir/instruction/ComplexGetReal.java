package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexGetReal - Instruction. Gets the real component of the argument.
 * <p>
 * ComplexGetReal(Complex complex)
 */
public class ComplexGetReal implements IValue {
    private IValue complex;

    private ComplexGetReal(IValue complex) {
        this.complex = complex;
    }

    public IValue getComplex() {
        return complex;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexGetReal(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("complex", complex)
                .toString();
    }

    public static ComplexGetReal create(IValue complex) {
        if (complex == null)
            throw new NullPointerException("Complex cannot be null");
        return new ComplexGetReal(complex);
    }

    public static class Builder {
        private IValue complex;

        public Builder() {
        }

        public Builder(IValue complex) {
            this.complex = complex;
        }

        public ComplexGetReal build() {
            if (complex == null)
                throw new IllegalStateException("No complex specified");
            return new ComplexGetReal(complex);
        }

        public IValue getComplex() {
            return complex;
        }

        public Builder setComplex(IValue complex) {
            this.complex = complex;
            return this;
        }
    }
}
