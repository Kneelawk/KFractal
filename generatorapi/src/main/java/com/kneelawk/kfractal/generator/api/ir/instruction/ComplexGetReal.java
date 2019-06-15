package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;

/**
 * ComplexGetReal - Instruction. Gets the real component of the last argument and stores it in the variable referenced
 * by the first argument.
 * <p>
 * ComplexGetReal(Real real, Complex complex)
 */
public class ComplexGetReal implements IInstruction {
	private IInstructionOutput real;
	private IInstructionInput complex;

	private ComplexGetReal(IInstructionOutput real,
						   IInstructionInput complex) {
		this.real = real;
		this.complex = complex;
	}

	public IInstructionOutput getReal() {
		return real;
	}

	public IInstructionInput getComplex() {
		return complex;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitComplexGetReal(this);
	}

	public static class Builder {
		private IInstructionOutput real;
		private IInstructionInput complex;

		public Builder() {
		}

		public Builder(IInstructionOutput real,
					   IInstructionInput complex) {
			this.real = real;
			this.complex = complex;
		}

		public ComplexGetReal build() {
			return new ComplexGetReal(real, complex);
		}

		public IInstructionOutput getReal() {
			return real;
		}

		public Builder setReal(IInstructionOutput real) {
			this.real = real;
			return this;
		}

		public IInstructionInput getComplex() {
			return complex;
		}

		public Builder setComplex(IInstructionInput complex) {
			this.complex = complex;
			return this;
		}
	}
}
