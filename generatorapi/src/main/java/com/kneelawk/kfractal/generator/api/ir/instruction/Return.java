package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Return - Instruction. Returns a value or void constant from a function, exiting the current function.
 * <p>
 * Return(* returnValue)
 */
public class Return implements IInstruction {
    private IInstructionInput returnValue;

    private Return(IInstructionInput returnValue) {
        this.returnValue = returnValue;
    }

    public IInstructionInput getReturnValue() {
        return returnValue;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitReturn(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("returnValue", returnValue)
                .toString();
    }

    public static Return create(IInstructionInput returnValue) {
        return new Return(returnValue);
    }

    public static class Builder {
        private IInstructionInput returnValue;

        public Builder() {
        }

        public Builder(IInstructionInput returnValue) {
            this.returnValue = returnValue;
        }

        public Return build() {
            return new Return(returnValue);
        }

        public IInstructionInput getReturnValue() {
            return returnValue;
        }

        public Builder setReturnValue(
                IInstructionInput returnValue) {
            this.returnValue = returnValue;
            return this;
        }
    }
}
