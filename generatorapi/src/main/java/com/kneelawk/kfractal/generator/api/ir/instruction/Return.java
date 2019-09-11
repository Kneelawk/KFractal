package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Return - Instruction. Returns a value or void constant from a function, exiting the current function.
 * <p>
 * Return(* returnValue)
 */
public class Return implements IValue {
    private IValue returnValue;

    private Return(IValue returnValue) {
        this.returnValue = returnValue;
    }

    public IValue getReturnValue() {
        return returnValue;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitReturn(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("returnValue", returnValue)
                .toString();
    }

    public static Return create(IValue returnValue) {
        if (returnValue == null)
            throw new NullPointerException("ReturnValue cannot be null");
        return new Return(returnValue);
    }

    public static class Builder {
        private IValue returnValue;

        public Builder() {
        }

        public Builder(IValue returnValue) {
            this.returnValue = returnValue;
        }

        public Return build() {
            if (returnValue == null)
                throw new IllegalStateException("No returnValue specified");
            return new Return(returnValue);
        }

        public IValue getReturnValue() {
            return returnValue;
        }

        public Builder setReturnValue(
                IValue returnValue) {
            this.returnValue = returnValue;
            return this;
        }
    }
}
