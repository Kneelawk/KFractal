package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * BranchConditional - Instruction. Jumps to one of the two BasicBlocks referenced by the two last arguments depending
 * on the boolean value of the first argument.
 * <p>
 * BranchConditional(Bool condition, trueBlockIndex, falseBlockIndex)
 */
public class BranchConditional implements IProceduralValue {
    private IProceduralValue condition;
    private int trueBlockIndex;
    private int falseBlockIndex;

    private BranchConditional(IProceduralValue condition, int trueBlockIndex, int falseBlockIndex) {
        this.condition = condition;
        this.trueBlockIndex = trueBlockIndex;
        this.falseBlockIndex = falseBlockIndex;
    }

    public IProceduralValue getCondition() {
        return condition;
    }

    public int getTrueBlockIndex() {
        return trueBlockIndex;
    }

    public int getFalseBlockIndex() {
        return falseBlockIndex;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitBranchConditional(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
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

    public static BranchConditional create(IProceduralValue condition, int trueBlockIndex,
                                           int falseBlockIndex) {
        if (condition == null)
            throw new NullPointerException("Condition cannot be null");
        return new BranchConditional(condition, trueBlockIndex, falseBlockIndex);
    }

    public static class Builder {
        private IProceduralValue condition;
        private int trueBlockIndex;
        private int falseBlockIndex;

        public Builder() {
        }

        public Builder(IProceduralValue condition, int trueBlockIndex,
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

        public IProceduralValue getCondition() {
            return condition;
        }

        public Builder setCondition(IProceduralValue condition) {
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
