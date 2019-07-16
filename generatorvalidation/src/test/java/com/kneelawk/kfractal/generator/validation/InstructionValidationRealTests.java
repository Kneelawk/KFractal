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

import static com.kneelawk.kfractal.generator.validation.ValueTypeAsserts.assertThreeCompatibleValueTypes;
import static com.kneelawk.kfractal.generator.validation.ValueTypeAsserts.assertThreeIncompatibleValueTypes;

public class InstructionValidationRealTests {
	private static final List<ValueType> oneBoolAndTwoRealValueTypes =
			ImmutableList.of(ValueTypes.BOOL, ValueTypes.REAL, ValueTypes.REAL);
	private static final List<ValueType> threeRealValueTypes =
			ImmutableList.of(ValueTypes.REAL, ValueTypes.REAL, ValueTypes.REAL);
	private static final List<ValueType> oneComplexAndTwoRealValueTypes =
			ImmutableList.of(ValueTypes.COMPLEX, ValueTypes.REAL, ValueTypes.REAL);

	private static Stream<ImmutableList<ValueType>> notOneBoolAndTwoRealValueTypes() {
		return VariableValueTypesProvider.variableValueTypes().flatMap(
				a -> VariableValueTypesProvider.variableValueTypes().flatMap(
						b -> VariableValueTypesProvider.variableValueTypes().map(c -> ImmutableList.of(a, b, c))))
				.filter(l -> !l.equals(oneBoolAndTwoRealValueTypes));
	}

	private static Stream<ImmutableList<ValueType>> notThreeRealValueTypes() {
		return VariableValueTypesProvider.variableValueTypes().flatMap(
				a -> VariableValueTypesProvider.variableValueTypes().flatMap(
						b -> VariableValueTypesProvider.variableValueTypes().map(c -> ImmutableList.of(a, b, c))))
				.filter(l -> !l.equals(threeRealValueTypes));
	}

	private static Stream<ImmutableList<ValueType>> notOneComplexAndTwoRealValueTypes() {
		return VariableValueTypesProvider.variableValueTypes().flatMap(
				a -> VariableValueTypesProvider.variableValueTypes().flatMap(
						b -> VariableValueTypesProvider.variableValueTypes().map(c -> ImmutableList.of(a, b, c))))
				.filter(l -> !l.equals(oneComplexAndTwoRealValueTypes));
	}

	@ParameterizedTest(name = "testIncompatibleRealAddTypes({arguments})")
	@MethodSource("notThreeRealValueTypes")
	void testIncompatibleRealAddTypes(List<ValueType> variableTypes) {
		assertThreeIncompatibleValueTypes(variableTypes, RealAdd::create);
	}

	@Test
	void testCompatibleRealAddTypes() {
		assertThreeCompatibleValueTypes(threeRealValueTypes, RealAdd::create);
	}

	@ParameterizedTest(name = "testIncompatibleRealSubtractTypes({arguments})")
	@MethodSource("notThreeRealValueTypes")
	void testIncompatibleRealSubtractTypes(List<ValueType> variableTypes) {
		assertThreeIncompatibleValueTypes(variableTypes, RealSubtract::create);
	}

	@Test
	void testCompatibleRealSubtractTypes() {
		assertThreeCompatibleValueTypes(threeRealValueTypes, RealSubtract::create);
	}

	@ParameterizedTest(name = "testIncompatibleRealMultiplyTypes({arguments})")
	@MethodSource("notThreeRealValueTypes")
	void testIncompatibleRealMultiplyType(List<ValueType> variableTypes) {
		assertThreeIncompatibleValueTypes(variableTypes, RealMultiply::create);
	}

	@Test
	void testCompatibleRealMultiplyTypes() {
		assertThreeCompatibleValueTypes(threeRealValueTypes, RealMultiply::create);
	}

	@ParameterizedTest(name = "testIncompatibleRealDivideTypes({arguments})")
	@MethodSource("notThreeRealValueTypes")
	void testIncompatibleRealDivideTypes(List<ValueType> variableTypes) {
		assertThreeIncompatibleValueTypes(variableTypes, RealDivide::create);
	}

	@Test
	void testCompatibleRealDivideTypes() {
		assertThreeCompatibleValueTypes(threeRealValueTypes, RealDivide::create);
	}

	@ParameterizedTest(name = "testIncompatibleRealPowerTypes({arguments})")
	@MethodSource("notThreeRealValueTypes")
	void testIncompatibleRealPowerTypes(List<ValueType> variableTypes) {
		assertThreeIncompatibleValueTypes(variableTypes, RealPower::create);
	}

	@Test
	void testCompatibleRealPowerTypes() {
		assertThreeCompatibleValueTypes(threeRealValueTypes, RealPower::create);
	}

	@ParameterizedTest(name = "testIncompatibleRealIsEqualTypes({arguments})")
	@MethodSource("notOneBoolAndTwoRealValueTypes")
	void testIncompatibleRealIsEqualTypes(List<ValueType> variableTypes) {
		assertThreeIncompatibleValueTypes(variableTypes, RealIsEqual::create);
	}

	@Test
	void testCompatibleRealIsEqualTypes() {
		assertThreeCompatibleValueTypes(oneBoolAndTwoRealValueTypes, RealIsEqual::create);
	}

	@ParameterizedTest(name = "testIncompatibleRealIsNotEqualTypes({arguments})")
	@MethodSource("notOneBoolAndTwoRealValueTypes")
	void testIncompatibleRealIsNotEqualTypes(List<ValueType> variableTypes) {
		assertThreeIncompatibleValueTypes(variableTypes, RealIsNotEqual::create);
	}

	@Test
	void testCompatibleRealIsNotEqualTypes() {
		assertThreeCompatibleValueTypes(oneBoolAndTwoRealValueTypes, RealIsNotEqual::create);
	}

	@ParameterizedTest(name = "testIncompatibleRealIsGreaterTypes({arguments})")
	@MethodSource("notOneBoolAndTwoRealValueTypes")
	void testIncompatibleRealIsGreaterTypes(List<ValueType> variableTypes) {
		assertThreeIncompatibleValueTypes(variableTypes, RealIsGreater::create);
	}

	@Test
	void testCompatibleRealIsGreaterTypes() {
		assertThreeCompatibleValueTypes(oneBoolAndTwoRealValueTypes, RealIsGreater::create);
	}

	@ParameterizedTest(name = "testIncompatibleRealIsGreaterOrEqualTypes({arguments})")
	@MethodSource("notOneBoolAndTwoRealValueTypes")
	void testIncompatibleRealIsGreaterOrEqualTypes(List<ValueType> variableTypes) {
		assertThreeIncompatibleValueTypes(variableTypes, RealIsGreaterOrEqual::create);
	}

	@Test
	void testCompatibleRealIsGreaterOrEqualTypes() {
		assertThreeCompatibleValueTypes(oneBoolAndTwoRealValueTypes, RealIsGreaterOrEqual::create);
	}

	@ParameterizedTest(name = "testIncompatibleRealComposeComplexTypes({arguments})")
	@MethodSource("notOneComplexAndTwoRealValueTypes")
	void testIncompatibleRealComposeComplexTypes(List<ValueType> variableTypes) {
		assertThreeIncompatibleValueTypes(variableTypes, RealComposeComplex::create);
	}

	@Test
	void testCompatibleRealComposeComplexTypes() {
		assertThreeCompatibleValueTypes(oneComplexAndTwoRealValueTypes, RealComposeComplex::create);
	}
}
