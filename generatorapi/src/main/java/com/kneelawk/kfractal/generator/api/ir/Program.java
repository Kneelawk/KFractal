package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.List;

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
        private List<VariableDeclaration> globalVariables = Lists.newArrayList();
        private List<FunctionDefinition> functions = Lists.newArrayList();

        public Builder() {
        }

        public Builder(Collection<VariableDeclaration> globalVariables,
                       Collection<FunctionDefinition> functions) {
            this.globalVariables.addAll(globalVariables);
            this.functions.addAll(functions);
        }

        public Program build() {
            return new Program(ImmutableList.copyOf(globalVariables), ImmutableList.copyOf(functions));
        }

        public List<VariableDeclaration> getGlobalVariables() {
            return globalVariables;
        }

        public VariableReference getNextGlobalVariableReference() {
            return VariableReference.create(Scope.GLOBAL, globalVariables.size());
        }

        public int getNextGlobalVariableIndex() {
            return globalVariables.size();
        }

        public Builder setGlobalVariables(
                Collection<VariableDeclaration> globalVariables) {
            this.globalVariables.clear();
            this.globalVariables.addAll(globalVariables);
            return this;
        }

        public VariableReference addGlobalVariable(VariableDeclaration declaration) {
            globalVariables.add(declaration);
            return VariableReference.create(Scope.GLOBAL, globalVariables.size() - 1);
        }

        public Builder addGlobalVariables(Collection<VariableDeclaration> declarations) {
            globalVariables.addAll(declarations);
            return this;
        }

        public List<FunctionDefinition> getFunctions() {
            return functions;
        }

        public int getNextFunctionIndex() {
            return functions.size();
        }

        public Builder setFunctions(
                Collection<FunctionDefinition> functions) {
            this.functions.clear();
            this.functions.addAll(functions);
            return this;
        }

        public int addFunction(FunctionDefinition definition) {
            functions.add(definition);
            return functions.size() - 1;
        }

        public Builder addFunctions(List<FunctionDefinition> definitions) {
            functions.addAll(definitions);
            return this;
        }
    }
}
