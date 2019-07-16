package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class VariableValueTypesProvider implements ArgumentsProvider {
	static Stream<ValueType> variableValueTypes() {
		return Stream.of(ValueTypes.BOOL, ValueTypes.INT, ValueTypes.REAL, ValueTypes.COMPLEX,
				ValueTypes.FUNCTION(ValueTypes.VOID),
				ValueTypes.FUNCTION(ValueTypes.INT),
				ValueTypes.FUNCTION(ValueTypes.INT, ValueTypes.INT),
				ValueTypes.FUNCTION(ValueTypes.INT, ValueTypes.INT, ValueTypes.INT),
				ValueTypes.FUNCTION(ValueTypes.FUNCTION(ValueTypes.INT, ValueTypes.INT),
						ValueTypes.FUNCTION(ValueTypes.INT, ValueTypes.INT),
						ValueTypes.FUNCTION(ValueTypes.INT, ValueTypes.INT)),
				ValueTypes.POINTER(ValueTypes.BOOL),
				ValueTypes.POINTER(ValueTypes.INT),
				ValueTypes.POINTER(ValueTypes.FUNCTION(ValueTypes.INT, ValueTypes.INT)),
				ValueTypes.POINTER(ValueTypes.POINTER(ValueTypes.COMPLEX)));
	}

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		return variableValueTypes().map(Arguments::of);
	}
}
