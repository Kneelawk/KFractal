package com.kneelawk.kfractal.generator.api.ir.instruction.io;

/**
 * Created by Kneelawk on 5/26/19.
 */
public interface IInstructionInput {
	void accept(IInstructionInputVisitor visitor);
}