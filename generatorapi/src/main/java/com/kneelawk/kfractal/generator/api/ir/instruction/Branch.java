package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Branch - Instruction. Unconditionally jumps to the basic block referenced by its argument.
 * <p>
 * Branch(blockIndex)
 */
public class Branch implements IValue {
    private int blockIndex;

    public Branch(int blockIndex) {
        this.blockIndex = blockIndex;
    }

    public int getBlockIndex() {
        return blockIndex;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitBranch(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("blockIndex", blockIndex)
                .toString();
    }

    public static Branch create(int blockIndex) {
        return new Branch(blockIndex);
    }

    public static class Builder {
        private int blockIndex;

        public Builder() {
        }

        public Builder(int blockIndex) {
            this.blockIndex = blockIndex;
        }

        public Branch build() {
            return new Branch(blockIndex);
        }

        public int getBlockIndex() {
            return blockIndex;
        }

        public Builder setBlockIndex(int blockIndex) {
            this.blockIndex = blockIndex;
            return this;
        }
    }
}
