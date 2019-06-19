package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;

public interface IInstruction {
	<R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException;
}
