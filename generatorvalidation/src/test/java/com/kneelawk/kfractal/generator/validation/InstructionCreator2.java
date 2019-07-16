package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;

public interface InstructionCreator2 {
    IInstruction create(IInstructionOutput output, IInstructionInput input);
}
