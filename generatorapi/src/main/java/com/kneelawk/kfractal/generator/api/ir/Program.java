package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Kneelawk on 5/25/19.
 */
public class Program {
    private List<VariableDeclaration> globalVariables;
    private List<FunctionDefinition> functions;

    private Program(List<VariableDeclaration> globalVariables,
                    List<FunctionDefinition> functions) {
        this.globalVariables = globalVariables;
        this.functions = functions;
    }

    public List<VariableDeclaration> getGlobalVariables() {
        return globalVariables;
    }

    public List<FunctionDefinition> getFunctions() {
        return functions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("globalVariables", globalVariables)
                .append("functions", functions)
                .toString();
    }

    public static Program create(
            List<VariableDeclaration> globalVariables,
            List<FunctionDefinition> functions) {
        return new Program(ImmutableList.copyOf(globalVariables), ImmutableList.copyOf(functions));
    }

    public static class Builder {
        private List<Supplier<VariableDeclaration>> globalVariables = Lists.newArrayList();
        private List<Supplier<FunctionDefinition>> functions = Lists.newArrayList();

        public Builder() {
        }

        public Builder(Collection<Supplier<VariableDeclaration>> globalVariables,
                       Collection<Supplier<FunctionDefinition>> functions) {
            this.globalVariables.addAll(globalVariables);
            this.functions.addAll(functions);
        }

        public Program build() {
            return new Program(globalVariables.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()),
                    functions.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()));
        }

        public List<Supplier<VariableDeclaration>> getGlobalVariables() {
            return globalVariables;
        }

        public VariableReference getNextGlobalVariableReference() {
            return VariableReference.create(Scope.GLOBAL, globalVariables.size());
        }

        public int getNextGlobalVariableIndex() {
            return globalVariables.size();
        }

        public Builder setGlobalVariableSuppliers(
                Collection<Supplier<VariableDeclaration>> globalVariables) {
            this.globalVariables.clear();
            this.globalVariables.addAll(globalVariables);
            return this;
        }

        public Builder setGlobalVariables(Collection<VariableDeclaration> globalVariables) {
            this.globalVariables.clear();
            this.globalVariables
                    .addAll(globalVariables.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public VariableReference addGlobalVariable(Supplier<VariableDeclaration> declaration) {
            globalVariables.add(declaration);
            return VariableReference.create(Scope.GLOBAL, globalVariables.size() - 1);
        }

        public VariableReference addGlobalVariable(VariableDeclaration declaration) {
            globalVariables.add(Suppliers.ofInstance(declaration));
            return VariableReference.create(Scope.GLOBAL, globalVariables.size() - 1);
        }

        public Builder addGlobalVariableSuppliers(Collection<Supplier<VariableDeclaration>> declarations) {
            globalVariables.addAll(declarations);
            return this;
        }

        public Builder addGlobalVariables(Collection<VariableDeclaration> declarations) {
            globalVariables.addAll(declarations.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public List<Supplier<FunctionDefinition>> getFunctions() {
            return functions;
        }

        public int getNextFunctionIndex() {
            return functions.size();
        }

        public Builder setFunctionSuppliers(
                Collection<Supplier<FunctionDefinition>> functions) {
            this.functions.clear();
            this.functions.addAll(functions);
            return this;
        }

        public Builder setFunctions(Collection<FunctionDefinition> functions) {
            this.functions.clear();
            this.functions.addAll(functions.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public int addFunction(Supplier<FunctionDefinition> definition) {
            functions.add(definition);
            return functions.size() - 1;
        }

        public int addFunction(FunctionDefinition definition) {
            functions.add(Suppliers.ofInstance(definition));
            return functions.size() - 1;
        }

        public Builder addFunctionSuppliers(Collection<Supplier<FunctionDefinition>> definitions) {
            functions.addAll(definitions);
            return this;
        }

        public Builder addFunctions(Collection<FunctionDefinition> definitions) {
            functions.addAll(definitions.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }
    }
}
