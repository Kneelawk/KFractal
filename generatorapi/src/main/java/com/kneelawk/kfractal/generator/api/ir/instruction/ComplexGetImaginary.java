package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexGetImaginary - Instruction. Gets the imaginary component of the last argument and stores it in the variable
 * referenced by the first argument.
 * <p>
 * ComplexGetImaginary(Real imaginary, Complex complex)
 */
public class ComplexGetImaginary implements IInstruction {
    private IInstructionOutput imaginary;
    private IInstructionInput complex;

    private ComplexGetImaginary(IInstructionOutput imaginary,
                                IInstructionInput complex) {
        this.imaginary = imaginary;
        this.complex = complex;
    }

    public IInstructionOutput getImaginary() {
        return imaginary;
    }

    public IInstructionInput getComplex() {
        return complex;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexGetImaginary(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("imaginary", imaginary)
                .append("complex", complex)
                .toString();
    }

    public static ComplexGetImaginary create(
            IInstructionOutput imaginary,
            IInstructionInput complex) {
        if (imaginary == null)
            throw new NullPointerException("Imaginary cannot be null");
        if (complex == null)
            throw new NullPointerException("Complex cannot be null");
        return new ComplexGetImaginary(imaginary, complex);
    }

    public static class Builder {
        private IInstructionOutput imaginary;
        private IInstructionInput complex;

        public Builder() {
        }

        public Builder(IInstructionOutput imaginary,
                       IInstructionInput complex) {
            this.imaginary = imaginary;
            this.complex = complex;
        }

        public ComplexGetImaginary build() {
            if (imaginary == null)
                throw new IllegalStateException("No imaginary specified");
            if (complex == null)
                throw new IllegalStateException("No complex specified");
            return new ComplexGetImaginary(imaginary, complex);
        }

        public IInstructionOutput getImaginary() {
            return imaginary;
        }

        public Builder setImaginary(
                IInstructionOutput imaginary) {
            this.imaginary = imaginary;
            return this;
        }

        public IInstructionInput getComplex() {
            return complex;
        }

        public Builder setComplex(IInstructionInput complex) {
            this.complex = complex;
            return this;
        }
    }
}
