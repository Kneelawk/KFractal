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

class InstructionValidationIntTests {
    private static final List<ValueType> twoIntValueTypes = ImmutableList.of(ValueTypes.INT, ValueTypes.INT);

    private static Stream<ValueType> notOneIntValueType() {
        return VariableValueTypesProvider.variableValueTypes()
                .filter(l -> !l.equals(ValueTypes.INT));
    }

    private static Stream<ImmutableList<ValueType>> notTwoIntValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes()
                        .map(b -> ImmutableList.of(a, b)))
                .filter(l -> !l.equals(twoIntValueTypes));
    }

    @ParameterizedTest(name = "testIncompatibleIntAddTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntAddTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntAdd::create);
    }

    @Test
    void testCompatibleIntAddTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntAdd::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntSubtractTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntSubtractTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntSubtract::create);
    }

    @Test
    void testCompatibleIntSubtractTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntSubtract::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntMultiplyTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntMultiplyTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntMultiply::create);
    }

    @Test
    void testCompatibleIntMultiplyTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntMultiply::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntDivideTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntDivideTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntDivide::create);
    }

    @Test
    void testCompatibleIntDivideTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntDivide::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntModuloTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntModuloTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntModulo::create);
    }

    @Test
    void testCompatibleIntModuloTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntModulo::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntPowerTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntPowerTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntPower::create);
    }

    @Test
    void testCompatibleIntPowerTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntPower::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntNotTypes({arguments})")
    @MethodSource("notOneIntValueType")
    void testIncompatibleIntNotTypes(ValueType variableTypes) {
        assertOneIncompatibleValueType(variableTypes, IntNot::create);
    }

    @Test
    void testCompatibleIntNotTypes() {
        assertOneCompatibleValueType(ValueTypes.INT, IntNot::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntAndTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntAndTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntAnd::create);
    }

    @Test
    void testCompatibleIntAndTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntAnd::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntOrTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntOrTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntOr::create);
    }

    @Test
    void testCompatibleIntOrTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntOr::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntXorTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntXorTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntXor::create);
    }

    @Test
    void testCompatibleIntXorTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntXor::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntIsEqualTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntIsEqualTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntIsEqual::create);
    }

    @Test
    void testCompatibleIntIsEqualTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntIsEqual::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntIsGreaterTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntIsGreaterTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntIsGreater::create);
    }

    @Test
    void testCompatibleIntIsGreaterTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntIsGreater::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntIsGreaterOrEqualTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntIsGreaterOrEqualTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntIsGreaterOrEqual::create);
    }

    @Test
    void testCompatibleIntIsGreaterOrEqualTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntIsGreaterOrEqual::create);
    }
}
