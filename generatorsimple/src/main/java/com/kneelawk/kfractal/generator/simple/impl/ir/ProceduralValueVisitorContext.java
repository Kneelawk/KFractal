package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.BasicBlock;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.simple.impl.SimpleProgramEngine;
import com.kneelawk.kfractal.generator.simple.impl.ValueContainer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class ProceduralValueVisitorContext {
    private final SimpleProgramEngine engine;
    private final Program program;
    private final List<ValueContainer> globalVariables;
    private final FunctionDefinition function;
    private final List<IEngineValue> contextVariables;
    private final List<IEngineValue> arguments;
    private final BasicBlock block;

    private ProceduralValueVisitorContext(SimpleProgramEngine engine, Program program,
                                          List<ValueContainer> globalVariables,
                                          FunctionDefinition function,
                                          List<IEngineValue> contextVariables,
                                          List<IEngineValue> arguments,
                                          BasicBlock block) {
        this.engine = engine;
        this.program = program;
        this.globalVariables = globalVariables;
        this.function = function;
        this.contextVariables = contextVariables;
        this.arguments = arguments;
        this.block = block;
    }

    public SimpleProgramEngine getEngine() {
        return engine;
    }

    public Program getProgram() {
        return program;
    }

    public List<ValueContainer> getGlobalVariables() {
        return globalVariables;
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

    public BasicBlock getBlock() {
        return block;
    }

    public static ProceduralValueVisitorContext create(
            SimpleProgramEngine engine,
            Program program,
            Iterable<ValueContainer> globalVariables,
            FunctionDefinition function,
            Iterable<IEngineValue> contextVariables,
            Iterable<IEngineValue> arguments,
            BasicBlock block) {
        if (engine == null)
            throw new NullPointerException("Engine cannot be null");
        if (program == null)
            throw new NullPointerException("Program cannot be null");
        if (function == null)
            throw new NullPointerException("Function cannot be null");
        if (block == null)
            throw new NullPointerException("Block cannot be null");
        return new ProceduralValueVisitorContext(engine, program,
                ImmutableList.copyOf(globalVariables), function,
                ImmutableList.copyOf(contextVariables),
                ImmutableList.copyOf(arguments), block);
    }

    public static class Builder {
        private SimpleProgramEngine engine;
        private Program program;
        private List<ValueContainer> globalVariables =
                Lists.newArrayList();
        private FunctionDefinition function;
        private List<IEngineValue> contextVariables =
                Lists.newArrayList();
        private List<IEngineValue> arguments =
                Lists.newArrayList();
        private BasicBlock block;

        public Builder() {
        }

        public Builder(SimpleProgramEngine engine,
                       Program program,
                       Collection<ValueContainer> globalVariables,
                       FunctionDefinition function,
                       Collection<IEngineValue> contextVariables,
                       Collection<IEngineValue> arguments,
                       BasicBlock block) {
            this.engine = engine;
            this.program = program;
            this.globalVariables.addAll(globalVariables);
            this.function = function;
            this.contextVariables.addAll(contextVariables);
            this.arguments.addAll(arguments);
            this.block = block;
        }

        public ProceduralValueVisitorContext build() {
            if (engine == null)
                throw new IllegalStateException("No engine specified");
            if (program == null)
                throw new IllegalStateException("No program specified");
            if (function == null)
                throw new IllegalStateException("No function specified");
            if (block == null)
                throw new IllegalStateException("No block specified");
            return new ProceduralValueVisitorContext(engine, program,
                    ImmutableList.copyOf(globalVariables), function,
                    ImmutableList.copyOf(contextVariables),
                    ImmutableList.copyOf(arguments), block);
        }

        public SimpleProgramEngine getEngine() {
            return engine;
        }

        public Builder setEngine(SimpleProgramEngine engine) {
            this.engine = engine;
            return this;
        }

        public Program getProgram() {
            return program;
        }

        public Builder setProgram(Program program) {
            this.program = program;
            return this;
        }

        public List<ValueContainer> getGlobalVariables() {
            return globalVariables;
        }

        public Builder setGlobalVariables(
                Collection<ValueContainer> globalVariables) {
            this.globalVariables.clear();
            this.globalVariables.addAll(globalVariables);
            return this;
        }

        public Builder addGlobalVariable(ValueContainer globalVariable) {
            globalVariables.add(globalVariable);
            return this;
        }

        public Builder addGlobalVariables(
                ValueContainer... globalVariables) {
            this.globalVariables.addAll(Arrays.asList(globalVariables));
            return this;
        }

        public Builder addGlobalVariables(
                Collection<ValueContainer> globalVariables) {
            this.globalVariables.addAll(globalVariables);
            return this;
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

        public BasicBlock getBlock() {
            return block;
        }

        public Builder setBlock(BasicBlock block) {
            this.block = block;
            return this;
        }
    }
}
