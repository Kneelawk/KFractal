package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class FunctionContextConstant implements IStatementIO {
	private String functionName;
	private List<IStatementIO> contextVariables;

	private FunctionContextConstant(String functionName, List<IStatementIO> contextVariables) {
		this.functionName = functionName;
		this.contextVariables = contextVariables;
	}

	public String getFunctionName() {
		return functionName;
	}

	public List<IStatementIO> getContextVariables() {
		return contextVariables;
	}

	@Override
	public void accept(IStatementIOVisitor visitor) {
		visitor.visitFunctionContextConstant(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("functionName", functionName)
				.append("contextVariables", contextVariables)
				.toString();
	}

	public static class Builder {
		private String functionName;
		private List<IStatementIO> contextVariables = Lists.newArrayList();

		public Builder() {
		}

		public Builder(String functionName, List<IStatementIO> contextVariables) {
			this.functionName = functionName;
			this.contextVariables.addAll(contextVariables);
		}

		public FunctionContextConstant build() {
			return new FunctionContextConstant(functionName, ImmutableList.copyOf(contextVariables));
		}

		public String getFunctionName() {
			return functionName;
		}

		public Builder setFunctionName(String functionName) {
			this.functionName = functionName;
			return this;
		}

		public List<IStatementIO> getContextVariables() {
			return contextVariables;
		}

		public Builder setContextVariables(List<IStatementIO> contextVariables) {
			this.contextVariables.clear();
			this.contextVariables.addAll(contextVariables);
			return this;
		}

		public Builder addContextVariable(IStatementIO contextVariable) {
			contextVariables.add(contextVariable);
			return this;
		}
	}
}
