package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.List;

/**
 * If - Instruction. Executes one of two instruction sub-lists based on the boolean value of the first argument.
 * <p>
 * If(Bool condition, [ Instruction* ifTrue ], [ Instruction* ifFalse ])
 */
public class If implements IInstruction {
    private IInstructionInput condition;
    private List<IInstruction> ifTrue;
    private List<IInstruction> ifFalse;

    private If(IInstructionInput condition,
               List<IInstruction> ifTrue,
               List<IInstruction> ifFalse) {
        this.condition = condition;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    public IInstructionInput getCondition() {
        return condition;
    }

    public List<IInstruction> getIfTrue() {
        return ifTrue;
    }

    public List<IInstruction> getIfFalse() {
        return ifFalse;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitIf(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("condition", condition)
                .append("ifTrue", ifTrue)
                .append("ifFalse", ifFalse)
                .toString();
    }

    public static If create(IInstructionInput condition,
                            Iterable<IInstruction> ifTrue,
                            Iterable<IInstruction> ifFalse) {
        if (condition == null)
            throw new NullPointerException("Condition cannot be null");
        return new If(condition, ImmutableList.copyOf(ifTrue), ImmutableList.copyOf(ifFalse));
    }

    public static class Builder {
        private IInstructionInput condition;
        private List<IInstruction> ifTrue = Lists.newArrayList();
        private List<IInstruction> ifFalse = Lists.newArrayList();

        public Builder() {
        }

        public Builder(IInstructionInput condition,
                       Collection<IInstruction> ifTrue,
                       Collection<IInstruction> ifFalse) {
            this.condition = condition;
            this.ifTrue.addAll(ifTrue);
            this.ifFalse.addAll(ifFalse);
        }

        public If build() {
            if (condition == null)
                throw new IllegalStateException("No condition specified");
            return new If(condition, ImmutableList.copyOf(ifTrue), ImmutableList.copyOf(ifFalse));
        }

        public IInstructionInput getCondition() {
            return condition;
        }

        public Builder setCondition(IInstructionInput condition) {
            this.condition = condition;
            return this;
        }

        public List<IInstruction> getIfTrue() {
            return ifTrue;
        }

        public Builder setIfTrue(
                Collection<IInstruction> ifTrue) {
            this.ifTrue.clear();
            this.ifTrue.addAll(ifTrue);
            return this;
        }

        public Builder addIfTrue(IInstruction instruction) {
            ifTrue.add(instruction);
            return this;
        }

        public Builder addIfTrue(Collection<IInstruction> instructions) {
            ifTrue.addAll(instructions);
            return this;
        }

        public List<IInstruction> getIfFalse() {
            return ifFalse;
        }

        public Builder setIfFalse(
                Collection<IInstruction> ifFalse) {
            this.ifFalse.clear();
            this.ifFalse.addAll(ifFalse);
            return this;
        }

        public Builder addIfFalse(IInstruction instruction) {
            ifFalse.add(instruction);
            return this;
        }

        public Builder addIfFalse(Collection<IInstruction> instructions) {
            ifFalse.addAll(instructions);
            return this;
        }
    }
}
