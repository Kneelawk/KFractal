package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexGetReal - Instruction. Gets the real component of the last argument and stores it in the variable referenced
 * by the first argument.
 * <p>
 * ComplexGetReal(Real real, Complex complex)
 */
public class ComplexGetReal implements IInstruction {
    private IInstructionOutput real;
    private IInstructionInput complex;

    private ComplexGetReal(IInstructionOutput real,
                           IInstructionInput complex) {
        this.real = real;
        this.complex = complex;
    }

    public IInstructionOutput getReal() {
        return real;
    }

    public IInstructionInput getComplex() {
        return complex;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexGetReal(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("real", real)
                .append("complex", complex)
                .toString();
    }

    public static ComplexGetReal create(IInstructionOutput real,
                                        IInstructionInput complex) {
        return new ComplexGetReal(real, complex);
    }

    public static class Builder {
        private IInstructionOutput real;
        private IInstructionInput complex;

        public Builder() {
        }

        public Builder(IInstructionOutput real,
                       IInstructionInput complex) {
            this.real = real;
            this.complex = complex;
        }

        public ComplexGetReal build() {
            return new ComplexGetReal(real, complex);
        }

        public IInstructionOutput getReal() {
            return real;
        }

        public Builder setReal(IInstructionOutput real) {
            this.real = real;
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
