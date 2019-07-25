package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntMultiply - Instruction. Multiplies the last two arguments together and stores the result in the variable
 * referenced by the first argument.
 * <p>
 * IntMultiply(Int product, Int leftFactor, Int rightFactor)
 */
public class IntMultiply implements IInstruction {
    private IInstructionOutput product;
    private IInstructionInput leftFactor;
    private IInstructionInput rightFactor;

    private IntMultiply(IInstructionOutput product,
                        IInstructionInput leftFactor,
                        IInstructionInput rightFactor) {
        this.product = product;
        this.leftFactor = leftFactor;
        this.rightFactor = rightFactor;
    }

    public IInstructionOutput getProduct() {
        return product;
    }

    public IInstructionInput getLeftFactor() {
        return leftFactor;
    }

    public IInstructionInput getRightFactor() {
        return rightFactor;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitIntMultiply(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("product", product)
                .append("leftFactor", leftFactor)
                .append("rightFactor", rightFactor)
                .toString();
    }

    public static IntMultiply create(IInstructionOutput product,
                                     IInstructionInput leftFactor,
                                     IInstructionInput rightFactor) {
        if (product == null)
            throw new NullPointerException("Product cannot be null");
        if (leftFactor == null)
            throw new NullPointerException("LeftFactor cannot be null");
        if (rightFactor == null)
            throw new NullPointerException("RightFactor cannot be null");
        return new IntMultiply(product, leftFactor, rightFactor);
    }

    public static class Builder {
        private IInstructionOutput product;
        private IInstructionInput leftFactor;
        private IInstructionInput rightFactor;

        public Builder() {
        }

        public Builder(IInstructionOutput product,
                       IInstructionInput leftFactor,
                       IInstructionInput rightFactor) {
            this.product = product;
            this.leftFactor = leftFactor;
            this.rightFactor = rightFactor;
        }

        public IntMultiply build() {
            if (product == null)
                throw new IllegalStateException("No product specified");
            if (leftFactor == null)
                throw new IllegalStateException("No leftFactor specified");
            if (rightFactor == null)
                throw new IllegalStateException("No rightFactor specified");
            return new IntMultiply(product, leftFactor, rightFactor);
        }

        public IInstructionOutput getProduct() {
            return product;
        }

        public Builder setProduct(IInstructionOutput product) {
            this.product = product;
            return this;
        }

        public IInstructionInput getLeftFactor() {
            return leftFactor;
        }

        public Builder setLeftFactor(
                IInstructionInput leftFactor) {
            this.leftFactor = leftFactor;
            return this;
        }

        public IInstructionInput getRightFactor() {
            return rightFactor;
        }

        public Builder setRightFactor(
                IInstructionInput rightFactor) {
            this.rightFactor = rightFactor;
            return this;
        }
    }
}
