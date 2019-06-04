package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class FunctionContextConstant implements IInstructionIO {
	private String functionName;
	private List<IInstructionIO> contextVariables;

	private FunctionContextConstant(String functionName, List<IInstructionIO> contextVariables) {
		this.functionName = functionName;
		this.contextVariables = contextVariables;
	}

	public String getFunctionName() {
		return functionName;
	}

	public List<IInstructionIO> getContextVariables() {
		return contextVariables;
	}

	@Override
	public void accept(IInstructionIOVisitor visitor) {
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
		private List<IInstructionIO> contextVariables = Lists.newArrayList();

		public Builder() {
		}

		public Builder(String functionName, List<IInstructionIO> contextVariables) {
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

		public List<IInstructionIO> getContextVariables() {
			return contextVariables;
		}

		public Builder setContextVariables(List<IInstructionIO> contextVariables) {
			this.contextVariables.clear();
			this.contextVariables.addAll(contextVariables);
			return this;
		}

		public Builder addContextVariable(IInstructionIO contextVariable) {
			contextVariables.add(contextVariable);
			return this;
		}
	}
}
