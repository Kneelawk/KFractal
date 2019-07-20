package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntAnd - Instruction. Bitwise ands the last two arguments together and stores the result in the variable referenced
 * by the last argument.
 * <p>
 * IntAnd(Int result, Int left, Int right)
 */
public class IntAnd implements IInstruction {
    private IInstructionOutput result;
    private IInstructionInput left;
    private IInstructionInput right;

    private IntAnd(IInstructionOutput result, IInstructionInput left,
                   IInstructionInput right) {
        this.result = result;
        this.left = left;
        this.right = right;
    }

    public IInstructionOutput getResult() {
        return result;
    }

    public IInstructionInput getLeft() {
        return left;
    }

    public IInstructionInput getRight() {
        return right;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitIntAnd(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("result", result)
                .append("left", left)
                .append("right", right)
                .toString();
    }

    public static IntAnd create(IInstructionOutput result,
                                IInstructionInput left,
                                IInstructionInput right) {
        return new IntAnd(result, left, right);
    }

    public static class Builder {
        private IInstructionOutput result;
        private IInstructionInput left;
        private IInstructionInput right;

        public Builder() {
        }

        public Builder(IInstructionOutput result,
                       IInstructionInput left,
                       IInstructionInput right) {
            this.result = result;
            this.left = left;
            this.right = right;
        }

        public IntAnd build() {
            return new IntAnd(result, left, right);
        }

        public IInstructionOutput getResult() {
            return result;
        }

        public Builder setResult(IInstructionOutput result) {
            this.result = result;
            return this;
        }

        public IInstructionInput getLeft() {
            return left;
        }

        public Builder setLeft(IInstructionInput left) {
            this.left = left;
            return this;
        }

        public IInstructionInput getRight() {
            return right;
        }

        public Builder setRight(IInstructionInput right) {
            this.right = right;
            return this;
        }
    }
}
