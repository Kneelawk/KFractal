package com.kneelawk.kfractal.generator.api.language;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public class FunctionDefinition {
	private List<VariableDefinition> arguments;
	private IStatement body;

	private FunctionDefinition(List<VariableDefinition> arguments, IStatement body) {
		this.arguments = arguments;
		this.body = body;
	}

	public List<VariableDefinition> getArguments() {
		return arguments;
	}

	public IStatement getBody() {
		return body;
	}

	private static class Builder {
		private List<VariableDefinition> arguments = Lists.newArrayList();
		private IStatement body;

		public Builder() {
		}

		public Builder(List<VariableDefinition> arguments, IStatement body) {
			this.arguments = arguments;
			this.body = body;
		}

		public FunctionDefinition build() {
			return new FunctionDefinition(ImmutableList.copyOf(arguments), body);
		}

		public List<VariableDefinition> getArguments() {
			return arguments;
		}

		public Builder setArguments(List<VariableDefinition> arguments) {
			this.arguments = arguments;
			return this;
		}

		public Builder addArgument(VariableDefinition argument) {
			arguments.add(argument);
			return this;
		}

		public IStatement getBody() {
			return body;
		}

		public Builder setBody(IStatement body) {
			this.body = body;
			return this;
		}
	}
}
