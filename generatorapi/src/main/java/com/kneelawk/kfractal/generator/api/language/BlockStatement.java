package com.kneelawk.kfractal.generator.api.language;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.IStatement;
import com.kneelawk.kfractal.generator.api.ir.IStatementVisitor;

import java.util.List;

public class BlockStatement implements IStatement {
	private List<IStatement> statements;

	private BlockStatement(List<IStatement> statements) {
		this.statements = statements;
	}

	public List<IStatement> getStatements() {
		return statements;
	}

	@Override
	public void accept(IStatementVisitor visitor) {
		visitor.visitBlockStatement(this);
	}

	public static class Builder {
		List<IStatement> statements = Lists.newArrayList();

		public Builder() {
		}

		public Builder(List<IStatement> statements) {
			this.statements = statements;
		}

		public BlockStatement build() {
			return new BlockStatement(ImmutableList.copyOf(statements));
		}

		public List<IStatement> getStatements() {
			return statements;
		}

		public Builder setStatements(List<IStatement> statements) {
			this.statements = statements;
			return this;
		}

		public Builder addStatement(IStatement statement) {
			statements.add(statement);
			return this;
		}
	}
}
