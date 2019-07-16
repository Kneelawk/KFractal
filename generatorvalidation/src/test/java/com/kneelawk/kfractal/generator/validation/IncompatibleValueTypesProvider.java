package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.ValueType;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class IncompatibleValueTypesProvider implements ArgumentsProvider {
    static Stream<Pair<ValueType, ValueType>> incompatibleValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes().map(b -> Pair.of(a, b)))
                .filter(p -> !p.getLeft().isAssignableFrom(p.getRight()));
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return incompatibleValueTypes().map(Arguments::of);
    }
}
