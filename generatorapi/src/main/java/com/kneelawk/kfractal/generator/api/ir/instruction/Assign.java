package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Assign - Instruction. Copies the value of the last argument into the first argument. The first and last argument must
 * represent the same types of values.
 * <p>
 * Assign(* dest, * source)
 */
public class Assign implements IInstruction {
	private IInstructionOutput dest;
	private IInstructionInput source;

	private Assign(IInstructionOutput dest, IInstructionInput source) {
		this.dest = dest;
		this.source = source;
	}

	public IInstructionOutput getDest() {
		return dest;
	}

	public IInstructionInput getSource() {
		return source;
	}

	@Override
	public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
		return visitor.visitAssign(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("dest", dest)
				.append("source", source)
				.toString();
	}

	public static Assign create(IInstructionOutput dest, IInstructionInput source) {
		return new Assign(dest, source);
	}

	public static class Builder {
		private IInstructionOutput dest;
		private IInstructionInput source;

		public Builder() {
		}

		public Builder(IInstructionOutput dest, IInstructionInput source) {
			this.dest = dest;
			this.source = source;
		}

		public Assign build() {
			return new Assign(dest, source);
		}

		public IInstructionOutput getDest() {
			return dest;
		}

		public Builder setDest(IInstructionOutput dest) {
			this.dest = dest;
			return this;
		}

		public IInstructionInput getSource() {
			return source;
		}

		public Builder setSource(IInstructionInput source) {
			this.source = source;
			return this;
		}
	}
}
