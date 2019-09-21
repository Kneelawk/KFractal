package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.reference.VariableScope;
import com.kneelawk.kfractal.generator.api.ir.reference.VariableReference;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FunctionDefinition {
    private ValueType returnType;
    private List<ArgumentDeclaration> contextVariables;
    private List<ArgumentDeclaration> arguments;
    private List<BasicBlock> blocks;

    private FunctionDefinition(ValueType returnType,
                               List<ArgumentDeclaration> contextVariables,
                               List<ArgumentDeclaration> arguments,
                               List<BasicBlock> blocks) {
        this.returnType = returnType;
        this.contextVariables = contextVariables;
        this.arguments = arguments;
        this.blocks = blocks;
    }

    public ValueType getReturnType() {
        return returnType;
    }

    public List<ArgumentDeclaration> getContextVariables() {
        return contextVariables;
    }

    public List<ArgumentDeclaration> getArguments() {
        return arguments;
    }

    public List<BasicBlock> getBlocks() {
        return blocks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("returnType", returnType)
                .append("contextVariables", contextVariables)
                .append("arguments", arguments)
                .append("blocks", blocks)
                .toString();
    }

    public static class Builder {
        private ValueType returnType;
        private List<Supplier<ArgumentDeclaration>> contextVariables = Lists.newArrayList();
        private List<Supplier<ArgumentDeclaration>> arguments = Lists.newArrayList();
        private List<Supplier<BasicBlock>> blocks = Lists.newArrayList();

        public Builder() {
        }

        public Builder(ValueType returnType,
                       Collection<Supplier<ArgumentDeclaration>> contextVariables,
                       Collection<Supplier<ArgumentDeclaration>> arguments,
                       Collection<Supplier<BasicBlock>> blocks) {
            this.returnType = returnType;
            this.contextVariables.addAll(contextVariables);
            this.arguments.addAll(arguments);
            this.blocks.addAll(blocks);
        }

        public FunctionDefinition build() {
            if (returnType == null)
                throw new IllegalStateException("No returnType specified");
            return new FunctionDefinition(returnType,
                    contextVariables.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()),
                    arguments.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()),
                    blocks.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()));
        }

        public ValueType getReturnType() {
            return returnType;
        }

        public Builder setReturnType(ValueType returnType) {
            this.returnType = returnType;
            return this;
        }

        public List<Supplier<ArgumentDeclaration>> getContextVariables() {
            return contextVariables;
        }

        public VariableReference getNextContextVariableReference() {
            return VariableReference.create(VariableScope.CONTEXT, contextVariables.size());
        }

        public int getNextContextVariableIndex() {
            return contextVariables.size();
        }

        public Builder setContextVariableSuppliers(
                Collection<Supplier<ArgumentDeclaration>> contextVariables) {
            this.contextVariables.clear();
            this.contextVariables.addAll(contextVariables);
            return this;
        }

        public Builder setContextVariables(Collection<ArgumentDeclaration> contextVariables) {
            this.contextVariables.clear();
            this.contextVariables
                    .addAll(contextVariables.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public VariableReference addContextVariable(Supplier<ArgumentDeclaration> declaration) {
            contextVariables.add(declaration);
            return VariableReference.create(VariableScope.CONTEXT, contextVariables.size() - 1);
        }

        public VariableReference addContextVariable(ArgumentDeclaration declaration) {
            contextVariables.add(Suppliers.ofInstance(declaration));
            return VariableReference.create(VariableScope.CONTEXT, contextVariables.size() - 1);
        }

        @SafeVarargs
        public final Builder addContextVariables(Supplier<ArgumentDeclaration>... declarations) {
            contextVariables.addAll(Arrays.asList(declarations));
            return this;
        }

        public final Builder addContextVariables(ArgumentDeclaration... declarations) {
            contextVariables
                    .addAll(Arrays.stream(declarations).map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public Builder addContextVariableSuppliers(Collection<Supplier<ArgumentDeclaration>> declarations) {
            contextVariables.addAll(declarations);
            return this;
        }

        public Builder addContextVariables(Collection<ArgumentDeclaration> declarations) {
            contextVariables.addAll(declarations.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public List<Supplier<ArgumentDeclaration>> getArguments() {
            return arguments;
        }

        public VariableReference getNextArgumentReference() {
            return VariableReference.create(VariableScope.ARGUMENTS, arguments.size());
        }

        public int getNextArgumentIndex() {
            return arguments.size();
        }

        public Builder setArgumentSuppliers(
                Collection<Supplier<ArgumentDeclaration>> arguments) {
            this.arguments.clear();
            this.arguments.addAll(arguments);
            return this;
        }

        public Builder setArguments(Collection<ArgumentDeclaration> arguments) {
            this.arguments.clear();
            this.arguments.addAll(arguments.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public VariableReference addArgument(Supplier<ArgumentDeclaration> declaration) {
            arguments.add(declaration);
            return VariableReference.create(VariableScope.ARGUMENTS, arguments.size() - 1);
        }

        public VariableReference addArgument(ArgumentDeclaration declaration) {
            arguments.add(Suppliers.ofInstance(declaration));
            return VariableReference.create(VariableScope.ARGUMENTS, arguments.size() - 1);
        }

        @SafeVarargs
        public final Builder addArguments(Supplier<ArgumentDeclaration>... declarations) {
            arguments.addAll(Arrays.asList(declarations));
            return this;
        }

        public Builder addArguments(ArgumentDeclaration... declarations) {
            arguments.addAll(Arrays.stream(declarations).map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public Builder addArgumentSuppliers(Collection<Supplier<ArgumentDeclaration>> declarations) {
            arguments.addAll(declarations);
            return this;
        }

        public Builder addArguments(Collection<ArgumentDeclaration> declarations) {
            arguments.addAll(declarations.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public List<Supplier<BasicBlock>> getBlocks() {
            return blocks;
        }

        public int getNextBlockIndex() {
            return blocks.size();
        }

        public Builder setBlockSuppliers(Collection<Supplier<BasicBlock>> blocks) {
            this.blocks.clear();
            this.blocks.addAll(blocks);
            return this;
        }

        public Builder setBlocks(Collection<BasicBlock> blocks) {
            this.blocks.clear();
            this.blocks.addAll(blocks.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public int addBlock(Supplier<BasicBlock> block) {
            blocks.add(block);
            return blocks.size() - 1;
        }

        public int addBlock(BasicBlock block) {
            blocks.add(Suppliers.ofInstance(block));
            return blocks.size() - 1;
        }

        public BasicBlock.Builder addBlock() {
            BasicBlock.Builder builder = new BasicBlock.Builder(blocks.size());
            blocks.add(builder::build);
            return builder;
        }

        @SafeVarargs
        public final Builder addBlocks(Supplier<BasicBlock>... blocks) {
            this.blocks.addAll(Arrays.asList(blocks));
            return this;
        }

        public Builder addBlocks(BasicBlock... blocks) {
            this.blocks.addAll(Arrays.stream(blocks).map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public Builder addBlockSuppliers(Collection<Supplier<BasicBlock>> blocks) {
            this.blocks.addAll(blocks);
            return this;
        }

        public Builder addBlocks(Collection<BasicBlock> blocks) {
            this.blocks.addAll(blocks.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }
    }
}
