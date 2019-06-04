package com.kneelawk.kfractal.generator.api.ir.instruction.io;

/**
 * Created by Kneelawk on 5/26/19.
 */
public interface IInstructionInputVisitor {
	void visitVariableReference(VariableReference reference);

	void visitBoolConstant(BoolConstant constant);

	void visitIntConstant(IntConstant constant);

	void visitRealConstant(RealConstant constant);

	void visitComplexConstant(ComplexConstant constant);

	void visitFunctionContextConstant(FunctionContextConstant contextConstant);

	void visitNullPointer();

	void visitVoid();
}
