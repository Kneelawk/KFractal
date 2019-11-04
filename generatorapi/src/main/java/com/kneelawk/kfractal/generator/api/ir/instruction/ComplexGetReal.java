package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexGetReal - Instruction. Gets the real component of the argument.
 * <p>
 * ComplexGetReal(Complex complex)
 */
public class ComplexGetReal implements IProceduralValue {
    private IProceduralValue complex;

    private ComplexGetReal(IProceduralValue complex) {
        this.complex = complex;
    }

    public IProceduralValue getComplex() {
        return complex;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexGetReal(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitComplexGetReal(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("complex", complex)
                .toString();
    }

    public static ComplexGetReal create(IProceduralValue complex) {
        if (complex == null)
            throw new NullPointerException("Complex cannot be null");
        return new ComplexGetReal(complex);
    }

    public static class Builder {
        private IProceduralValue complex;

        public Builder() {
        }

        public Builder(IProceduralValue complex) {
            this.complex = complex;
        }

        public ComplexGetReal build() {
            if (complex == null)
                throw new IllegalStateException("No complex specified");
            return new ComplexGetReal(complex);
        }

        public IProceduralValue getComplex() {
            return complex;
        }

        public Builder setComplex(IProceduralValue complex) {
            this.complex = complex;
            return this;
        }
    }
}
