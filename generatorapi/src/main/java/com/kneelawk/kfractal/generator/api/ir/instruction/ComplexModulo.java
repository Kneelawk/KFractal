package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexModulo - Instruction. Computes the complex modulo of the argument. A complex number's modulus is that complex
 * number's absolute distance from the origin as computed by
 * sqrt(&lt;real-component&gt;^2 + &lt;imaginary-component&gt;^2).
 * <p>
 * ComplexModulo(Complex complex)
 */
public class ComplexModulo implements IProceduralValue {
    private IProceduralValue complex;

    private ComplexModulo(IProceduralValue complex) {
        this.complex = complex;
    }

    public IProceduralValue getComplex() {
        return complex;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexModulo(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitComplexModulo(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("complex", complex)
                .toString();
    }

    public static ComplexModulo create(IProceduralValue complex) {
        if (complex == null)
            throw new NullPointerException("Complex cannot be null");
        return new ComplexModulo(complex);
    }

    public static class Builder {
        private IProceduralValue complex;

        public Builder() {
        }

        public Builder(IProceduralValue complex) {
            this.complex = complex;
        }

        public ComplexModulo build() {
            if (complex == null)
                throw new IllegalStateException("No complex specified");
            return new ComplexModulo(complex);
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
