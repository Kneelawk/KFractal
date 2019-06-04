package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;

public class FunctionDefinition {
	private String name;
	private ValueType returnType;
	private Map<String, VariableDeclaration> contextVariables;
	private List<VariableDeclaration> contextVariableList;
	private Map<String, VariableDeclaration> arguments;
	private List<VariableDeclaration> argumentList;
	private Map<String, VariableDeclaration> localVariables;
	private List<IInstruction> body;

	private FunctionDefinition(String name, ValueType returnType,
							   Map<String, VariableDeclaration> contextVariables,
							   List<VariableDeclaration> contextVariableList,
							   Map<String, VariableDeclaration> arguments,
							   List<VariableDeclaration> argumentList,
							   Map<String, VariableDeclaration> localVariables,
							   List<IInstruction> body) {
		this.name = name;
		this.returnType = returnType;
		this.contextVariables = contextVariables;
		this.contextVariableList = contextVariableList;
		this.arguments = arguments;
		this.argumentList = argumentList;
		this.localVariables = localVariables;
		this.body = body;
	}

	public String getName() {
		return name;
	}

	public ValueType getReturnType() {
		return returnType;
	}

	public Map<String, VariableDeclaration> getContextVariables() {
		return contextVariables;
	}

	public List<VariableDeclaration> getContextVariableList() {
		return contextVariableList;
	}

	public Map<String, VariableDeclaration> getArguments() {
		return arguments;
	}

	public List<VariableDeclaration> getArgumentList() {
		return argumentList;
	}

	public Map<String, VariableDeclaration> getLocalVariables() {
		return localVariables;
	}

	public List<IInstruction> getBody() {
		return body;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("name", name)
				.append("returnType", returnType)
				.append("contextVariables", contextVariables)
				.append("arguments", arguments)
				.append("localVariables", localVariables)
				.append("body", body)
				.toString();
	}

	public static class Builder {
		private String name;
		private ValueType returnType;
		private Map<String, VariableDeclaration> contextVariables = Maps.newLinkedHashMap();
		private Map<String, VariableDeclaration> arguments = Maps.newLinkedHashMap();
		private Map<String, VariableDeclaration> localVariables = Maps.newHashMap();
		private List<IInstruction> body = Lists.newArrayList();

		public Builder() {
		}

		public Builder(String name,
					   Map<String, VariableDeclaration> contextVariables,
					   Map<String, VariableDeclaration> arguments,
					   Map<String, VariableDeclaration> localVariables,
					   List<IInstruction> body) {
			this.name = name;
			this.contextVariables.putAll(contextVariables);
			this.arguments.putAll(arguments);
			this.localVariables.putAll(localVariables);
			this.body.addAll(body);
		}

		public FunctionDefinition build() {
			return new FunctionDefinition(name, returnType, ImmutableMap.copyOf(contextVariables),
					ImmutableList.copyOf(contextVariables.values()), ImmutableMap.copyOf(arguments),
					ImmutableList.copyOf(arguments.values()), ImmutableMap.copyOf(localVariables),
					ImmutableList.copyOf(body));
		}

		public String getName() {
			return name;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public ValueType getReturnType() {
			return returnType;
		}

		public Builder setReturnType(ValueType returnType) {
			this.returnType = returnType;
			return this;
		}

		public Map<String, VariableDeclaration> getContextVariables() {
			return contextVariables;
		}

		public Builder setContextVariables(
				Map<String, VariableDeclaration> contextVariables) {
			this.contextVariables.clear();
			this.contextVariables.putAll(contextVariables);
			return this;
		}

		public Builder addContextVariable(VariableDeclaration contextVariable) {
			contextVariables.put(contextVariable.getName(), contextVariable);
			return this;
		}

		public Map<String, VariableDeclaration> getArguments() {
			return arguments;
		}

		public Builder setArguments(
				Map<String, VariableDeclaration> arguments) {
			this.arguments.clear();
			this.arguments.putAll(arguments);
			return this;
		}

		public Builder addArgument(VariableDeclaration argument) {
			arguments.put(argument.getName(), argument);
			return this;
		}

		public Map<String, VariableDeclaration> getLocalVariables() {
			return localVariables;
		}

		public Builder setLocalVariables(
				Map<String, VariableDeclaration> localVariables) {
			this.localVariables.clear();
			this.localVariables.putAll(localVariables);
			return this;
		}

		public Builder addLocalVariable(VariableDeclaration localVariable) {
			localVariables.put(localVariable.getName(), localVariable);
			return this;
		}

		public List<IInstruction> getBody() {
			return body;
		}

		public Builder setBody(List<IInstruction> body) {
			this.body.clear();
			this.body.addAll(body);
			return this;
		}

		public Builder addStatement(IInstruction statement) {
			body.add(statement);
			return this;
		}
	}
}
