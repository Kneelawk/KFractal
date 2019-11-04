package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.Program;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class ValidatingVisitorContext {
    private final Set<IValue> traversedNodes;
    private final Program program;
    private final FunctionDefinition function;
    private final int blockIndex;
    private final int instructionIndex;

    private ValidatingVisitorContext(Set<IValue> traversedNodes, Program program,
                                     FunctionDefinition function, int blockIndex, int instructionIndex) {
        this.traversedNodes = traversedNodes;
        this.program = program;
        this.function = function;
        this.blockIndex = blockIndex;
        this.instructionIndex = instructionIndex;
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

    public int getBlockIndex() {
        return blockIndex;
    }

    public int getInstructionIndex() {
        return instructionIndex;
    }

    public Builder builder() {
        return new Builder(traversedNodes, program, function, blockIndex, instructionIndex);
    }

    public static ValidatingVisitorContext create(Iterable<IValue> traversedNodes,
                                                  Program program,
                                                  FunctionDefinition function,
                                                  int blockIndex, int instructionIndex) {
        if (program == null)
            throw new NullPointerException("Program cannot be null");
        if (function == null)
            throw new NullPointerException("Function cannot be null");
        return new ValidatingVisitorContext(ImmutableSet.copyOf(traversedNodes), program,
                function, blockIndex, instructionIndex);
    }

    public static class Builder {
        private Set<IValue> traversedNodes = Sets.newHashSet();
        private Program program;
        private FunctionDefinition function;
        private int blockIndex;
        private int instructionIndex;

        public Builder() {
        }

        public Builder(Collection<IValue> traversedNodes,
                       Program program,
                       FunctionDefinition function, int blockIndex,
                       int instructionIndex) {
            this.traversedNodes.addAll(traversedNodes);
            this.program = program;
            this.function = function;
            this.blockIndex = blockIndex;
            this.instructionIndex = instructionIndex;
        }

        public ValidatingVisitorContext build() {
            if (program == null)
                throw new IllegalStateException("No program specified");
            if (function == null)
                throw new IllegalStateException("No function specified");
            return new ValidatingVisitorContext(ImmutableSet.copyOf(traversedNodes), program,
                    function, blockIndex, instructionIndex);
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

        public int getBlockIndex() {
            return blockIndex;
        }

        public Builder setBlockIndex(int blockIndex) {
            this.blockIndex = blockIndex;
            return this;
        }

        public int getInstructionIndex() {
            return instructionIndex;
        }

        public Builder setInstructionIndex(int instructionIndex) {
            this.instructionIndex = instructionIndex;
            return this;
        }
    }
}
