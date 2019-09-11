package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntDivide - Instruction. Divides the first argument by the second argument.
 * <p>
 * IntDivide(Int dividend, Int divisor)
 */
public class IntDivide implements IValue {
    private IValue dividend;
    private IValue divisor;

    private IntDivide(IValue dividend, IValue divisor) {
        this.dividend = dividend;
        this.divisor = divisor;
    }

    public IValue getDividend() {
        return dividend;
    }

    public IValue getDivisor() {
        return divisor;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntDivide(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("dividend", dividend)
                .append("divisor", divisor)
                .toString();
    }

    public static IntDivide create(IValue dividend,
                                   IValue divisor) {
        if (dividend == null)
            throw new NullPointerException("Dividend cannot be null");
        if (divisor == null)
            throw new NullPointerException("Divisor cannot be null");
        return new IntDivide(dividend, divisor);
    }

    public static class Builder {
        private IValue dividend;
        private IValue divisor;

        public Builder() {
        }

        public Builder(IValue dividend,
                       IValue divisor) {
            this.dividend = dividend;
            this.divisor = divisor;
        }

        public IntDivide build() {
            if (dividend == null)
                throw new IllegalStateException("No dividend specified");
            if (divisor == null)
                throw new IllegalStateException("No divisor specified");
            return new IntDivide(dividend, divisor);
        }

        public IValue getDividend() {
            return dividend;
        }

        public Builder setDividend(IValue dividend) {
            this.dividend = dividend;
            return this;
        }

        public IValue getDivisor() {
            return divisor;
        }

        public Builder setDivisor(IValue divisor) {
            this.divisor = divisor;
            return this;
        }
    }
}
