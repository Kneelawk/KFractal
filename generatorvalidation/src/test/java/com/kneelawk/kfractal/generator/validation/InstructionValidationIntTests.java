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

public class InstructionValidationIntTests {
    private static final List<ValueType> twoIntValueTypes = ImmutableList.of(ValueTypes.INT, ValueTypes.INT);
    private static final List<ValueType> oneBoolAndTwoIntValueTypes =
            ImmutableList.of(ValueTypes.BOOL, ValueTypes.INT, ValueTypes.INT);
    private static final List<ValueType> threeIntValueTypes =
            ImmutableList.of(ValueTypes.INT, ValueTypes.INT, ValueTypes.INT);

    private static Stream<ImmutableList<ValueType>> notTwoIntValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes()
                        .map(b -> ImmutableList.of(a, b)))
                .filter(l -> !l.equals(twoIntValueTypes));
    }

    private static Stream<ImmutableList<ValueType>> notOneBoolAndTwoIntValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes()
                        .flatMap(b -> VariableValueTypesProvider.variableValueTypes()
                                .map(c -> ImmutableList.of(a, b, c))))
                .filter(l -> !l.equals(oneBoolAndTwoIntValueTypes));
    }

    private static Stream<ImmutableList<ValueType>> notThreeIntValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes()
                        .flatMap(b -> VariableValueTypesProvider.variableValueTypes()
                                .map(c -> ImmutableList.of(a, b, c))))
                .filter(l -> !l.equals(threeIntValueTypes));
    }

    @ParameterizedTest(name = "testIncompatibleIntAddTypes({arguments})")
    @MethodSource("notThreeIntValueTypes")
    void testIncompatibleIntAddTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntAdd::create);
    }

    @Test
    void testCompatibleIntAddTypes() {
        assertThreeCompatibleValueTypes(threeIntValueTypes, IntAdd::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntSubtractTypes({arguments})")
    @MethodSource("notThreeIntValueTypes")
    void testIncompatibleIntSubtractTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntSubtract::create);
    }

    @Test
    void testCompatibleIntSubtractTypes() {
        assertThreeCompatibleValueTypes(threeIntValueTypes, IntSubtract::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntMultiplyTypes({arguments})")
    @MethodSource("notThreeIntValueTypes")
    void testIncompatibleIntMultiplyTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntMultiply::create);
    }

    @Test
    void testCompatibleIntMultiplyTypes() {
        assertThreeCompatibleValueTypes(threeIntValueTypes, IntMultiply::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntDivideTypes({arguments})")
    @MethodSource("notThreeIntValueTypes")
    void testIncompatibleIntDivideTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntDivide::create);
    }

    @Test
    void testCompatibleIntDivideTypes() {
        assertThreeCompatibleValueTypes(threeIntValueTypes, IntDivide::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntModuloTypes({arguments})")
    @MethodSource("notThreeIntValueTypes")
    void testIncompatibleIntModuloTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntModulo::create);
    }

    @Test
    void testCompatibleIntModuloTypes() {
        assertThreeCompatibleValueTypes(threeIntValueTypes, IntModulo::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntPowerTypes({arguments})")
    @MethodSource("notThreeIntValueTypes")
    void testIncompatibleIntPowerTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntPower::create);
    }

    @Test
    void testCompatibleIntPowerTypes() {
        assertThreeCompatibleValueTypes(threeIntValueTypes, IntPower::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntNotTypes({arguments})")
    @MethodSource("notTwoIntValueTypes")
    void testIncompatibleIntNotTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, IntNot::create);
    }

    @Test
    void testCompatibleIntNotTypes() {
        assertTwoCompatibleValueTypes(twoIntValueTypes, IntNot::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntAndTypes({arguments})")
    @MethodSource("notThreeIntValueTypes")
    void testIncompatibleIntAndTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntAnd::create);
    }

    @Test
    void testCompatibleIntAndTypes() {
        assertThreeCompatibleValueTypes(threeIntValueTypes, IntAnd::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntOrTypes({arguments})")
    @MethodSource("notThreeIntValueTypes")
    void testIncompatibleIntOrTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntOr::create);
    }

    @Test
    void testCompatibleIntOrTypes() {
        assertThreeCompatibleValueTypes(threeIntValueTypes, IntOr::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntXorTypes({arguments})")
    @MethodSource("notThreeIntValueTypes")
    void testIncompatibleIntXorTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntXor::create);
    }

    @Test
    void testCompatibleIntXorTypes() {
        assertThreeCompatibleValueTypes(threeIntValueTypes, IntXor::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntIsEqualTypes({arguments})")
    @MethodSource("notOneBoolAndTwoIntValueTypes")
    void testIncompatibleIntIsEqualTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntIsEqual::create);
    }

    @Test
    void testCompatibleIntIsEqualTypes() {
        assertThreeCompatibleValueTypes(oneBoolAndTwoIntValueTypes, IntIsEqual::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntIsNotEqualTypes({arguments})")
    @MethodSource("notOneBoolAndTwoIntValueTypes")
    void testIncompatibleIntIsNotEqualTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntIsNotEqual::create);
    }

    @Test
    void testCompatibleIntIsNotEqualTypes() {
        assertThreeCompatibleValueTypes(oneBoolAndTwoIntValueTypes, IntIsNotEqual::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntIsGreaterTypes({arguments})")
    @MethodSource("notOneBoolAndTwoIntValueTypes")
    void testIncompatibleIntIsGreaterTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntIsGreater::create);
    }

    @Test
    void testCompatibleIntIsGreaterTypes() {
        assertThreeCompatibleValueTypes(oneBoolAndTwoIntValueTypes, IntIsGreater::create);
    }

    @ParameterizedTest(name = "testIncompatibleIntIsGreaterOrEqualTypes({arguments})")
    @MethodSource("notOneBoolAndTwoIntValueTypes")
    void testIncompatibleIntIsGreaterOrEqualTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, IntIsGreaterOrEqual::create);
    }

    @Test
    void testCompatibleIntIsGreaterOrEqualTypes() {
        assertThreeCompatibleValueTypes(oneBoolAndTwoIntValueTypes, IntIsGreaterOrEqual::create);
    }
}
