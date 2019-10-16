package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * RealIsGreaterOrEqual - Instruction. Checks to see if the first argument has a real value greater than or equal to the
 * real value of the second argument.
 * <p>
 * RealIsGreaterOrEqual(Real subject, Real basis)
 */
public class RealIsGreaterOrEqual implements IProceduralValue {
    private IProceduralValue subject;
    private IProceduralValue basis;

    private RealIsGreaterOrEqual(IProceduralValue subject, IProceduralValue basis) {
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
        return visitor.visitRealIsGreaterOrEqual(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitRealIsGreaterOrEqual(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("subject", subject)
                .append("basis", basis)
                .toString();
    }

    public static RealIsGreaterOrEqual create(IProceduralValue subject,
                                              IProceduralValue basis) {
        if (subject == null)
            throw new NullPointerException("Subject cannot be null");
        if (basis == null)
            throw new NullPointerException("Basis cannot be null");
        return new RealIsGreaterOrEqual(subject, basis);
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

        public RealIsGreaterOrEqual build() {
            if (subject == null)
                throw new IllegalStateException("No subject specified");
            if (basis == null)
                throw new IllegalStateException("No basis specified");
            return new RealIsGreaterOrEqual(subject, basis);
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
