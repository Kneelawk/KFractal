package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.phi.Phi;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionScope;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BasicBlock {
    private List<Phi> phis;
    private List<IProceduralValue> body;

    private BasicBlock(List<Phi> phis, List<IProceduralValue> body) {
        this.phis = phis;
        this.body = body;
    }

    public List<Phi> getPhis() {
        return phis;
    }

    public List<IProceduralValue> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("phis", phis)
                .append("body", body)
                .toString();
    }

    public static BasicBlock create(Phi phi,
                                    IProceduralValue... body) {
        return new BasicBlock(ImmutableList.of(phi),
                ImmutableList.copyOf(body));
    }

    public static BasicBlock create(Phi phi0, Phi phi1,
                                    IProceduralValue... body) {
        return new BasicBlock(ImmutableList.of(phi0, phi1),
                ImmutableList.copyOf(body));
    }

    public static BasicBlock create(Phi phi0, Phi phi1, Phi phi2,
                                    IProceduralValue... body) {
        return new BasicBlock(ImmutableList.of(phi0, phi1, phi2),
                ImmutableList.copyOf(body));
    }

    public static BasicBlock create(Iterable<Phi> phis,
                                    Iterable<IProceduralValue> body) {
        return new BasicBlock(ImmutableList.copyOf(phis),
                ImmutableList.copyOf(body));
    }

    public static class Builder {
        private List<Supplier<Phi>> phis = Lists.newArrayList();
        private List<Supplier<IProceduralValue>> body = Lists.newArrayList();
        private int blockIndex = 0;

        public Builder() {
        }

        public Builder(int blockIndex) {
            this.blockIndex = blockIndex;
        }

        public Builder(Collection<Supplier<Phi>> phis, Collection<Supplier<IProceduralValue>> body) {
            this.phis.addAll(phis);
            this.body.addAll(body);
        }

        public Builder(Collection<Supplier<Phi>> phis, Collection<Supplier<IProceduralValue>> body, int blockIndex) {
            this.phis.addAll(phis);
            this.body.addAll(body);
            this.blockIndex = blockIndex;
        }

        public BasicBlock build() {
            return new BasicBlock(phis.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()),
                    body.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()));
        }

        public List<Supplier<Phi>> getPhis() {
            return phis;
        }

        public InstructionReference getNextPhiReference() {
            return InstructionReference.create(blockIndex, InstructionScope.PHI, phis.size());
        }

        public int getNextPhiIndex() {
            return phis.size();
        }

        public Builder setPhiSuppliers(Collection<Supplier<Phi>> phis) {
            this.phis.clear();
            this.phis.addAll(phis);
            return this;
        }

        public Builder setPhis(Collection<Phi> phis) {
            this.phis.clear();
            this.phis.addAll(phis.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public InstructionReference addPhi(Supplier<Phi> phi) {
            phis.add(phi);
            return InstructionReference.create(blockIndex, InstructionScope.PHI, phis.size() - 1);
        }

        public InstructionReference addPhi(Phi phi) {
            phis.add(Suppliers.ofInstance(phi));
            return InstructionReference.create(blockIndex, InstructionScope.PHI, phis.size() - 1);
        }

        @SafeVarargs
        public final Builder addPhis(Supplier<Phi>... phis) {
            this.phis.addAll(Arrays.asList(phis));
            return this;
        }

        public Builder addPhis(Phi... phis) {
            this.phis.addAll(Arrays.stream(phis).map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public Builder addPhiSuppliers(Collection<Supplier<Phi>> phis) {
            this.phis.addAll(phis);
            return this;
        }

        public Builder addPhis(Collection<Phi> phis) {
            this.phis.addAll(phis.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public List<Supplier<IProceduralValue>> getBody() {
            return body;
        }

        public InstructionReference getNextValueReference() {
            return InstructionReference.create(blockIndex, InstructionScope.BODY, body.size());
        }

        public int getNextValueIndex() {
            return body.size();
        }

        public Builder setBodySupplier(Collection<Supplier<IProceduralValue>> body) {
            this.body.clear();
            this.body.addAll(body);
            return this;
        }

        public Builder setBody(Collection<IProceduralValue> body) {
            this.body.clear();
            this.body.addAll(body.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public InstructionReference addValue(Supplier<IProceduralValue> value) {
            this.body.add(value);
            return InstructionReference.create(blockIndex, InstructionScope.BODY, body.size() - 1);
        }

        public InstructionReference addValue(IProceduralValue value) {
            this.body.add(Suppliers.ofInstance(value));
            return InstructionReference.create(blockIndex, InstructionScope.BODY, body.size() - 1);
        }

        @SafeVarargs
        public final Builder addValues(Supplier<IProceduralValue>... values) {
            this.body.addAll(Arrays.asList(values));
            return this;
        }

        public Builder addValues(IProceduralValue... values) {
            this.body.addAll(Arrays.stream(values).map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public Builder addValueSuppliers(Collection<Supplier<IProceduralValue>> values) {
            this.body.addAll(values);
            return this;
        }

        public Builder addValues(Collection<IProceduralValue> values) {
            this.body.addAll(values.stream().map(Suppliers::ofInstance).collect(Collectors.toList()));
            return this;
        }

        public int getBlockIndex() {
            return blockIndex;
        }

        public void setBlockIndex(int blockIndex) {
            this.blockIndex = blockIndex;
        }
    }
}
