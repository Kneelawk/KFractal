package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntIsGreater - Instruction. Checks to see if the second to last argument is greater than the last argument and stores
 * the resulting boolean in the variable referenced by the first argument.
 * <p>
 * IntIsGreater(Bool result, Int subject, Int basis)
 */
public class IntIsGreater implements IInstruction {
	private IInstructionOutput result;
	private IInstructionInput subject;
	private IInstructionInput basis;

	private IntIsGreater(IInstructionOutput result,
						 IInstructionInput subject,
						 IInstructionInput basis) {
		this.result = result;
		this.subject = subject;
		this.basis = basis;
	}

	public IInstructionOutput getResult() {
		return result;
	}

	public IInstructionInput getSubject() {
		return subject;
	}

	public IInstructionInput getBasis() {
		return basis;
	}

	@Override
	public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
		return visitor.visitIntIsGreater(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("result", result)
				.append("subject", subject)
				.append("basis", basis)
				.toString();
	}

	public static class Builder {
		private IInstructionOutput result;
		private IInstructionInput subject;
		private IInstructionInput basis;

		public Builder() {
		}

		public Builder(IInstructionOutput result,
					   IInstructionInput subject,
					   IInstructionInput basis) {
			this.result = result;
			this.subject = subject;
			this.basis = basis;
		}

		public IntIsGreater build() {
			return new IntIsGreater(result, subject, basis);
		}

		public IInstructionOutput getResult() {
			return result;
		}

		public Builder setResult(IInstructionOutput result) {
			this.result = result;
			return this;
		}

		public IInstructionInput getSubject() {
			return subject;
		}

		public Builder setSubject(IInstructionInput subject) {
			this.subject = subject;
			return this;
		}

		public IInstructionInput getBasis() {
			return basis;
		}

		public Builder setBasis(IInstructionInput basis) {
			this.basis = basis;
			return this;
		}
	}
}
