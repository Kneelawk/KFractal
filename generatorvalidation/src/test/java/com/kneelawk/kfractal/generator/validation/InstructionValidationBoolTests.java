package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.instruction.BoolAnd;
import com.kneelawk.kfractal.generator.api.ir.instruction.BoolIsEqual;
import com.kneelawk.kfractal.generator.api.ir.instruction.BoolNot;
import com.kneelawk.kfractal.generator.api.ir.instruction.BoolOr;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class InstructionValidationBoolTests {
    private static final List<ValueType> twoBoolValueTypes = ImmutableList.of(ValueTypes.BOOL, ValueTypes.BOOL);

    private static Stream<ValueType> notOneBoolValueType() {
        return VariableValueTypesProvider.variableValueTypes()
                .filter(l -> !l.equals(ValueTypes.BOOL));
    }

    private static Stream<ImmutableList<ValueType>> notTwoBoolValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes()
                        .map(b -> ImmutableList.of(a, b)))
                .filter(l -> !l.equals(twoBoolValueTypes));
    }

    @ParameterizedTest(name = "testIncompatibleBoolNotTypes({arguments})")
    @MethodSource("notOneBoolValueType")
    void testIncompatibleBoolNotTypes(ValueType argumentTypes) {
        ValueTypeAsserts.assertOneIncompatibleValueType(argumentTypes, BoolNot::create);
    }

    @Test
    void testCompatibleBoolNotTypes() {
        ValueTypeAsserts.assertOneCompatibleValueType(ValueTypes.BOOL, BoolNot::create);
    }

    @ParameterizedTest(name = "testIncompatibleBoolAndTypes({arguments})")
    @MethodSource("notTwoBoolValueTypes")
    void testIncompatibleBoolAndTypes(List<ValueType> argumentTypes) {
        ValueTypeAsserts.assertTwoIncompatibleValueTypes(argumentTypes, BoolAnd::create);
    }

    @Test
    void testCompatibleBoolAndTypes() {
        ValueTypeAsserts.assertTwoCompatibleValueTypes(twoBoolValueTypes, BoolAnd::create);
    }

    @ParameterizedTest(name = "testIncompatibleBoolOrTypes({arguments})")
    @MethodSource("notTwoBoolValueTypes")
    void testIncompatibleBoolOrTypes(List<ValueType> argumentTypes) {
        ValueTypeAsserts.assertTwoIncompatibleValueTypes(argumentTypes, BoolOr::create);
    }

    @Test
    void testCompatibleBoolOrTypes() {
        ValueTypeAsserts.assertTwoCompatibleValueTypes(twoBoolValueTypes, BoolOr::create);
    }

    @ParameterizedTest(name = "testIncompatibleBoolIsEqualTypes({arguments})")
    @MethodSource("notTwoBoolValueTypes")
    void testIncompatibleBoolIsEqualTypes(List<ValueType> argumentTypes) {
        ValueTypeAsserts.assertTwoIncompatibleValueTypes(argumentTypes, BoolIsEqual::create);
    }

    @Test
    void testCompatibleBoolIsEqualTypes() {
        ValueTypeAsserts.assertTwoCompatibleValueTypes(twoBoolValueTypes, BoolIsEqual::create);
    }
}
