package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class InstructionValidationBoolTests {
	private static final List<ValueType> twoBoolValueTypes = ImmutableList.of(ValueTypes.BOOL, ValueTypes.BOOL);
	private static final List<ValueType> threeBoolValueTypes =
			ImmutableList.of(ValueTypes.BOOL, ValueTypes.BOOL, ValueTypes.BOOL);

	private static Stream<ImmutableList<ValueType>> notTwoBoolValueTypes() {
		return VariableValueTypesProvider.variableValueTypes()
				.flatMap(a -> VariableValueTypesProvider.variableValueTypes()
						.map(b -> ImmutableList.of(a, b)))
				.filter(l -> !l.equals(twoBoolValueTypes));
	}

	private static Stream<ImmutableList<ValueType>> notThreeBoolValueTypes() {
		return VariableValueTypesProvider.variableValueTypes()
				.flatMap(a -> VariableValueTypesProvider.variableValueTypes()
						.flatMap(b -> VariableValueTypesProvider.variableValueTypes()
								.map(c -> ImmutableList.of(a, b, c))))
				.filter(l -> !l.equals(threeBoolValueTypes));
	}

	@ParameterizedTest(name = "testIncompatibleBoolNotTypes({arguments})")
	@MethodSource("notTwoBoolValueTypes")
	void testIncompatibleBoolNotTypes(List<ValueType> argumentTypes) {
		ValueTypeAsserts.assertTwoIncompatibleValueTypes(argumentTypes, BoolNot::create);
	}

	@Test
	void testCompatibleBoolNotTypes() {
		ValueTypeAsserts.assertTwoCompatibleValueTypes(twoBoolValueTypes, BoolNot::create);
	}

	@ParameterizedTest(name = "testIncompatibleBoolAndTypes({arguments})")
	@MethodSource("notThreeBoolValueTypes")
	void testIncompatibleBoolAndTypes(List<ValueType> argumentTypes) {
		ValueTypeAsserts.assertThreeIncompatibleValueTypes(argumentTypes, BoolAnd::create);
	}

	@Test
	void testCompatibleBoolAndTypes() {
		ValueTypeAsserts.assertThreeCompatibleValueTypes(threeBoolValueTypes, BoolAnd::create);
	}

	@ParameterizedTest(name = "testIncompatibleBoolOrTypes({arguments})")
	@MethodSource("notThreeBoolValueTypes")
	void testIncompatibleBoolOrTypes(List<ValueType> argumentTypes) {
		ValueTypeAsserts.assertThreeIncompatibleValueTypes(argumentTypes, BoolOr::create);
	}

	@Test
	void testCompatibleBoolOrTypes() {
		ValueTypeAsserts.assertThreeCompatibleValueTypes(threeBoolValueTypes, BoolOr::create);
	}

	@ParameterizedTest(name = "testIncompatibleBoolIsEqualTypes({arguments})")
	@MethodSource("notThreeBoolValueTypes")
	void testIncompatibleBoolIsEqualTypes(List<ValueType> argumentTypes) {
		ValueTypeAsserts.assertThreeIncompatibleValueTypes(argumentTypes, BoolIsEqual::create);
	}

	@Test
	void testCompatibleBoolIsEqualTypes() {
		ValueTypeAsserts.assertThreeCompatibleValueTypes(threeBoolValueTypes, BoolIsEqual::create);
	}

	@ParameterizedTest(name = "testIncompatibleBoolIsNotEqualTypes({arguments})")
	@MethodSource("notThreeBoolValueTypes")
	void testIncompatibleBoolIsNotEqualTypes(List<ValueType> argumentTypes) {
		ValueTypeAsserts.assertThreeIncompatibleValueTypes(argumentTypes, BoolIsNotEqual::create);
	}

	@Test
	void testCompatibleBoolIsNotEqualTypes() {
		ValueTypeAsserts.assertThreeCompatibleValueTypes(threeBoolValueTypes, BoolIsNotEqual::create);
	}
}
