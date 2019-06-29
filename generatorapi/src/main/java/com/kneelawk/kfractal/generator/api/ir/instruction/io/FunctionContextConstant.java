package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.List;

public class FunctionContextConstant implements IInstructionInput {
	private String functionName;
	private List<IInstructionInput> contextVariables;

	private FunctionContextConstant(String functionName, List<IInstructionInput> contextVariables) {
		this.functionName = functionName;
		this.contextVariables = contextVariables;
	}

	public String getFunctionName() {
		return functionName;
	}

	public List<IInstructionInput> getContextVariables() {
		return contextVariables;
	}

	@Override
	public <R> R accept(IInstructionInputVisitor<R> visitor) throws FractalIRException {
		return visitor.visitFunctionContextConstant(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("functionName", functionName)
				.append("contextVariables", contextVariables)
				.toString();
	}

	public static FunctionContextConstant create(String functionName, Iterable<IInstructionInput> contextVariables) {
		return new FunctionContextConstant(functionName, ImmutableList.copyOf(contextVariables));
	}

	public static class Builder {
		private String functionName;
		private List<IInstructionInput> contextVariables = Lists.newArrayList();

		public Builder() {
		}

		public Builder(String functionName, Collection<IInstructionInput> contextVariables) {
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

		public List<IInstructionInput> getContextVariables() {
			return contextVariables;
		}

		public Builder setContextVariables(Collection<IInstructionInput> contextVariables) {
			this.contextVariables.clear();
			this.contextVariables.addAll(contextVariables);
			return this;
		}

		public Builder addContextVariable(IInstructionInput contextVariable) {
			contextVariables.add(contextVariable);
			return this;
		}

		public Builder addContextVariables(Collection<IInstructionInput> contextVariables) {
			this.contextVariables.addAll(contextVariables);
			return this;
		}
	}
}
