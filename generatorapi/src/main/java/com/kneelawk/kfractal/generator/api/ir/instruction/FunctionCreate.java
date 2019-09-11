package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * FunctionCreate - Instruction. Constructs a function context variable using the given function name and context
 * variable values.
 * <p>
 * FunctionCreate(functionName, [ ** contextVariables ])
 */
public class FunctionCreate implements IValue {
    private int functionIndex;
    private List<IValue> contextVariables;

    private FunctionCreate(int functionIndex,
                           List<IValue> contextVariables) {
        this.functionIndex = functionIndex;
        this.contextVariables = contextVariables;
    }

    public int getFunctionIndex() {
        return functionIndex;
    }

    public List<IValue> getContextVariables() {
        return contextVariables;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitFunctionCreate(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("functionIndex", functionIndex)
                .append("contextVariables", contextVariables)
                .toString();
    }

    public static FunctionCreate create(int functionIndex) {
        if (functionIndex < 0)
            throw new IndexOutOfBoundsException("FunctionContextConstant index cannot be less than 0");
        return new FunctionCreate(functionIndex, ImmutableList.of());
    }

    public static FunctionCreate create(int functionIndex,
                                        IValue... contextVariables) {
        if (functionIndex < 0)
            throw new IndexOutOfBoundsException("FunctionContextConstant index cannot be less than 0");
        return new FunctionCreate(functionIndex, ImmutableList.copyOf(contextVariables));
    }

    public static FunctionCreate create(int functionIndex,
                                        List<IValue> contextVariables) {
        if (functionIndex < 0)
            throw new IndexOutOfBoundsException("FunctionContextConstant index cannot be less than 0");
        return new FunctionCreate(functionIndex, ImmutableList.copyOf(contextVariables));
    }

    public static class Builder {
        private int functionIndex;
        private List<IValue>
                contextVariables = Lists.newArrayList();

        public Builder() {
        }

        public Builder(int functionIndex,
                       Collection<IValue> contextVariables) {
            this.functionIndex = functionIndex;
            this.contextVariables.addAll(contextVariables);
        }

        public FunctionCreate build() {
            if (functionIndex < 0)
                throw new IndexOutOfBoundsException("FunctionContextConstant index cannot be less than 0");
            return new FunctionCreate(functionIndex, ImmutableList.copyOf(contextVariables));
        }

        public int getFunctionIndex() {
            return functionIndex;
        }

        public Builder setFunctionIndex(int functionIndex) {
            this.functionIndex = functionIndex;
            return this;
        }

        public List<IValue> getContextVariables() {
            return contextVariables;
        }

        public Builder setContextVariables(
                List<IValue> contextVariables) {
            this.contextVariables.clear();
            contextVariables.addAll(contextVariables);
            return this;
        }

        public Builder addContextVariable(IValue contextVariable) {
            contextVariables.add(contextVariable);
            return this;
        }

        public Builder addContextVariables(IValue... contextVariables) {
            this.contextVariables.addAll(Arrays.asList(contextVariables));
            return this;
        }

        public Builder addContextVariables(Collection<IValue> contextVariables) {
            this.contextVariables.addAll(contextVariables);
            return this;
        }
    }
}
