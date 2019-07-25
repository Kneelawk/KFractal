package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FunctionDefinition {
    private ValueType returnType;
    private List<VariableDeclaration> contextVariables;
    private List<VariableDeclaration> arguments;
    private List<VariableDeclaration> localVariables;
    private List<IInstruction> body;

    private FunctionDefinition(ValueType returnType,
                               List<VariableDeclaration> contextVariables,
                               List<VariableDeclaration> arguments,
                               List<VariableDeclaration> localVariables,
                               List<IInstruction> body) {
        this.returnType = returnType;
        this.contextVariables = contextVariables;
        this.arguments = arguments;
        this.localVariables = localVariables;
        this.body = body;
    }

    public ValueType getReturnType() {
        return returnType;
    }

    public List<VariableDeclaration> getContextVariables() {
        return contextVariables;
    }

    public List<VariableDeclaration> getArguments() {
        return arguments;
    }

    public List<VariableDeclaration> getLocalVariables() {
        return localVariables;
    }

    public List<IInstruction> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("returnType", returnType)
                .append("contextVariables", contextVariables)
                .append("arguments", arguments)
                .append("localVariables", localVariables)
                .append("body", body)
                .toString();
    }

    public static class Builder {
        private ValueType returnType;
        private List<Supplier<VariableDeclaration>> contextVariables = Lists.newArrayList();
        private List<Supplier<VariableDeclaration>> arguments = Lists.newArrayList();
        private List<Supplier<VariableDeclaration>> localVariables = Lists.newArrayList();
        private List<IInstruction> body = Lists.newArrayList();

        public Builder() {
        }

        public Builder(ValueType returnType,
                       Collection<Supplier<VariableDeclaration>> contextVariables,
                       Collection<Supplier<VariableDeclaration>> arguments,
                       Collection<Supplier<VariableDeclaration>> localVariables,
                       Collection<IInstruction> body) {
            this.returnType = returnType;
            this.contextVariables.addAll(contextVariables);
            this.arguments.addAll(arguments);
            this.localVariables.addAll(localVariables);
            this.body.addAll(body);
        }

        public FunctionDefinition build() {
            if (returnType == null)
                throw new IllegalStateException("No returnType specified");
            return new FunctionDefinition(returnType,
                    contextVariables.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()),
                    arguments.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()),
                    localVariables.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()),
                    ImmutableList.copyOf(body));
        }

        public ValueType getReturnType() {
            return returnType;
        }

        public Builder setReturnType(ValueType returnType) {
            this.returnType = returnType;
            return this;
        }

        public List<Supplier<VariableDeclaration>> getContextVariables() {
            return contextVariables;
        }

        public VariableReference getNextContextVariableReference() {
            return VariableReference.create(Scope.CONTEXT, contextVariables.size());
        }

        public int getNextContextVariableIndex() {
            return contextVariables.size();
        }

        public Builder setContextVariableSuppliers(
                Collection<Supplier<VariableDeclaration>> contextVariables) {
            this.contextVariables.clear();
            this.contextVariables.addAll(contextVariables);
            return this;
        }

        public Builder setContextVariables(Collection<VariableDeclaration> contextVariables) {
            this.contextVariables.clear();
            this.contextVariables
                    .addAll(contextVariables.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public VariableReference addContextVariable(Supplier<VariableDeclaration> declaration) {
            contextVariables.add(declaration);
            return VariableReference.create(Scope.CONTEXT, contextVariables.size() - 1);
        }

        public VariableReference addContextVariable(VariableDeclaration declaration) {
            contextVariables.add(Suppliers.ofInstance(declaration));
            return VariableReference.create(Scope.CONTEXT, contextVariables.size() - 1);
        }

        @SafeVarargs
        public final Builder addContextVariables(Supplier<VariableDeclaration>... declarations) {
            contextVariables.addAll(Arrays.asList(declarations));
            return this;
        }

        public final Builder addContextVariables(VariableDeclaration... declarations) {
            contextVariables
                    .addAll(Arrays.stream(declarations).map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public Builder addContextVariableSuppliers(Collection<Supplier<VariableDeclaration>> declarations) {
            contextVariables.addAll(declarations);
            return this;
        }

        public Builder addContextVariables(Collection<VariableDeclaration> declarations) {
            contextVariables.addAll(declarations.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public List<Supplier<VariableDeclaration>> getArguments() {
            return arguments;
        }

        public VariableReference getNextArgumentReference() {
            return VariableReference.create(Scope.ARGUMENTS, arguments.size());
        }

        public int getNextArgumentIndex() {
            return arguments.size();
        }

        public Builder setArgumentSuppliers(
                Collection<Supplier<VariableDeclaration>> arguments) {
            this.arguments.clear();
            this.arguments.addAll(arguments);
            return this;
        }

        public Builder setArguments(Collection<VariableDeclaration> arguments) {
            this.arguments.clear();
            this.arguments.addAll(arguments.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public VariableReference addArgument(Supplier<VariableDeclaration> declaration) {
            arguments.add(declaration);
            return VariableReference.create(Scope.ARGUMENTS, arguments.size() - 1);
        }

        public VariableReference addArgument(VariableDeclaration declaration) {
            arguments.add(Suppliers.ofInstance(declaration));
            return VariableReference.create(Scope.ARGUMENTS, arguments.size() - 1);
        }

        @SafeVarargs
        public final Builder addArguments(Supplier<VariableDeclaration>... declarations) {
            arguments.addAll(Arrays.asList(declarations));
            return this;
        }

        public Builder addArguments(VariableDeclaration... declarations) {
            arguments.addAll(Arrays.stream(declarations).map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public Builder addArgumentSuppliers(Collection<Supplier<VariableDeclaration>> declarations) {
            arguments.addAll(declarations);
            return this;
        }

        public Builder addArguments(Collection<VariableDeclaration> declarations) {
            arguments.addAll(declarations.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public List<Supplier<VariableDeclaration>> getLocalVariables() {
            return localVariables;
        }

        public VariableReference getNextLocalVariableReference() {
            return VariableReference.create(Scope.LOCAL, localVariables.size());
        }

        public int getNextLocalVariableIndex() {
            return localVariables.size();
        }

        public Builder setLocalVariableSuppliers(
                Collection<Supplier<VariableDeclaration>> localVariables) {
            this.localVariables.clear();
            this.localVariables.addAll(localVariables);
            return this;
        }

        public Builder setLocalVariables(Collection<VariableDeclaration> localVariables) {
            this.localVariables.clear();
            this.localVariables.addAll(localVariables.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public VariableReference addLocalVariable(Supplier<VariableDeclaration> declaration) {
            localVariables.add(declaration);
            return VariableReference.create(Scope.LOCAL, localVariables.size() - 1);
        }

        public VariableReference addLocalVariable(VariableDeclaration declaration) {
            localVariables.add(Suppliers.ofInstance(declaration));
            return VariableReference.create(Scope.LOCAL, localVariables.size() - 1);
        }

        @SafeVarargs
        public final Builder addLocalVariables(Supplier<VariableDeclaration>... declarations) {
            localVariables.addAll(Arrays.asList(declarations));
            return this;
        }

        public Builder addLocalVariables(VariableDeclaration... declarations) {
            localVariables.addAll(Arrays.stream(declarations).map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public Builder addLocalVariableSuppliers(Collection<Supplier<VariableDeclaration>> declarations) {
            localVariables.addAll(declarations);
            return this;
        }

        public Builder addLocalVariables(Collection<VariableDeclaration> declarations) {
            localVariables.addAll(declarations.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public List<IInstruction> getBody() {
            return body;
        }

        public Builder setBody(Collection<IInstruction> body) {
            this.body.clear();
            this.body.addAll(body);
            return this;
        }

        public Builder addStatement(IInstruction statement) {
            body.add(statement);
            return this;
        }

        public Builder addStatements(IInstruction... statements) {
            body.addAll(Arrays.asList(statements));
            return this;
        }

        public Builder addStatements(Collection<IInstruction> statements) {
            body.addAll(statements);
            return this;
        }
    }
}
