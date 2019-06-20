package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * PointerFree - Instruction. Frees the data pointed to by the handle in the only argument. This also sets the pointer's
 * handle value to NullPointer.
 * <p>
 * PointerFree(Pointer(*) pointer)
 */
public class PointerFree implements IInstruction {
	private IInstructionInput pointer;

	private PointerFree(IInstructionInput pointer) {
		this.pointer = pointer;
	}

	public IInstructionInput getPointer() {
		return pointer;
	}

	@Override
	public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
		return visitor.visitPointerFree(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("pointer", pointer)
				.toString();
	}

	public static class Builder {
		private IInstructionInput pointer;

		public Builder() {
		}

		public Builder(IInstructionInput pointer) {
			this.pointer = pointer;
		}

		public PointerFree build() {
			return new PointerFree(pointer);
		}

		public IInstructionInput getPointer() {
			return pointer;
		}

		public Builder setPointer(IInstructionInput pointer) {
			this.pointer = pointer;
			return this;
		}
	}
}
