package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * PointerAllocate - Instruction. Allocates data for a pointer on the language-backed heap and stores its handle in the
 * variable referenced by the only argument.
 * <p>
 * PointerAllocate(Pointer(*) pointer)
 */
public class PointerAllocate implements IInstruction {
    private IInstructionOutput pointer;

    private PointerAllocate(IInstructionOutput pointer) {
        this.pointer = pointer;
    }

    public IInstructionOutput getPointer() {
        return pointer;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitPointerAllocate(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("pointer", pointer)
                .toString();
    }

    public static PointerAllocate create(
            IInstructionOutput pointer) {
        return new PointerAllocate(pointer);
    }

    public static class Builder {
        private IInstructionOutput pointer;

        public Builder() {
        }

        public Builder(IInstructionOutput pointer) {
            this.pointer = pointer;
        }

        public PointerAllocate build() {
            return new PointerAllocate(pointer);
        }

        public IInstructionOutput getPointer() {
            return pointer;
        }

        public Builder setPointer(IInstructionOutput pointer) {
            this.pointer = pointer;
            return this;
        }
    }
}
