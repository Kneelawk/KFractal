package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Kneelawk on 5/25/19.
 */
public class Program {
    private List<GlobalDeclaration> globalVariables;
    private List<FunctionDefinition> functions;

    private Program(List<GlobalDeclaration> globalVariables,
                    List<FunctionDefinition> functions) {
        this.globalVariables = globalVariables;
        this.functions = functions;
    }

    public List<GlobalDeclaration> getGlobalVariables() {
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
            List<GlobalDeclaration> globalVariables,
            List<FunctionDefinition> functions) {
        return new Program(ImmutableList.copyOf(globalVariables), ImmutableList.copyOf(functions));
    }

    public static class Builder {
        private List<Supplier<GlobalDeclaration>> globalVariables = Lists.newArrayList();
        private List<Supplier<FunctionDefinition>> functions = Lists.newArrayList();

        public Builder() {
        }

        public Builder(Collection<Supplier<GlobalDeclaration>> globalVariables,
                       Collection<Supplier<FunctionDefinition>> functions) {
            this.globalVariables.addAll(globalVariables);
            this.functions.addAll(functions);
        }

        public Program build() {
            return new Program(globalVariables.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()),
                    functions.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()));
        }

        public List<Supplier<GlobalDeclaration>> getGlobalVariables() {
            return globalVariables;
        }

        public int getNextGlobalVariableIndex() {
            return globalVariables.size();
        }

        public Builder setGlobalVariableSuppliers(
                Collection<Supplier<GlobalDeclaration>> globalVariables) {
            this.globalVariables.clear();
            this.globalVariables.addAll(globalVariables);
            return this;
        }

        public Builder setGlobalVariables(Collection<GlobalDeclaration> globalVariables) {
            this.globalVariables.clear();
            this.globalVariables
                    .addAll(globalVariables.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public int addGlobalVariable(Supplier<GlobalDeclaration> declaration) {
            globalVariables.add(declaration);
            return globalVariables.size() - 1;
        }

        public int addGlobalVariable(GlobalDeclaration declaration) {
            globalVariables.add(Suppliers.ofInstance(declaration));
            return globalVariables.size() - 1;
        }

        @SafeVarargs
        public final Builder addGlobalVariables(Supplier<GlobalDeclaration>... declarations) {
            globalVariables.addAll(Arrays.asList(declarations));
            return this;
        }

        public final Builder addGlobalVariables(GlobalDeclaration... declarations) {
            globalVariables.addAll(Arrays.stream(declarations).map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public Builder addGlobalVariableSuppliers(Collection<Supplier<GlobalDeclaration>> declarations) {
            globalVariables.addAll(declarations);
            return this;
        }

        public Builder addGlobalVariables(Collection<GlobalDeclaration> declarations) {
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

        @SafeVarargs
        public final Builder addFunctions(Supplier<FunctionDefinition>... definitions) {
            functions.addAll(Arrays.asList(definitions));
            return this;
        }

        public Builder addFunctions(FunctionDefinition... definitions) {
            functions.addAll(Arrays.stream(definitions).map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
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
