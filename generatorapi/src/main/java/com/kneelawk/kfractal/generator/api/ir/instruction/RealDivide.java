package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * RealDivide - Instruction. Divides the first argument by the second argument.
 * <p>
 * RealDivide(Real dividend, Real divisor)
 */
public class RealDivide implements IProceduralValue {
    private IProceduralValue dividend;
    private IProceduralValue divisor;

    private RealDivide(IProceduralValue dividend, IProceduralValue divisor) {
        this.dividend = dividend;
        this.divisor = divisor;
    }

    public IProceduralValue getDividend() {
        return dividend;
    }

    public IProceduralValue getDivisor() {
        return divisor;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitRealDivide(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitRealDivide(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("dividend", dividend)
                .append("divisor", divisor)
                .toString();
    }

    public static RealDivide create(IProceduralValue dividend,
                                    IProceduralValue divisor) {
        if (dividend == null)
            throw new NullPointerException("Dividend cannot be null");
        if (divisor == null)
            throw new NullPointerException("Divisor cannot be null");
        return new RealDivide(dividend, divisor);
    }

    public static class Builder {
        private IProceduralValue dividend;
        private IProceduralValue divisor;

        public Builder() {
        }

        public Builder(IProceduralValue dividend,
                       IProceduralValue divisor) {
            this.dividend = dividend;
            this.divisor = divisor;
        }

        public RealDivide build() {
            if (dividend == null)
                throw new IllegalStateException("No dividend specified");
            if (divisor == null)
                throw new IllegalStateException("No divisor specified");
            return new RealDivide(dividend, divisor);
        }

        public IProceduralValue getDividend() {
            return dividend;
        }

        public Builder setDividend(IProceduralValue dividend) {
            this.dividend = dividend;
            return this;
        }

        public IProceduralValue getDivisor() {
            return divisor;
        }

        public Builder setDivisor(IProceduralValue divisor) {
            this.divisor = divisor;
            return this;
        }
    }
}
