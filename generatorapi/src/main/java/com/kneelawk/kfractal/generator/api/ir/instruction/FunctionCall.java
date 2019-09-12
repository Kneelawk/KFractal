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
 * FunctionCall - Instruction. Calls the function represented by the function context that is the first argument with
 * arguments in the list that is the second argument. This instruction's return value is that of the called function,
 * or void if the called function returned void. Each of the arguments in the specified argument list must be the same
 * types as the arguments in the function.
 * <p>
 * FunctionCall(Function(*, [ ** ]) function, [ ** arguments ])
 */
public class FunctionCall implements IValue {
    private IValue function;
    private List<IValue> arguments;

    private FunctionCall(IValue function, List<IValue> arguments) {
        this.function = function;
        this.arguments = arguments;
    }

    public IValue getFunction() {
        return function;
    }

    public List<IValue> getArguments() {
        return arguments;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitFunctionCall(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("function", function)
                .append("arguments", arguments)
                .toString();
    }

    public static FunctionCall create(IValue function) {
        if (function == null)
            throw new NullPointerException("Function cannot be null");
        return new FunctionCall(function, ImmutableList.of());
    }

    public static FunctionCall create(IValue function,
                                      IValue... arguments) {
        if (function == null)
            throw new NullPointerException("Function cannot be null");
        return new FunctionCall(function, ImmutableList.copyOf(arguments));
    }

    public static FunctionCall create(IValue function,
                                      Iterable<IValue> arguments) {
        if (function == null)
            throw new NullPointerException("Function cannot be null");
        return new FunctionCall(function, ImmutableList.copyOf(arguments));
    }

    public static class Builder {
        private IValue function;
        private List<IValue> arguments =
                Lists.newArrayList();

        public Builder() {
        }

        public Builder(IValue function,
                       Collection<IValue> arguments) {
            this.function = function;
            this.arguments.addAll(arguments);
        }

        public FunctionCall build() {
            if (function == null)
                throw new IllegalStateException("No function specified");
            return new FunctionCall(function, ImmutableList.copyOf(arguments));
        }

        public IValue getFunction() {
            return function;
        }

        public Builder setFunction(IValue function) {
            this.function = function;
            return this;
        }

        public List<IValue> getArguments() {
            return arguments;
        }

        public Builder setArguments(Collection<IValue> arguments) {
            this.arguments.clear();
            this.arguments.addAll(arguments);
            return this;
        }

        public Builder addArgument(IValue argument) {
            arguments.add(argument);
            return this;
        }

        public Builder addArguments(IValue... arguments) {
            this.arguments.addAll(Arrays.asList(arguments));
            return this;
        }

        public Builder addArguments(Collection<IValue> arguments) {
            this.arguments.addAll(arguments);
            return this;
        }
    }
}
