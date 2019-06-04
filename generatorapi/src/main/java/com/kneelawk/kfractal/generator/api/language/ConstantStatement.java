package com.kneelawk.kfractal.generator.api.language;

import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.IInstruction;
import com.kneelawk.kfractal.generator.api.ir.IInstructionVisitor;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import org.apache.commons.math3.complex.Complex;

public class ConstantStatement implements IInstruction {
	private ValueType type;
	private boolean boolValue;
	private Complex complexValue;
	private FunctionDefinition functionValue;

	private ConstantStatement(ValueType type, boolean boolValue, Complex complexValue, FunctionDefinition functionValue) {
		this.type = type;
		this.boolValue = boolValue;
		this.complexValue = complexValue;
		this.functionValue = functionValue;
	}

	public ValueType getType() {
		return type;
	}

	public boolean isBoolValue() {
		return boolValue;
	}

	public Complex getComplexValue() {
		return complexValue;
	}

	public FunctionDefinition getFunctionValue() {
		return functionValue;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitConstant(this);
	}

	public static class Builder {
		private ValueType type;
		private boolean boolValue;
		private Complex complexValue;
		private FunctionDefinition functionValue;

		public Builder() {
		}

		public Builder(ValueType type, boolean boolValue, Complex complexValue, FunctionDefinition functionValue) {
			this.type = type;
			this.boolValue = boolValue;
			this.complexValue = complexValue;
			this.functionValue = functionValue;
		}

		public ConstantStatement build() {
			return new ConstantStatement(type, boolValue, complexValue, functionValue);
		}

		public ValueType getType() {
			return type;
		}

		public Builder setType(ValueType type) {
			this.type = type;
			return this;
		}

		public boolean isBoolValue() {
			return boolValue;
		}

		public Builder setBoolValue(boolean boolValue) {
			this.boolValue = boolValue;
			return this;
		}

		public Complex getComplexValue() {
			return complexValue;
		}

		public Builder setComplexValue(Complex complexValue) {
			this.complexValue = complexValue;
			return this;
		}

		public FunctionDefinition getFunctionValue() {
			return functionValue;
		}

		public Builder setFunctionValue(FunctionDefinition functionValue) {
			this.functionValue = functionValue;
			return this;
		}
	}
}
