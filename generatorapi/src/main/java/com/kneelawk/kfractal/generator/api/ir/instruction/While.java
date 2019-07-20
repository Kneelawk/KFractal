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
 * While - Instruction. Repeated executes an internal instruction sub-list as long as the boolean value of the first
 * argument is true, re-checking before every iteration.
 * <p>
 * While(Bool condition, [ Instruction* whileTrue ])
 */
public class While implements IInstruction {
    private IInstructionInput condition;
    private List<IInstruction> whileTrue;

    private While(IInstructionInput condition,
                  List<IInstruction> whileTrue) {
        this.condition = condition;
        this.whileTrue = whileTrue;
    }

    public IInstructionInput getCondition() {
        return condition;
    }

    public List<IInstruction> getWhileTrue() {
        return whileTrue;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitWhile(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("condition", condition)
                .append("whileTrue", whileTrue)
                .toString();
    }

    public static While create(IInstructionInput condition,
                               Iterable<IInstruction> whileTrue) {
        return new While(condition, ImmutableList.copyOf(whileTrue));
    }

    public static class Builder {
        private IInstructionInput condition;
        private List<IInstruction> whileTrue = Lists.newArrayList();

        public Builder() {
        }

        public Builder(IInstructionInput condition,
                       Collection<IInstruction> whileTrue) {
            this.condition = condition;
            this.whileTrue.addAll(whileTrue);
        }

        public While build() {
            return new While(condition, ImmutableList.copyOf(whileTrue));
        }

        public IInstructionInput getCondition() {
            return condition;
        }

        public Builder setCondition(IInstructionInput condition) {
            this.condition = condition;
            return this;
        }

        public List<IInstruction> getWhileTrue() {
            return whileTrue;
        }

        public Builder setWhileTrue(
                Collection<IInstruction> whileTrue) {
            this.whileTrue.clear();
            this.whileTrue.addAll(whileTrue);
            return this;
        }

        public Builder addWhileTrue(IInstruction instruction) {
            whileTrue.add(instruction);
            return this;
        }

        public Builder addWhileTrue(Collection<IInstruction> instructions) {
            whileTrue.addAll(instructions);
            return this;
        }
    }
}
