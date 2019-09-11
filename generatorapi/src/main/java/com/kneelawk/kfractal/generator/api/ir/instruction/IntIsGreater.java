package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntIsGreater - Instruction. Checks to see if the first argument is greater than the second argument.
 * <p>
 * IntIsGreater(Int subject, Int basis)
 */
public class IntIsGreater implements IValue {
    private IValue subject;
    private IValue basis;

    private IntIsGreater(IValue subject, IValue basis) {
        this.subject = subject;
        this.basis = basis;
    }

    public IValue getSubject() {
        return subject;
    }

    public IValue getBasis() {
        return basis;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntIsGreater(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("subject", subject)
                .append("basis", basis)
                .toString();
    }

    public static IntIsGreater create(IValue subject,
                                      IValue basis) {
        if (subject == null)
            throw new NullPointerException("Subject cannot be null");
        if (basis == null)
            throw new NullPointerException("Basis cannot be null");
        return new IntIsGreater(subject, basis);
    }

    public static class Builder {
        private IValue subject;
        private IValue basis;

        public Builder() {
        }

        public Builder(IValue subject,
                       IValue basis) {
            this.subject = subject;
            this.basis = basis;
        }

        public IntIsGreater build() {
            if (subject == null)
                throw new IllegalStateException("No subject specified");
            if (basis == null)
                throw new IllegalStateException("No basis specified");
            return new IntIsGreater(subject, basis);
        }

        public IValue getSubject() {
            return subject;
        }

        public Builder setSubject(IValue subject) {
            this.subject = subject;
            return this;
        }

        public IValue getBasis() {
            return basis;
        }

        public Builder setBasis(IValue basis) {
            this.basis = basis;
            return this;
        }
    }
}
