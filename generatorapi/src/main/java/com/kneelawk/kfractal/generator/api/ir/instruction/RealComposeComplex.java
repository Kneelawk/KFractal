package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * RealComposeComplex - Instruction. Composes a complex number with the second to last argument as its real component
 * and the last argument as its imaginary component and stores it in the variable referenced by the first argument.
 * <p>
 * RealComposeComplex(Complex complex, Real real, Real imaginary)
 */
public class RealComposeComplex implements IInstruction {
    private IInstructionOutput complex;
    private IInstructionInput real;
    private IInstructionInput imaginary;

    private RealComposeComplex(IInstructionOutput complex,
                               IInstructionInput real,
                               IInstructionInput imaginary) {
        this.complex = complex;
        this.real = real;
        this.imaginary = imaginary;
    }

    public IInstructionOutput getComplex() {
        return complex;
    }

    public IInstructionInput getReal() {
        return real;
    }

    public IInstructionInput getImaginary() {
        return imaginary;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
        return visitor.visitRealComposeComplex(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("complex", complex)
                .append("real", real)
                .append("imaginary", imaginary)
                .toString();
    }

    public static RealComposeComplex create(
            IInstructionOutput complex,
            IInstructionInput real,
            IInstructionInput imaginary) {
        return new RealComposeComplex(complex, real, imaginary);
    }

    public static class Builder {
        private IInstructionOutput complex;
        private IInstructionInput real;
        private IInstructionInput imaginary;

        public Builder() {
        }

        public Builder(IInstructionOutput complex,
                       IInstructionInput real,
                       IInstructionInput imaginary) {
            this.complex = complex;
            this.real = real;
            this.imaginary = imaginary;
        }

        public RealComposeComplex build() {
            return new RealComposeComplex(complex, real, imaginary);
        }

        public IInstructionOutput getComplex() {
            return complex;
        }

        public Builder setComplex(IInstructionOutput complex) {
            this.complex = complex;
            return this;
        }

        public IInstructionInput getReal() {
            return real;
        }

        public Builder setReal(IInstructionInput real) {
            this.real = real;
            return this;
        }

        public IInstructionInput getImaginary() {
            return imaginary;
        }

        public Builder setImaginary(IInstructionInput imaginary) {
            this.imaginary = imaginary;
            return this;
        }
    }
}
