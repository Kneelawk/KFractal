package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PhiInputVisitorContext {
    private final FunctionDefinition function;
    private final List<IEngineValue> contextVariables;
    private final List<IEngineValue> arguments;

    private PhiInputVisitorContext(FunctionDefinition function,
                                   List<IEngineValue> contextVariables,
                                   List<IEngineValue> arguments) {
        this.function = function;
        this.contextVariables = contextVariables;
        this.arguments = arguments;
    }

    public FunctionDefinition getFunction() {
        return function;
    }

    public List<IEngineValue> getContextVariables() {
        return contextVariables;
    }

    public List<IEngineValue> getArguments() {
        return arguments;
    }

    public static PhiInputVisitorContext create(FunctionDefinition function,
                                                Iterable<IEngineValue> contextVariables,
                                                Iterable<IEngineValue> arguments) {
        if (function == null)
            throw new NullPointerException("Function cannot be null");
        return new PhiInputVisitorContext(function, ImmutableList.copyOf(contextVariables),
                ImmutableList.copyOf(arguments));
    }

    public static class Builder {
        private FunctionDefinition function;
        private List<IEngineValue> contextVariables =
                Lists.newArrayList();
        private List<IEngineValue> arguments =
                Lists.newArrayList();

        public Builder() {
        }

        public Builder(FunctionDefinition function,
                       Collection<IEngineValue> contextVariables,
                       Collection<IEngineValue> arguments) {
            this.function = function;
            this.contextVariables.addAll(contextVariables);
            this.arguments.addAll(arguments);
        }

        public PhiInputVisitorContext build() {
            if (function == null)
                throw new IllegalStateException("No function specified");
            return new PhiInputVisitorContext(function,
                    ImmutableList.copyOf(contextVariables),
                    ImmutableList.copyOf(arguments));
        }

        public FunctionDefinition getFunction() {
            return function;
        }

        public Builder setFunction(FunctionDefinition function) {
            this.function = function;
            return this;
        }

        public List<IEngineValue> getContextVariables() {
            return contextVariables;
        }

        public Builder setContextVariables(
                Collection<IEngineValue> contextVariables) {
            this.contextVariables.clear();
            this.contextVariables.addAll(contextVariables);
            return this;
        }

        public Builder addContextVariable(
                IEngineValue contextVariable) {
            contextVariables.add(contextVariable);
            return this;
        }

        public Builder addContextVariables(
                IEngineValue... contextVariables) {
            this.contextVariables.addAll(Arrays.asList(contextVariables));
            return this;
        }

        public Builder addContextVariables(
                Collection<IEngineValue> contextVariables) {
            this.contextVariables.addAll(contextVariables);
            return this;
        }

        public List<IEngineValue> getArguments() {
            return arguments;
        }

        public Builder setArguments(
                Collection<IEngineValue> arguments) {
            this.arguments.clear();
            this.arguments.addAll(arguments);
            return this;
        }

        public Builder addArgument(IEngineValue argument) {
            arguments.add(argument);
            return this;
        }

        public Builder addArguments(IEngineValue... arguments) {
            this.arguments.addAll(Arrays.asList(arguments));
            return this;
        }

        public Builder addArguments(
                Collection<IEngineValue> arguments) {
            this.arguments.addAll(arguments);
            return this;
        }
    }
}
