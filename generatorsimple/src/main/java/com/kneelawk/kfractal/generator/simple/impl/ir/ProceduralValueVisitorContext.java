package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.BasicBlock;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.simple.impl.SimpleProgramEngine;
import com.kneelawk.kfractal.generator.simple.impl.ValueContainer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class ProceduralValueVisitorContext {
    private final SimpleProgramEngine engine;
    private final Program program;
    private final Map<String, ValueContainer> globalVariables;
    private final FunctionDefinition function;
    private final List<IEngineValue> contextVariables;
    private final List<IEngineValue> arguments;
    private final BasicBlock block;

    private ProceduralValueVisitorContext(SimpleProgramEngine engine, Program program,
                                          Map<String, ValueContainer> globalVariables,
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

    public Map<String, ValueContainer> getGlobalVariables() {
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
            Map<String, ValueContainer> globalVariables,
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
                ImmutableMap.copyOf(globalVariables), function,
                ImmutableList.copyOf(contextVariables),
                ImmutableList.copyOf(arguments), block);
    }

    public static class Builder {
        private SimpleProgramEngine engine;
        private Program program;
        private Map<String, ValueContainer>
                globalVariables = Maps.newHashMap();
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
                       Map<String, ValueContainer> globalVariables,
                       FunctionDefinition function,
                       Collection<IEngineValue> contextVariables,
                       Collection<IEngineValue> arguments,
                       BasicBlock block) {
            this.engine = engine;
            this.program = program;
            this.globalVariables.putAll(globalVariables);
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
                    ImmutableMap.copyOf(globalVariables), function,
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

        public Map<String, ValueContainer> getGlobalVariables() {
            return globalVariables;
        }

        public Builder setGlobalVariables(
                Map<String, ValueContainer> globalVariables) {
            this.globalVariables.clear();
            this.globalVariables.putAll(globalVariables);
            return this;
        }

        public Builder putGlobalVariable(String key,
                                         ValueContainer value) {
            this.globalVariables.put(key, value);
            return this;
        }

        public Builder putGlobalVariables(
                Map<String, ValueContainer> globalVariables) {
            this.globalVariables.putAll(globalVariables);
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
