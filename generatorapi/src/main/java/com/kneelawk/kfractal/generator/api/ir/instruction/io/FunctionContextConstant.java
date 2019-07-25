package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FunctionContextConstant implements IInstructionInput {
    private int functionIndex;
    private List<IInstructionInput> contextVariables;

    private FunctionContextConstant(int functionIndex,
                                    List<IInstructionInput> contextVariables) {
        this.functionIndex = functionIndex;
        this.contextVariables = contextVariables;
    }

    public int getFunctionIndex() {
        return functionIndex;
    }

    public List<IInstructionInput> getContextVariables() {
        return contextVariables;
    }

    @Override
    public <R> R accept(IInstructionInputVisitor<R> visitor) throws FractalException {
        return visitor.visitFunctionContextConstant(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("functionIndex", functionIndex)
                .append("contextVariables", contextVariables)
                .toString();
    }

    public static FunctionContextConstant create(int functionIndex) {
        if (functionIndex < 0)
            throw new IndexOutOfBoundsException("FunctionContextConstant index cannot be less than 0");
        return new FunctionContextConstant(functionIndex, ImmutableList.of());
    }

    public static FunctionContextConstant create(int functionIndex,
                                                 IInstructionInput... contextVariables) {
        if (functionIndex < 0)
            throw new IndexOutOfBoundsException("FunctionContextConstant index cannot be less than 0");
        return new FunctionContextConstant(functionIndex, ImmutableList.copyOf(contextVariables));
    }

    public static FunctionContextConstant create(int functionIndex,
                                                 List<IInstructionInput> contextVariables) {
        if (functionIndex < 0)
            throw new IndexOutOfBoundsException("FunctionContextConstant index cannot be less than 0");
        return new FunctionContextConstant(functionIndex, ImmutableList.copyOf(contextVariables));
    }

    public static class Builder {
        private int functionIndex;
        private List<IInstructionInput>
                contextVariables = Lists.newArrayList();

        public Builder() {
        }

        public Builder(int functionIndex,
                       Collection<IInstructionInput> contextVariables) {
            this.functionIndex = functionIndex;
            this.contextVariables.addAll(contextVariables);
        }

        public FunctionContextConstant build() {
            if (functionIndex < 0)
                throw new IndexOutOfBoundsException("FunctionContextConstant index cannot be less than 0");
            return new FunctionContextConstant(functionIndex, ImmutableList.copyOf(contextVariables));
        }

        public int getFunctionIndex() {
            return functionIndex;
        }

        public Builder setFunctionIndex(int functionIndex) {
            this.functionIndex = functionIndex;
            return this;
        }

        public List<IInstructionInput> getContextVariables() {
            return contextVariables;
        }

        public Builder setContextVariables(
                List<IInstructionInput> contextVariables) {
            this.contextVariables.clear();
            contextVariables.addAll(contextVariables);
            return this;
        }

        public Builder addContextVariable(IInstructionInput contextVariable) {
            contextVariables.add(contextVariable);
            return this;
        }

        public Builder addContextVariables(IInstructionInput... contextVariables) {
            this.contextVariables.addAll(Arrays.asList(contextVariables));
            return this;
        }

        public Builder addContextVariables(Collection<IInstructionInput> contextVariables) {
            this.contextVariables.addAll(contextVariables);
            return this;
        }
    }
}
