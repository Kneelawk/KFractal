package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionIO;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Assign - Instruction. Copies the value of the last argument into the first argument. The first and last argument must
 * represent the same types of values.
 *
 * Assign(? dest, ? source)
 */
public class Assign implements IInstruction {
	private IInstructionIO dest;
	private IInstructionIO source;

	private Assign(IInstructionIO dest, IInstructionIO source) {
		this.dest = dest;
		this.source = source;
	}

	public IInstructionIO getDest() {
		return dest;
	}

	public IInstructionIO getSource() {
		return source;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitAssign(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("dest", dest)
				.append("source", source)
				.toString();
	}

	public static class Builder {
		private IInstructionIO dest;
		private IInstructionIO source;

		public Builder() {
		}

		public Builder(IInstructionIO dest, IInstructionIO source) {
			this.dest = dest;
			this.source = source;
		}

		public Assign build() {
			return new Assign(dest, source);
		}

		public IInstructionIO getDest() {
			return dest;
		}

		public Builder setDest(IInstructionIO dest) {
			this.dest = dest;
			return this;
		}

		public IInstructionIO getSource() {
			return source;
		}

		public Builder setSource(IInstructionIO source) {
			this.source = source;
			return this;
		}
	}
}
