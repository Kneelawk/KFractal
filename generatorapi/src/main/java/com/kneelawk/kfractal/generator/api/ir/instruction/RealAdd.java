package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * RealAdd - Instruction. Adds the last two arguments together and stores the result in the variable referenced by the
 * first argument.
 * <p>
 * RealAdd(Int sum, Int leftAddend, Int rightAddend)
 */
public class RealAdd implements IInstruction {
    private IInstructionOutput sum;
    private IInstructionInput leftAddend;
    private IInstructionInput rightAddend;

    private RealAdd(IInstructionOutput sum,
                    IInstructionInput leftAddend,
                    IInstructionInput rightAddend) {
        this.sum = sum;
        this.leftAddend = leftAddend;
        this.rightAddend = rightAddend;
    }

    public IInstructionOutput getSum() {
        return sum;
    }

    public IInstructionInput getLeftAddend() {
        return leftAddend;
    }

    public IInstructionInput getRightAddend() {
        return rightAddend;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitRealAdd(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("sum", sum)
                .append("leftAddend", leftAddend)
                .append("rightAddend", rightAddend)
                .toString();
    }

    public static RealAdd create(IInstructionOutput sum,
                                 IInstructionInput leftAddend,
                                 IInstructionInput rightAddend) {
        if (sum == null)
            throw new NullPointerException("Sum cannot be null");
        if (leftAddend == null)
            throw new NullPointerException("LeftAddend cannot be null");
        if (rightAddend == null)
            throw new NullPointerException("RightAddend cannot be null");
        return new RealAdd(sum, leftAddend, rightAddend);
    }

    public static class Builder {
        private IInstructionOutput sum;
        private IInstructionInput leftAddend;
        private IInstructionInput rightAddend;

        public Builder() {
        }

        public Builder(IInstructionOutput sum,
                       IInstructionInput leftAddend,
                       IInstructionInput rightAddend) {
            this.sum = sum;
            this.leftAddend = leftAddend;
            this.rightAddend = rightAddend;
        }

        public RealAdd build() {
            if (sum == null)
                throw new IllegalStateException("No sum specified");
            if (leftAddend == null)
                throw new IllegalStateException("No leftAddend specified");
            if (rightAddend == null)
                throw new IllegalStateException("No rightAddend specified");
            return new RealAdd(sum, leftAddend, rightAddend);
        }

        public IInstructionOutput getSum() {
            return sum;
        }

        public Builder setSum(IInstructionOutput sum) {
            this.sum = sum;
            return this;
        }

        public IInstructionInput getLeftAddend() {
            return leftAddend;
        }

        public Builder setLeftAddend(
                IInstructionInput leftAddend) {
            this.leftAddend = leftAddend;
            return this;
        }

        public IInstructionInput getRightAddend() {
            return rightAddend;
        }

        public Builder setRightAddend(
                IInstructionInput rightAddend) {
            this.rightAddend = rightAddend;
            return this;
        }
    }
}
