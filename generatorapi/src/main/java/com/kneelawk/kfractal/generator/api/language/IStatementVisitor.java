package com.kneelawk.kfractal.generator.api.language;

public interface IStatementVisitor {
	void visitBinaryOperation(BinaryOperationStatement statement);

	void visitBlockStatement(BlockStatement statement);

	void visitConstant(ConstantStatement statement);

	void visitFunctionCall(FunctionCallStatement statement);

	void visitVariable(VariableStatement statement);
}
