package com.kneelawk.kfractal.generator.api.ir.reference;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInput;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInputVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class InstructionReference implements IValue, IPhiInput {
    private int blockIndex;
    private InstructionScope scope;
    private int instructionIndex;

    private InstructionReference(int blockIndex, InstructionScope scope, int instructionIndex) {
        this.blockIndex = blockIndex;
        this.scope = scope;
        this.instructionIndex = instructionIndex;
    }

    public int getBlockIndex() {
        return blockIndex;
    }

    public InstructionScope getScope() {
        return scope;
    }

    public int getInstructionIndex() {
        return instructionIndex;
    }

    @Override
    public <R> R accept(IPhiInputVisitor<R> visitor) throws FractalException {
        return visitor.visitInstructionReference(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitInstructionReference(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("blockIndex", blockIndex)
                .append("scope", scope)
                .append("instructionIndex", instructionIndex)
                .toString();
    }

    public static InstructionReference create(int blockIndex,
                                              InstructionScope scope,
                                              int instructionIndex) {
        if (scope == null)
            throw new NullPointerException("Scope cannot be null");
        return new InstructionReference(blockIndex, scope, instructionIndex);
    }

    public static class Builder {
        private int blockIndex;
        private InstructionScope scope;
        private int instructionIndex;

        public Builder() {
        }

        public Builder(int blockIndex, InstructionScope scope,
                       int instructionIndex) {
            this.blockIndex = blockIndex;
            this.scope = scope;
            this.instructionIndex = instructionIndex;
        }

        public InstructionReference build() {
            if (scope == null)
                throw new IllegalStateException("No scope specified");
            return new InstructionReference(blockIndex, scope, instructionIndex);
        }

        public int getBlockIndex() {
            return blockIndex;
        }

        public Builder setBlockIndex(int blockIndex) {
            this.blockIndex = blockIndex;
            return this;
        }

        public InstructionScope getScope() {
            return scope;
        }

        public Builder setScope(InstructionScope scope) {
            this.scope = scope;
            return this;
        }

        public int getInstructionIndex() {
            return instructionIndex;
        }

        public Builder setInstructionIndex(int instructionIndex) {
            this.instructionIndex = instructionIndex;
            return this;
        }
    }
}
