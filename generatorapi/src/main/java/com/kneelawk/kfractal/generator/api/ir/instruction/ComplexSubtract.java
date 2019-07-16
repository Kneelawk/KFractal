package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexSubtract - Instruction. Subtracts the last argument from the second to last argument and stores the result in
 * the variable referenced by the first argument.
 * <p>
 * ComplexSubtract(Complex difference, Complex minuend, Complex subtrahend)
 */
public class ComplexSubtract implements IInstruction {
    private IInstructionOutput difference;
    private IInstructionInput minuend;
    private IInstructionInput subtrahend;

    private ComplexSubtract(IInstructionOutput difference, IInstructionInput minuend, IInstructionInput subtrahend) {
        this.difference = difference;
        this.minuend = minuend;
        this.subtrahend = subtrahend;
    }

    public IInstructionOutput getDifference() {
        return difference;
    }

    public IInstructionInput getMinuend() {
        return minuend;
    }

    public IInstructionInput getSubtrahend() {
        return subtrahend;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
        return visitor.visitComplexSubtract(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("difference", difference)
                .append("minuend", minuend)
                .append("subtrahend", subtrahend)
                .toString();
    }

    public static ComplexSubtract create(
            IInstructionOutput difference,
            IInstructionInput minuend,
            IInstructionInput subtrahend) {
        return new ComplexSubtract(difference, minuend, subtrahend);
    }

    public static class Builder {
        private IInstructionOutput difference;
        private IInstructionInput minuend;
        private IInstructionInput subtrahend;

        public Builder() {
        }

        public Builder(IInstructionOutput difference, IInstructionInput minuend, IInstructionInput subtrahend) {
            this.difference = difference;
            this.minuend = minuend;
            this.subtrahend = subtrahend;
        }

        public ComplexSubtract build() {
            return new ComplexSubtract(difference, minuend, subtrahend);
        }

        public IInstructionOutput getDifference() {
            return difference;
        }

        public Builder setDifference(IInstructionOutput difference) {
            this.difference = difference;
            return this;
        }

        public IInstructionInput getMinuend() {
            return minuend;
        }

        public Builder setMinuend(IInstructionInput minuend) {
            this.minuend = minuend;
            return this;
        }

        public IInstructionInput getSubtrahend() {
            return subtrahend;
        }

        public Builder setSubtrahend(IInstructionInput subtrahend) {
            this.subtrahend = subtrahend;
            return this;
        }
    }
}
