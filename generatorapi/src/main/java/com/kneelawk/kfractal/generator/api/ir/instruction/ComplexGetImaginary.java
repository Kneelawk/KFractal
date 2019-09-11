package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexGetImaginary - Instruction. Gets the imaginary component of the argument.
 * <p>
 * ComplexGetImaginary(Complex complex)
 */
public class ComplexGetImaginary implements IValue {
    private IValue complex;

    private ComplexGetImaginary(IValue complex) {
        this.complex = complex;
    }

    public IValue getComplex() {
        return complex;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexGetImaginary(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("complex", complex)
                .toString();
    }

    public static ComplexGetImaginary create(IValue complex) {
        if (complex == null)
            throw new NullPointerException("Complex cannot be null");
        return new ComplexGetImaginary(complex);
    }

    public static class Builder {
        private IValue complex;

        public Builder() {
        }

        public Builder(IValue complex) {
            this.complex = complex;
        }

        public ComplexGetImaginary build() {
            if (complex == null)
                throw new IllegalStateException("No complex specified");
            return new ComplexGetImaginary(complex);
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
