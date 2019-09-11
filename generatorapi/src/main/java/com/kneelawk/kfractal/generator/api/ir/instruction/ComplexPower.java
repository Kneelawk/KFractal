package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexPower - Instruction. Raises the first argument to the power of the second argument.
 * <p>
 * ComplexPower(Complex base, Complex exponent)
 */
public class ComplexPower implements IValue {
    private IValue base;
    private IValue exponent;

    private ComplexPower(IValue base, IValue exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    public IValue getBase() {
        return base;
    }

    public IValue getExponent() {
        return exponent;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexPower(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("base", base)
                .append("exponent", exponent)
                .toString();
    }

    public static ComplexPower create(IValue base,
                                      IValue exponent) {
        if (base == null)
            throw new NullPointerException("Base cannot be null");
        if (exponent == null)
            throw new NullPointerException("Exponent cannot be null");
        return new ComplexPower(base, exponent);
    }

    public static class Builder {
        private IValue base;
        private IValue exponent;

        public Builder() {
        }

        public Builder(IValue base,
                       IValue exponent) {
            this.base = base;
            this.exponent = exponent;
        }

        public ComplexPower build() {
            if (base == null)
                throw new IllegalStateException("No base specified");
            if (exponent == null)
                throw new IllegalStateException("No exponent specified");
            return new ComplexPower(base, exponent);
        }

        public IValue getBase() {
            return base;
        }

        public Builder setBase(IValue base) {
            this.base = base;
            return this;
        }

        public IValue getExponent() {
            return exponent;
        }

        public Builder setExponent(IValue exponent) {
            this.exponent = exponent;
            return this;
        }
    }
}
