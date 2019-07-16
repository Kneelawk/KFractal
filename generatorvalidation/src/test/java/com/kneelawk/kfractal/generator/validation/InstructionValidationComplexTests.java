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

public class InstructionValidationComplexTests {
    private static final List<ValueType> oneRealAndOneComplexValueTypes =
            ImmutableList.of(ValueTypes.REAL, ValueTypes.COMPLEX);
    private static final List<ValueType> threeComplexValueTypes =
            ImmutableList.of(ValueTypes.COMPLEX, ValueTypes.COMPLEX, ValueTypes.COMPLEX);

    private static Stream<ImmutableList<ValueType>> notOneRealAndOneComplexValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes().map(b -> ImmutableList.of(a, b)))
                .filter(l -> !l.equals(oneRealAndOneComplexValueTypes));
    }

    private static Stream<ImmutableList<ValueType>> notThreeComplexValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().flatMap(
                a -> VariableValueTypesProvider.variableValueTypes().flatMap(
                        b -> VariableValueTypesProvider.variableValueTypes().map(c -> ImmutableList.of(a, b, c))))
                .filter(l -> !l.equals(threeComplexValueTypes));
    }

    @ParameterizedTest(name = "testIncompatibleComplexAddTypes({arguments})")
    @MethodSource("notThreeComplexValueTypes")
    void testIncompatibleComplexAddTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, ComplexAdd::create);
    }

    @Test
    void testCompatibleComplexAddTypes() {
        assertThreeCompatibleValueTypes(threeComplexValueTypes, ComplexAdd::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexSubtractTypes({arguments})")
    @MethodSource("notThreeComplexValueTypes")
    void testIncompatibleComplexSubtractTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, ComplexSubtract::create);
    }

    @Test
    void testCompatibleComplexSubtractTypes() {
        assertThreeCompatibleValueTypes(threeComplexValueTypes, ComplexSubtract::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexMultiplyTypes({arguments})")
    @MethodSource("notThreeComplexValueTypes")
    void testIncompatibleComplexMultiplyTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, ComplexMultiply::create);
    }

    @Test
    void testCompatibleComplexMultiplyTypes() {
        assertThreeCompatibleValueTypes(threeComplexValueTypes, ComplexMultiply::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexDivideTypes({arguments})")
    @MethodSource("notThreeComplexValueTypes")
    void testIncompatibleComplexDivideTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, ComplexDivide::create);
    }

    @Test
    void testCompatibleComplexDivideTypes() {
        assertThreeCompatibleValueTypes(threeComplexValueTypes, ComplexDivide::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexPowerTypes({arguments})")
    @MethodSource("notThreeComplexValueTypes")
    void testIncompatibleComplexPowerTypes(List<ValueType> variableTypes) {
        assertThreeIncompatibleValueTypes(variableTypes, ComplexPower::create);
    }

    @Test
    void testCompatibleComplexPowerTypes() {
        assertThreeCompatibleValueTypes(threeComplexValueTypes, ComplexPower::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexGetRealTypes({arguments})")
    @MethodSource("notOneRealAndOneComplexValueTypes")
    void testIncompatibleComplexGetRealTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, ComplexGetReal::create);
    }

    @Test
    void testCompatibleComplexGetRealTypes() {
        assertTwoCompatibleValueTypes(oneRealAndOneComplexValueTypes, ComplexGetReal::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexGetImaginaryTypes({arguments})")
    @MethodSource("notOneRealAndOneComplexValueTypes")
    void testIncompatibleComplexGetImaginaryTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, ComplexGetImaginary::create);
    }

    @Test
    void testCompatibleComplexGetImaginaryTypes() {
        assertTwoCompatibleValueTypes(oneRealAndOneComplexValueTypes, ComplexGetImaginary::create);
    }

    @ParameterizedTest(name = "testIncompatibleComplexModuloTypes({arguments})")
    @MethodSource("notOneRealAndOneComplexValueTypes")
    void testIncompatibleComplexModuloTypes(List<ValueType> variableTypes) {
        assertTwoIncompatibleValueTypes(variableTypes, ComplexModulo::create);
    }

    @Test
    void testCompatibleComplexModuloTypes() {
        assertTwoCompatibleValueTypes(oneRealAndOneComplexValueTypes, ComplexModulo::create);
    }
}
