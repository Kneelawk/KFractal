package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * RealComposeComplex - Instruction. Composes a complex number with the first argument as its real component and the
 * second argument as its imaginary component.
 * <p>
 * RealComposeComplex(Real real, Real imaginary)
 */
public class RealComposeComplex implements IValue {
    private IValue real;
    private IValue imaginary;

    private RealComposeComplex(IValue real, IValue imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public IValue getReal() {
        return real;
    }

    public IValue getImaginary() {
        return imaginary;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitRealComposeComplex(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("real", real)
                .append("imaginary", imaginary)
                .toString();
    }

    public static RealComposeComplex create(IValue real,
                                            IValue imaginary) {
        if (real == null)
            throw new NullPointerException("Real cannot be null");
        if (imaginary == null)
            throw new NullPointerException("Imaginary cannot be null");
        return new RealComposeComplex(real, imaginary);
    }

    public static class Builder {
        private IValue real;
        private IValue imaginary;

        public Builder() {
        }

        public Builder(IValue real,
                       IValue imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }

        public RealComposeComplex build() {
            if (real == null)
                throw new IllegalStateException("No real specified");
            if (imaginary == null)
                throw new IllegalStateException("No imaginary specified");
            return new RealComposeComplex(real, imaginary);
        }

        public IValue getReal() {
            return real;
        }

        public Builder setReal(IValue real) {
            this.real = real;
            return this;
        }

        public IValue getImaginary() {
            return imaginary;
        }

        public Builder setImaginary(IValue imaginary) {
            this.imaginary = imaginary;
            return this;
        }
    }
}
