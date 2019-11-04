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

class InstructionValidationComplexTests {
    private static final List<ValueType> twoComplexValueTypes =
            ImmutableList.of(ValueTypes.COMPLEX, ValueTypes.COMPLEX);

    private static Stream<ValueType> notOneComplexValueType() {
        return VariableValueTypesProvider.variableValueTypes()
                .filter(l -> !l.equals(ValueTypes.COMPLEX));
    }

    private static Stream<ImmutableList<ValueType>> notTwoComplexValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().flatMap(
                        a -> VariableValueTypesProvider.variableValueTypes().map(b -> ImmutableList.of(a, b)))
                .filter(l -> !l.equals(twoComplexValueTypes));
    }

    @ParameterizedTest(name = "testIncompatibleComplexAddTypes({arguments})")
    @MethodSource("notTwoComplexValueTypes")
    void testIncompatibleComplexAddTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, ComplexAdd::create);
    }

    @Test
    void testCompatibleComplexAddTypes() {
        assertTwoCompatibleValueTypes(twoComplexValueTypes, ComplexAdd::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexSubtractTypes({arguments})")
    @MethodSource("notTwoComplexValueTypes")
    void testIncompatibleComplexSubtractTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, ComplexSubtract::create);
    }

    @Test
    void testCompatibleComplexSubtractTypes() {
        assertTwoCompatibleValueTypes(twoComplexValueTypes, ComplexSubtract::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexMultiplyTypes({arguments})")
    @MethodSource("notTwoComplexValueTypes")
    void testIncompatibleComplexMultiplyTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, ComplexMultiply::create);
    }

    @Test
    void testCompatibleComplexMultiplyTypes() {
        assertTwoCompatibleValueTypes(twoComplexValueTypes, ComplexMultiply::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexDivideTypes({arguments})")
    @MethodSource("notTwoComplexValueTypes")
    void testIncompatibleComplexDivideTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, ComplexDivide::create);
    }

    @Test
    void testCompatibleComplexDivideTypes() {
        assertTwoCompatibleValueTypes(twoComplexValueTypes, ComplexDivide::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexPowerTypes({arguments})")
    @MethodSource("notTwoComplexValueTypes")
    void testIncompatibleComplexPowerTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, ComplexPower::create);
    }

    @Test
    void testCompatibleComplexPowerTypes() {
        assertTwoCompatibleValueTypes(twoComplexValueTypes, ComplexPower::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexGetRealTypes({arguments})")
    @MethodSource("notOneComplexValueType")
    void testIncompatibleComplexGetRealTypes(ValueType variableTypes) {
        assertOneIncompatibleValueType(variableTypes, ComplexGetReal::create);
    }

    @Test
    void testCompatibleComplexGetRealTypes() {
        assertOneCompatibleValueType(ValueTypes.COMPLEX, ComplexGetReal::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexGetImaginaryTypes({arguments})")
    @MethodSource("notOneComplexValueType")
    void testIncompatibleComplexGetImaginaryTypes(ValueType variableTypes) {
        assertOneIncompatibleValueType(variableTypes, ComplexGetImaginary::create);
    }

    @Test
    void testCompatibleComplexGetImaginaryTypes() {
        assertOneCompatibleValueType(ValueTypes.COMPLEX, ComplexGetImaginary::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexModuloTypes({arguments})")
    @MethodSource("notOneComplexValueType")
    void testIncompatibleComplexModuloTypes(ValueType variableTypes) {
        assertOneIncompatibleValueType(variableTypes, ComplexModulo::create);
    }

    @Test
    void testCompatibleComplexModuloTypes() {
        assertOneCompatibleValueType(ValueTypes.COMPLEX, ComplexModulo::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexIsEqualTypes({arguments})")
    @MethodSource("notTwoComplexValueTypes")
    void testIncompatibleComplexIsEqualTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, ComplexIsEqual::create);
    }

    @Test
    void testCompatibleComplexIsEqualTypes() {
        assertTwoCompatibleValueTypes(twoComplexValueTypes, ComplexIsEqual::create);
    }
}
