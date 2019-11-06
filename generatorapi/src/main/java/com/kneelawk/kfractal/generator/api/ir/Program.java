package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Kneelawk on 5/25/19.
 */
public class Program {
    private Map<String, GlobalDeclaration> globalVariables;
    private Map<String, FunctionDefinition> functions;

    private Program(Map<String, GlobalDeclaration> globalVariables,
                    Map<String, FunctionDefinition> functions) {
        this.globalVariables = globalVariables;
        this.functions = functions;
    }

    public Map<String, GlobalDeclaration> getGlobalVariables() {
        return globalVariables;
    }

    public Map<String, FunctionDefinition> getFunctions() {
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
            Map<String, GlobalDeclaration> globalVariables,
            Map<String, FunctionDefinition> functions) {
        return new Program(ImmutableMap.copyOf(globalVariables), ImmutableMap.copyOf(functions));
    }

    public static class Builder {
        private Map<String, Supplier<GlobalDeclaration>> globalVariables = Maps.newHashMap();
        private Map<String, Supplier<FunctionDefinition>> functions = Maps.newHashMap();

        public Builder() {
        }

        public Builder(Map<String, Supplier<GlobalDeclaration>> globalVariables,
                       Map<String, Supplier<FunctionDefinition>> functions) {
            this.globalVariables.putAll(globalVariables);
            this.functions.putAll(functions);
        }

        public Program build() {
            return new Program(
                    globalVariables.entrySet().stream().map(e -> Pair.of(e.getKey(), e.getValue().get()))
                            .collect(ImmutableMap.toImmutableMap(Pair::getKey, Pair::getValue)),
                    functions.entrySet().stream().map(e -> Pair.of(e.getKey(), e.getValue().get()))
                            .collect(ImmutableMap.toImmutableMap(Pair::getKey, Pair::getValue)));
        }

        public Map<String, Supplier<GlobalDeclaration>> getGlobalVariables() {
            return globalVariables;
        }

        public Builder setGlobalVariableSuppliers(
                Collection<Map.Entry<String, Supplier<GlobalDeclaration>>> globalVariables) {
            this.globalVariables.clear();
            this.globalVariables.putAll(ImmutableMap.copyOf(globalVariables));
            return this;
        }

        public Builder setGlobalVariables(Collection<Map.Entry<String, GlobalDeclaration>> globalVariables) {
            this.globalVariables.clear();
            this.globalVariables.putAll(globalVariables.stream()
                    .map(e -> Pair.of(e.getKey(), Suppliers.ofInstance(e.getValue())))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
            return this;
        }

        public Builder addGlobalVariable(String name, Supplier<GlobalDeclaration> declaration) {
            globalVariables.put(name, declaration);
            return this;
        }

        public Builder addGlobalVariable(String name, GlobalDeclaration declaration) {
            globalVariables.put(name, Suppliers.ofInstance(declaration));
            return this;
        }

        @SafeVarargs
        public final Builder addGlobalVariableSuppliers(
                Map.Entry<String, Supplier<GlobalDeclaration>>... declarations) {
            globalVariables.putAll(Arrays.stream(declarations)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return this;
        }

        @SafeVarargs
        public final Builder addGlobalVariables(Map.Entry<String, GlobalDeclaration>... declarations) {
            globalVariables.putAll(Arrays.stream(declarations)
                    .map(p -> Pair.of(p.getKey(), Suppliers.ofInstance(p.getValue())))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
            return this;
        }

        public Builder addGlobalVariableSuppliers(
                Collection<Map.Entry<String, Supplier<GlobalDeclaration>>> declarations) {
            globalVariables.putAll(ImmutableMap.copyOf(declarations));
            return this;
        }

        public Builder addGlobalVariables(Collection<Map.Entry<String, GlobalDeclaration>> declarations) {
            globalVariables
                    .putAll(declarations.stream().map(e -> Pair.of(e.getKey(), Suppliers.ofInstance(e.getValue())))
                            .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
            return this;
        }

        public Map<String, Supplier<FunctionDefinition>> getFunctions() {
            return functions;
        }

        public Builder setFunctionSuppliers(
                Collection<Map.Entry<String, Supplier<FunctionDefinition>>> functions) {
            this.functions.clear();
            this.functions.putAll(ImmutableMap.copyOf(functions));
            return this;
        }

        public Builder setFunctions(Collection<Map.Entry<String, FunctionDefinition>> functions) {
            this.functions.clear();
            this.functions.putAll(functions.stream().map(e -> Pair.of(e.getKey(), Suppliers.ofInstance(e.getValue())))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
            return this;
        }

        public Builder addFunction(String name, Supplier<FunctionDefinition> definition) {
            functions.put(name, definition);
            return this;
        }

        public Builder addFunction(String name, FunctionDefinition definition) {
            functions.put(name, Suppliers.ofInstance(definition));
            return this;
        }

        @SafeVarargs
        public final Builder addFunctionSuppliers(Map.Entry<String, Supplier<FunctionDefinition>>... definitions) {
            functions.putAll(Arrays.stream(definitions)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return this;
        }

        @SafeVarargs
        public final Builder addFunctions(Map.Entry<String, FunctionDefinition>... definitions) {
            functions
                    .putAll(Arrays.stream(definitions).map(e -> Pair.of(e.getKey(), Suppliers.ofInstance(e.getValue())))
                            .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
            return this;
        }

        public Builder addFunctionSuppliers(Collection<Map.Entry<String, Supplier<FunctionDefinition>>> definitions) {
            functions.putAll(ImmutableMap.copyOf(definitions));
            return this;
        }

        public Builder addFunctions(Collection<Map.Entry<String, FunctionDefinition>> definitions) {
            functions.putAll(definitions.stream().map(e -> Pair.of(e.getKey(), Suppliers.ofInstance(e.getValue())))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
            return this;
        }
    }
}
