package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * RealDivide - Instruction. Divides the second to last argument by the last argument and stores the result in the
 * variable referenced by the first argument.
 * <p>
 * RealDivide(Real quotient, Real dividend, Real divisor)
 */
public class RealDivide implements IInstruction {
    private IInstructionOutput quotient;
    private IInstructionInput dividend;
    private IInstructionInput divisor;

    private RealDivide(IInstructionOutput quotient,
                       IInstructionInput dividend,
                       IInstructionInput divisor) {
        this.quotient = quotient;
        this.dividend = dividend;
        this.divisor = divisor;
    }

    public IInstructionOutput getQuotient() {
        return quotient;
    }

    public IInstructionInput getDividend() {
        return dividend;
    }

    public IInstructionInput getDivisor() {
        return divisor;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
        return visitor.visitRealDivide(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("quotient", quotient)
                .append("dividend", dividend)
                .append("divisor", divisor)
                .toString();
    }

    public static RealDivide create(IInstructionOutput quotient,
                                    IInstructionInput dividend,
                                    IInstructionInput divisor) {
        return new RealDivide(quotient, dividend, divisor);
    }

    public static class Builder {
        private IInstructionOutput quotient;
        private IInstructionInput dividend;
        private IInstructionInput divisor;

        public Builder() {
        }

        public Builder(IInstructionOutput quotient,
                       IInstructionInput dividend,
                       IInstructionInput divisor) {
            this.quotient = quotient;
            this.dividend = dividend;
            this.divisor = divisor;
        }

        public RealDivide build() {
            return new RealDivide(quotient, dividend, divisor);
        }

        public IInstructionOutput getQuotient() {
            return quotient;
        }

        public Builder setQuotient(IInstructionOutput quotient) {
            this.quotient = quotient;
            return this;
        }

        public IInstructionInput getDividend() {
            return dividend;
        }

        public Builder setDividend(IInstructionInput dividend) {
            this.dividend = dividend;
            return this;
        }

        public IInstructionInput getDivisor() {
            return divisor;
        }

        public Builder setDivisor(IInstructionInput divisor) {
            this.divisor = divisor;
            return this;
        }
    }
}
