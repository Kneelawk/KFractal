package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * RealIsGreater - Instruction. Checks to see if the second to last argument has a greater real value than the last
 * argument and stores the resulting boolean in the variable referenced by the first argument.
 * <p>
 * RealIsGreater(Bool result, Real subject, Real basis)
 */
public class RealIsGreater implements IInstruction {
    private IInstructionOutput result;
    private IInstructionInput subject;
    private IInstructionInput basis;

    private RealIsGreater(IInstructionOutput result,
                          IInstructionInput subject,
                          IInstructionInput basis) {
        this.result = result;
        this.subject = subject;
        this.basis = basis;
    }

    public IInstructionOutput getResult() {
        return result;
    }

    public IInstructionInput getSubject() {
        return subject;
    }

    public IInstructionInput getBasis() {
        return basis;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitRealIsGreater(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("result", result)
                .append("subject", subject)
                .append("basis", basis)
                .toString();
    }

    public static RealIsGreater create(IInstructionOutput result,
                                       IInstructionInput subject,
                                       IInstructionInput basis) {
        if (result == null)
            throw new NullPointerException("Result cannot be null");
        if (subject == null)
            throw new NullPointerException("Subject cannot be null");
        if (basis == null)
            throw new NullPointerException("Basis cannot be null");
        return new RealIsGreater(result, subject, basis);
    }

    public static class Builder {
        private IInstructionOutput result;
        private IInstructionInput subject;
        private IInstructionInput basis;

        public Builder() {
        }

        public Builder(IInstructionOutput result,
                       IInstructionInput subject,
                       IInstructionInput basis) {
            this.result = result;
            this.subject = subject;
            this.basis = basis;
        }

        public RealIsGreater build() {
            if (result == null)
                throw new IllegalStateException("No result specified");
            if (subject == null)
                throw new IllegalStateException("No subject specified");
            if (basis == null)
                throw new IllegalStateException("No basis specified");
            return new RealIsGreater(result, subject, basis);
        }

        public IInstructionOutput getResult() {
            return result;
        }

        public Builder setResult(IInstructionOutput result) {
            this.result = result;
            return this;
        }

        public IInstructionInput getSubject() {
            return subject;
        }

        public Builder setSubject(IInstructionInput subject) {
            this.subject = subject;
            return this;
        }

        public IInstructionInput getBasis() {
            return basis;
        }

        public Builder setBasis(IInstructionInput basis) {
            this.basis = basis;
            return this;
        }
    }
}
