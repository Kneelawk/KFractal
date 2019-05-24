package com.kneelawk.kfractal.generator.api.language;

public interface IStatement {
	void accept(IStatementVisitor visitor);
}
