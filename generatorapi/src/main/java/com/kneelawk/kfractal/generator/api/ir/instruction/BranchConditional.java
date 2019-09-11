package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * BranchConditional - Instruction. Jumps to one of the two BasicBlocks referenced by the two last arguments depending
 * on the boolean value of the first argument.
 * <p>
 * BranchConditional(Bool condition, trueBlockIndex, falseBlockIndex)
 */
public class BranchConditional implements IValue {
    private IValue condition;
    private int trueBlockIndex;
    private int falseBlockIndex;

    private BranchConditional(IValue condition, int trueBlockIndex, int falseBlockIndex) {
        this.condition = condition;
        this.trueBlockIndex = trueBlockIndex;
        this.falseBlockIndex = falseBlockIndex;
    }

    public IValue getCondition() {
        return condition;
    }

    public int getTrueBlockIndex() {
        return trueBlockIndex;
    }

    public int getFalseBlockIndex() {
        return falseBlockIndex;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitBranchConditional(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("condition", condition)
                .append("trueBlockIndex", trueBlockIndex)
                .append("falseBlockIndex", falseBlockIndex)
                .toString();
    }

    public static BranchConditional create(IValue condition, int trueBlockIndex,
                                           int falseBlockIndex) {
        if (condition == null)
            throw new NullPointerException("Condition cannot be null");
        return new BranchConditional(condition, trueBlockIndex, falseBlockIndex);
    }

    public static class Builder {
        private IValue condition;
        private int trueBlockIndex;
        private int falseBlockIndex;

        public Builder() {
        }

        public Builder(IValue condition, int trueBlockIndex,
                       int falseBlockIndex) {
            this.condition = condition;
            this.trueBlockIndex = trueBlockIndex;
            this.falseBlockIndex = falseBlockIndex;
        }

        public BranchConditional build() {
            if (condition == null)
                throw new IllegalStateException("No condition specified");
            return new BranchConditional(condition, trueBlockIndex, falseBlockIndex);
        }

        public IValue getCondition() {
            return condition;
        }

        public Builder setCondition(IValue condition) {
            this.condition = condition;
            return this;
        }

        public int getTrueBlockIndex() {
            return trueBlockIndex;
        }

        public Builder setTrueBlockIndex(int trueBlockIndex) {
            this.trueBlockIndex = trueBlockIndex;
            return this;
        }

        public int getFalseBlockIndex() {
            return falseBlockIndex;
        }

        public Builder setFalseBlockIndex(int falseBlockIndex) {
            this.falseBlockIndex = falseBlockIndex;
            return this;
        }
    }
}
