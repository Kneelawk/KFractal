package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
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
public class FunctionCreate implements IProceduralValue {
    private String functionName;
    private List<IProceduralValue> contextVariables;

    private FunctionCreate(String functionName,
                           List<IProceduralValue> contextVariables) {
        this.functionName = functionName;
        this.contextVariables = contextVariables;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<IProceduralValue> getContextVariables() {
        return contextVariables;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitFunctionCreate(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitFunctionCreate(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("functionName", functionName)
                .append("contextVariables", contextVariables)
                .toString();
    }

    public static FunctionCreate create(String functionName) {
        if (functionName == null)
            throw new NullPointerException("FunctionName cannot be null");
        return new FunctionCreate(functionName, ImmutableList.of());
    }

    public static FunctionCreate create(String functionName,
                                        IProceduralValue... contextVariables) {
        if (functionName == null)
            throw new NullPointerException("FunctionName cannot be null");
        return new FunctionCreate(functionName, ImmutableList.copyOf(contextVariables));
    }

    public static FunctionCreate create(String functionName,
                                        Iterable<IProceduralValue> contextVariables) {
        if (functionName == null)
            throw new NullPointerException("FunctionName cannot be null");
        return new FunctionCreate(functionName, ImmutableList.copyOf(contextVariables));
    }

    public static class Builder {
        private String functionName;
        private List<IProceduralValue> contextVariables =
                Lists.newArrayList();

        public Builder() {
        }

        public Builder(String functionName,
                       Collection<IProceduralValue> contextVariables) {
            this.functionName = functionName;
            this.contextVariables.addAll(contextVariables);
        }

        public FunctionCreate build() {
            if (functionName == null)
                throw new IllegalStateException("No functionName specified");
            return new FunctionCreate(functionName, ImmutableList.copyOf(contextVariables));
        }

        public String getFunctionName() {
            return functionName;
        }

        public Builder setFunctionName(String functionName) {
            this.functionName = functionName;
            return this;
        }

        public List<IProceduralValue> getContextVariables() {
            return contextVariables;
        }

        public Builder setContextVariables(
                Collection<IProceduralValue> contextVariables) {
            this.contextVariables.clear();
            this.contextVariables.addAll(contextVariables);
            return this;
        }

        public Builder addContextVariable(IProceduralValue contextVariable) {
            contextVariables.add(contextVariable);
            return this;
        }

        public Builder addContextVariables(
                IProceduralValue... contextVariables) {
            this.contextVariables.addAll(Arrays.asList(contextVariables));
            return this;
        }

        public Builder addContextVariables(
                Collection<IProceduralValue> contextVariables) {
            this.contextVariables.addAll(contextVariables);
            return this;
        }
    }
}
