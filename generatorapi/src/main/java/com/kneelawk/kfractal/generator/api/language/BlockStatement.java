package com.kneelawk.kfractal.generator.api.language;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstructionVisitor;

import java.util.List;

public class BlockStatement implements IInstruction {
	private List<IInstruction> statements;

	private BlockStatement(List<IInstruction> statements) {
		this.statements = statements;
	}

	public List<IInstruction> getStatements() {
		return statements;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitBlockStatement(this);
	}

	public static class Builder {
		List<IInstruction> statements = Lists.newArrayList();

		public Builder() {
		}

		public Builder(List<IInstruction> statements) {
			this.statements = statements;
		}

		public BlockStatement build() {
			return new BlockStatement(ImmutableList.copyOf(statements));
		}

		public List<IInstruction> getStatements() {
			return statements;
		}

		public Builder setStatements(List<IInstruction> statements) {
			this.statements = statements;
			return this;
		}

		public Builder addStatement(IInstruction statement) {
			statements.add(statement);
			return this;
		}
	}
}
