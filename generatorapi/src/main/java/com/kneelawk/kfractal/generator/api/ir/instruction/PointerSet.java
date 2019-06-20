package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * PointerSet - Instruction. Sets the value of the data pointed to by the handle in the first argument to the data in
 * the second argument.
 * <p>
 * PointerSet(Pointer(*) pointer, * data)
 */
public class PointerSet implements IInstruction {
	private IInstructionInput pointer;
	private IInstructionInput data;

	private PointerSet(IInstructionInput pointer,
					   IInstructionInput data) {
		this.pointer = pointer;
		this.data = data;
	}

	public IInstructionInput getPointer() {
		return pointer;
	}

	public IInstructionInput getData() {
		return data;
	}

	@Override
	public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
		return visitor.visitPointerSet(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("pointer", pointer)
				.append("data", data)
				.toString();
	}

	public static class Builder {
		private IInstructionInput pointer;
		private IInstructionInput data;

		public Builder() {
		}

		public Builder(IInstructionInput pointer,
					   IInstructionInput data) {
			this.pointer = pointer;
			this.data = data;
		}

		public PointerSet build() {
			return new PointerSet(pointer, data);
		}

		public IInstructionInput getPointer() {
			return pointer;
		}

		public Builder setPointer(IInstructionInput pointer) {
			this.pointer = pointer;
			return this;
		}

		public IInstructionInput getData() {
			return data;
		}

		public Builder setData(IInstructionInput data) {
			this.data = data;
			return this;
		}
	}
}
