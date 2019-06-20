package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;

/**
 * Created by Kneelawk on 5/26/19.
 */
public interface IInstructionInput {
	<R> R accept(IInstructionInputVisitor<R> visitor) throws FractalIRException;
}
