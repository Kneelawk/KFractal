package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntNot - Instruction. Finds the bitwise not of the argument.
 * <p>
 * IntNot(Int input)
 */
public class IntNot implements IProceduralValue {
    private IProceduralValue input;

    private IntNot(IProceduralValue input) {
        this.input = input;
    }

    public IProceduralValue getInput() {
        return input;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntNot(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitIntNot(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("input", input)
                .toString();
    }

    public static IntNot create(IProceduralValue input) {
        if (input == null)
            throw new NullPointerException("Input cannot be null");
        return new IntNot(input);
    }

    public static class Builder {
        private IProceduralValue input;

        public Builder() {
        }

        public Builder(IProceduralValue input) {
            this.input = input;
        }

        public IntNot build() {
            if (input == null)
                throw new IllegalStateException("No input specified");
            return new IntNot(input);
        }

        public IProceduralValue getInput() {
            return input;
        }

        public Builder setInput(IProceduralValue input) {
            this.input = input;
            return this;
        }
    }
}
