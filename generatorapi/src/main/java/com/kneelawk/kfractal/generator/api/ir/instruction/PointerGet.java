package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * PointerGet - Instruction. Retrieves the data pointed to by the handle in the second argument and stores it in the
 * variable referenced by the first argument.
 * <p>
 * PointerGet(* data, Pointer(*) pointer)
 */
public class PointerGet implements IInstruction {
    private IInstructionOutput data;
    private IInstructionInput pointer;

    private PointerGet(IInstructionOutput data,
                       IInstructionInput pointer) {
        this.data = data;
        this.pointer = pointer;
    }

    public IInstructionOutput getData() {
        return data;
    }

    public IInstructionInput getPointer() {
        return pointer;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
        return visitor.visitPointerGet(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("data", data)
                .append("pointer", pointer)
                .toString();
    }

    public static PointerGet create(IInstructionOutput data,
                                    IInstructionInput pointer) {
        return new PointerGet(data, pointer);
    }

    public static class Builder {
        private IInstructionOutput data;
        private IInstructionInput pointer;

        public Builder() {
        }

        public Builder(IInstructionOutput data,
                       IInstructionInput pointer) {
            this.data = data;
            this.pointer = pointer;
        }

        public PointerGet build() {
            return new PointerGet(data, pointer);
        }

        public IInstructionOutput getData() {
            return data;
        }

        public Builder setData(IInstructionOutput data) {
            this.data = data;
            return this;
        }

        public IInstructionInput getPointer() {
            return pointer;
        }

        public Builder setPointer(IInstructionInput pointer) {
            this.pointer = pointer;
            return this;
        }
    }
}
