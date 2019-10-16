package com.kneelawk.kfractal.generator.api.ir.phi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Phi implements IValue {
    private List<PhiBranch> branches;

    private Phi(List<PhiBranch> branches) {
        this.branches = branches;
    }

    public List<PhiBranch> getBranches() {
        return branches;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitPhi(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("branches", branches)
                .toString();
    }

    public static Phi create(PhiBranch... branches) {
        return new Phi(ImmutableList.copyOf(branches));
    }

    public static Phi create(Iterable<PhiBranch> branches) {
        return new Phi(ImmutableList.copyOf(branches));
    }

    public static class Builder {
        private List<PhiBranch> branches =
                Lists.newArrayList();

        public Builder() {
        }

        public Builder(Collection<PhiBranch> branches) {
            this.branches.addAll(branches);
        }

        public Phi build() {
            return new Phi(ImmutableList.copyOf(branches));
        }

        public List<PhiBranch> getBranches() {
            return branches;
        }

        public Builder setBranches(
                Collection<PhiBranch> branches) {
            this.branches.clear();
            this.branches.addAll(branches);
            return this;
        }

        public Builder addBranch(PhiBranch branch) {
            branches.add(branch);
            return this;
        }

        public Builder addBranches(PhiBranch... branches) {
            this.branches.addAll(Arrays.asList(branches));
            return this;
        }

        public Builder addBranches(
                Collection<PhiBranch> branches) {
            this.branches.addAll(branches);
            return this;
        }
    }
}
