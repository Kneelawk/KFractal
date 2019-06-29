package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * Created by Kneelawk on 5/25/19.
 */
public class Program {
	private Map<String, VariableDeclaration> globalVariables;
	private Map<String, FunctionDefinition> functions;

	private Program(
			Map<String, VariableDeclaration> globalVariables,
			Map<String, FunctionDefinition> functions) {
		this.globalVariables = globalVariables;
		this.functions = functions;
	}

	public Map<String, VariableDeclaration> getGlobalVariables() {
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

	public static Program create(Iterable<VariableDeclaration> globalVariables,
								 Iterable<FunctionDefinition> functions) {
		ImmutableMap.Builder<String, VariableDeclaration> globalVariablesBuilder = ImmutableMap.builder();
		for (VariableDeclaration v : globalVariables) {
			globalVariablesBuilder.put(v.getName(), v);
		}

		ImmutableMap.Builder<String, FunctionDefinition> functionsBuilder = ImmutableMap.builder();
		for (FunctionDefinition f : functions) {
			functionsBuilder.put(f.getName(), f);
		}

		return new Program(globalVariablesBuilder.build(), functionsBuilder.build());
	}

	public static class Builder {
		private Map<String, VariableDeclaration>
				globalVariables = Maps.newHashMap();
		private Map<String, FunctionDefinition> functions = Maps.newHashMap();

		public Builder() {
		}

		public Builder(
				Map<String, VariableDeclaration> globalVariables,
				Map<String, FunctionDefinition> functions) {
			this.globalVariables.putAll(globalVariables);
			this.functions.putAll(functions);
		}

		public Program build() {
			return new Program(globalVariables, functions);
		}

		public Map<String, VariableDeclaration> getGlobalVariables() {
			return globalVariables;
		}

		public Builder setGlobalVariables(
				Map<String, VariableDeclaration> globalVariables) {
			this.globalVariables = globalVariables;
			return this;
		}

		public Builder addGlobalVariable(VariableDeclaration globalVariable) {
			globalVariables.put(globalVariable.getName(), globalVariable);
			return this;
		}

		public Map<String, FunctionDefinition> getFunctions() {
			return functions;
		}

		public Builder setFunctions(
				Map<String, FunctionDefinition> functions) {
			this.functions = functions;
			return this;
		}

		public Builder addFunction(FunctionDefinition function) {
			functions.put(function.getName(), function);
			return this;
		}
	}
}
