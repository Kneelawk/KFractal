package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexModulo - Instruction. Computes the complex modulo of the last argument and stores it in the variable
 * referenced by the first argument. A complex number's modulus is that complex number's absolute distance from the
 * origin as computed by sqrt(&lt;real-component&gt;^2 + &lt;imaginary-component&gt;^2).
 * <p>
 * ComplexModulo(Real modulus, Complex complex)
 */
public class ComplexModulo implements IInstruction {
    private IInstructionOutput modulus;
    private IInstructionInput complex;

    private ComplexModulo(IInstructionOutput modulus,
                          IInstructionInput complex) {
        this.modulus = modulus;
        this.complex = complex;
    }

    public IInstructionOutput getModulus() {
        return modulus;
    }

    public IInstructionInput getComplex() {
        return complex;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexModulo(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("modulus", modulus)
                .append("complex", complex)
                .toString();
    }

    public static ComplexModulo create(IInstructionOutput modulus,
                                       IInstructionInput complex) {
        return new ComplexModulo(modulus, complex);
    }

    public static class Builder {
        private IInstructionOutput modulus;
        private IInstructionInput complex;

        public Builder() {
        }

        public Builder(IInstructionOutput modulus,
                       IInstructionInput complex) {
            this.modulus = modulus;
            this.complex = complex;
        }

        public ComplexModulo build() {
            return new ComplexModulo(modulus, complex);
        }

        public IInstructionOutput getModulus() {
            return modulus;
        }

        public Builder setModulus(IInstructionOutput modulus) {
            this.modulus = modulus;
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
