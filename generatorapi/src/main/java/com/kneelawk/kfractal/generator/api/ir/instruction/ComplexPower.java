package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexPower - Instruction. Raises the first argument to the power of the second argument.
 * <p>
 * ComplexPower(Complex base, Complex exponent)
 */
public class ComplexPower implements IProceduralValue {
    private IProceduralValue base;
    private IProceduralValue exponent;

    private ComplexPower(IProceduralValue base, IProceduralValue exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    public IProceduralValue getBase() {
        return base;
    }

    public IProceduralValue getExponent() {
        return exponent;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexPower(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitComplexPower(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("base", base)
                .append("exponent", exponent)
                .toString();
    }

    public static ComplexPower create(IProceduralValue base,
                                      IProceduralValue exponent) {
        if (base == null)
            throw new NullPointerException("Base cannot be null");
        if (exponent == null)
            throw new NullPointerException("Exponent cannot be null");
        return new ComplexPower(base, exponent);
    }

    public static class Builder {
        private IProceduralValue base;
        private IProceduralValue exponent;

        public Builder() {
        }

        public Builder(IProceduralValue base,
                       IProceduralValue exponent) {
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

        public IProceduralValue getBase() {
            return base;
        }

        public Builder setBase(IProceduralValue base) {
            this.base = base;
            return this;
        }

        public IProceduralValue getExponent() {
            return exponent;
        }

        public Builder setExponent(IProceduralValue exponent) {
            this.exponent = exponent;
            return this;
        }
    }
}
