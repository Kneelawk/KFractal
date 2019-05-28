package com.kneelawk.kfractal.generator.api.ir;

public interface IStatement {
	void accept(IStatementVisitor visitor);
}
