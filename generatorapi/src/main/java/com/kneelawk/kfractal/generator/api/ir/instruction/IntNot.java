package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntNot - Instruction. Finds the bitwise not of the last argument ans stores it in the variable referenced by the
 * first argument.
 * <p>
 * IntNot(Int output, Int input)
 */
public class IntNot implements IInstruction {
    private IInstructionOutput output;
    private IInstructionInput input;

    private IntNot(IInstructionOutput output, IInstructionInput input) {
        this.output = output;
        this.input = input;
    }

    public IInstructionOutput getOutput() {
        return output;
    }

    public IInstructionInput getInput() {
        return input;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitIntNot(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("output", output)
                .append("input", input)
                .toString();
    }

    public static IntNot create(IInstructionOutput output,
                                IInstructionInput input) {
        if (output == null)
            throw new NullPointerException("Output cannot be null");
        if (input == null)
            throw new NullPointerException("Input cannot be null");
        return new IntNot(output, input);
    }

    public static class Builder {
        private IInstructionOutput output;
        private IInstructionInput input;

        public Builder() {
        }

        public Builder(IInstructionOutput output,
                       IInstructionInput input) {
            this.output = output;
            this.input = input;
        }

        public IntNot build() {
            if (output == null)
                throw new IllegalStateException("No output specified");
            if (input == null)
                throw new IllegalStateException("No input specified");
            return new IntNot(output, input);
        }

        public IInstructionOutput getOutput() {
            return output;
        }

        public Builder setOutput(IInstructionOutput output) {
            this.output = output;
            return this;
        }

        public IInstructionInput getInput() {
            return input;
        }

        public Builder setInput(IInstructionInput input) {
            this.input = input;
            return this;
        }
    }
}
