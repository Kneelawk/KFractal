package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * RealIsGreater - Instruction. Checks to see if the first argument has a greater real value than the second argument.
 * <p>
 * RealIsGreater(Real subject, Real basis)
 */
public class RealIsGreater implements IProceduralValue {
    private IProceduralValue subject;
    private IProceduralValue basis;

    private RealIsGreater(IProceduralValue subject, IProceduralValue basis) {
        this.subject = subject;
        this.basis = basis;
    }

    public IProceduralValue getSubject() {
        return subject;
    }

    public IProceduralValue getBasis() {
        return basis;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitRealIsGreater(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitRealIsGreater(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("subject", subject)
                .append("basis", basis)
                .toString();
    }

    public static RealIsGreater create(IProceduralValue subject,
                                       IProceduralValue basis) {
        if (subject == null)
            throw new NullPointerException("Subject cannot be null");
        if (basis == null)
            throw new NullPointerException("Basis cannot be null");
        return new RealIsGreater(subject, basis);
    }

    public static class Builder {
        private IProceduralValue subject;
        private IProceduralValue basis;

        public Builder() {
        }

        public Builder(IProceduralValue subject,
                       IProceduralValue basis) {
            this.subject = subject;
            this.basis = basis;
        }

        public RealIsGreater build() {
            if (subject == null)
                throw new IllegalStateException("No subject specified");
            if (basis == null)
                throw new IllegalStateException("No basis specified");
            return new RealIsGreater(subject, basis);
        }

        public IProceduralValue getSubject() {
            return subject;
        }

        public Builder setSubject(IProceduralValue subject) {
            this.subject = subject;
            return this;
        }

        public IProceduralValue getBasis() {
            return basis;
        }

        public Builder setBasis(IProceduralValue basis) {
            this.basis = basis;
            return this;
        }
    }
}
