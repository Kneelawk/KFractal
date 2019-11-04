package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.Program;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

class TypeLookupVisitorContext {
    private final Set<IValue> traversedNodes;
    private final Program program;
    private final FunctionDefinition function;

    private TypeLookupVisitorContext(Set<IValue> traversedNodes, Program program,
                                     FunctionDefinition function) {
        this.traversedNodes = traversedNodes;
        this.program = program;
        this.function = function;
    }

    public Set<IValue> getTraversedNodes() {
        return traversedNodes;
    }

    public Program getProgram() {
        return program;
    }

    public FunctionDefinition getFunction() {
        return function;
    }

    public Builder builder() {
        return new Builder(traversedNodes, program, function);
    }

    public static TypeLookupVisitorContext create(Iterable<IValue> traversedNodes,
                                                  Program program,
                                                  FunctionDefinition function) {
        if (program == null)
            throw new NullPointerException("Program cannot be null");
        if (function == null)
            throw new NullPointerException("Function cannot be null");
        return new TypeLookupVisitorContext(ImmutableSet.copyOf(traversedNodes), program,
                function);
    }

    public static class Builder {
        private Set<IValue> traversedNodes = Sets.newHashSet();
        private Program program;
        private FunctionDefinition function;

        public Builder() {
        }

        public Builder(Collection<IValue> traversedNodes,
                       Program program,
                       FunctionDefinition function) {
            this.traversedNodes.addAll(traversedNodes);
            this.program = program;
            this.function = function;
        }

        public TypeLookupVisitorContext build() {
            if (program == null)
                throw new IllegalStateException("No program specified");
            if (function == null)
                throw new IllegalStateException("No function specified");
            return new TypeLookupVisitorContext(ImmutableSet.copyOf(traversedNodes), program,
                    function);
        }

        public Set<IValue> getTraversedNodes() {
            return traversedNodes;
        }

        public Builder setTraversedNodes(Collection<IValue> traversedNodes) {
            this.traversedNodes.clear();
            this.traversedNodes.addAll(traversedNodes);
            return this;
        }

        public Builder addTraversedNode(IValue traversedNode) {
            traversedNodes.add(traversedNode);
            return this;
        }

        public Builder addTraversedNodes(IValue... traversedNodes) {
            this.traversedNodes.addAll(Arrays.asList(traversedNodes));
            return this;
        }

        public Builder addTraversedNodes(Collection<IValue> traversedNodes) {
            this.traversedNodes.addAll(traversedNodes);
            return this;
        }

        public Program getProgram() {
            return program;
        }

        public Builder setProgram(Program program) {
            this.program = program;
            return this;
        }

        public FunctionDefinition getFunction() {
            return function;
        }

        public Builder setFunction(FunctionDefinition function) {
            this.function = function;
            return this;
        }
    }
}
