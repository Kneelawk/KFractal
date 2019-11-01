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

import static com.kneelawk.kfractal.generator.validation.ValueTypeAsserts.*;

class InstructionValidationRealTests {
    private static final List<ValueType> twoRealValueTypes =
            ImmutableList.of(ValueTypes.REAL, ValueTypes.REAL);

    private static Stream<ImmutableList<ValueType>> notTwoRealValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes().map(b -> ImmutableList.of(a, b)))
                .filter(l -> !l.equals(twoRealValueTypes));
    }

    @ParameterizedTest(name = "testIncompatibleRealAddTypes({arguments})")
    @MethodSource("notTwoRealValueTypes")
    void testIncompatibleRealAddTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, RealAdd::create);
    }

    @Test
    void testCompatibleRealAddTypes() {
        assertTwoCompatibleValueTypes(twoRealValueTypes, RealAdd::create);
    }

    @ParameterizedTest(name = "testIncompatibleRealSubtractTypes({arguments})")
    @MethodSource("notTwoRealValueTypes")
    void testIncompatibleRealSubtractTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, RealSubtract::create);
    }

    @Test
    void testCompatibleRealSubtractTypes() {
        assertTwoCompatibleValueTypes(twoRealValueTypes, RealSubtract::create);
    }

    @ParameterizedTest(name = "testIncompatibleRealMultiplyTypes({arguments})")
    @MethodSource("notTwoRealValueTypes")
    void testIncompatibleRealMultiplyType(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, RealMultiply::create);
    }

    @Test
    void testCompatibleRealMultiplyTypes() {
        assertTwoCompatibleValueTypes(twoRealValueTypes, RealMultiply::create);
    }

    @ParameterizedTest(name = "testIncompatibleRealDivideTypes({arguments})")
    @MethodSource("notTwoRealValueTypes")
    void testIncompatibleRealDivideTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, RealDivide::create);
    }

    @Test
    void testCompatibleRealDivideTypes() {
        assertTwoCompatibleValueTypes(twoRealValueTypes, RealDivide::create);
    }

    @ParameterizedTest(name = "testIncompatibleRealPowerTypes({arguments})")
    @MethodSource("notTwoRealValueTypes")
    void testIncompatibleRealPowerTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, RealPower::create);
    }

    @Test
    void testCompatibleRealPowerTypes() {
        assertTwoCompatibleValueTypes(twoRealValueTypes, RealPower::create);
    }

    @ParameterizedTest(name = "testIncompatibleRealIsEqualTypes({arguments})")
    @MethodSource("notTwoRealValueTypes")
    void testIncompatibleRealIsEqualTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, RealIsEqual::create);
    }

    @Test
    void testCompatibleRealIsEqualTypes() {
        assertTwoCompatibleValueTypes(twoRealValueTypes, RealIsEqual::create);
    }

    @ParameterizedTest(name = "testIncompatibleRealIsGreaterTypes({arguments})")
    @MethodSource("notTwoRealValueTypes")
    void testIncompatibleRealIsGreaterTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, RealIsGreater::create);
    }

    @Test
    void testCompatibleRealIsGreaterTypes() {
        assertTwoCompatibleValueTypes(twoRealValueTypes, RealIsGreater::create);
    }

    @ParameterizedTest(name = "testIncompatibleRealIsGreaterOrEqualTypes({arguments})")
    @MethodSource("notTwoRealValueTypes")
    void testIncompatibleRealIsGreaterOrEqualTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, RealIsGreaterOrEqual::create);
    }

    @Test
    void testCompatibleRealIsGreaterOrEqualTypes() {
        assertTwoCompatibleValueTypes(twoRealValueTypes, RealIsGreaterOrEqual::create);
    }

    @ParameterizedTest(name = "testIncompatibleRealComposeComplexTypes({arguments})")
    @MethodSource("notTwoRealValueTypes")
    void testIncompatibleRealComposeComplexTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, RealComposeComplex::create);
    }

    @Test
    void testCompatibleRealComposeComplexTypes() {
        assertTwoCompatibleValueTypes(twoRealValueTypes, RealComposeComplex::create);
    }
}
