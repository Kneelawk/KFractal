package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntNot - Instruction. Finds the bitwise not of the argument.
 * <p>
 * IntNot(Int input)
 */
public class IntNot implements IValue {
    private IValue input;

    private IntNot(IValue input) {
        this.input = input;
    }

    public IValue getInput() {
        return input;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntNot(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("input", input)
                .toString();
    }

    public static IntNot create(IValue input) {
        if (input == null)
            throw new NullPointerException("Input cannot be null");
        return new IntNot(input);
    }

    public static class Builder {
        private IValue input;

        public Builder() {
        }

        public Builder(IValue input) {
            this.input = input;
        }

        public IntNot build() {
            if (input == null)
                throw new IllegalStateException("No input specified");
            return new IntNot(input);
        }

        public IValue getInput() {
            return input;
        }

        public Builder setInput(IValue input) {
            this.input = input;
            return this;
        }
    }
}
