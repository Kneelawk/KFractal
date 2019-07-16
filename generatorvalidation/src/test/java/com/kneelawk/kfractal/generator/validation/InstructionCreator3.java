package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;

interface InstructionCreator3 {
    IInstruction create(IInstructionOutput output, IInstructionInput left, IInstructionInput right);
}
