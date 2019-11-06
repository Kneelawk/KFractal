package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableSet;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.constant.VoidConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VariableValidationTests {
    // TODO: Investigate local "variable" usages

    @Test
    void testIllegalVoidVariableType() {
        Program.Builder program = new Program.Builder();
        program.addGlobalVariable("v", GlobalDeclaration.create(ValueTypes.VOID));

        assertThrows(IllegalVariableTypeException.class, () -> ProgramValidator.checkValidity(program.build()));
    }

    @Test
    void testIllegalNullFunctionVariableType() {
        Program.Builder program = new Program.Builder();
        program.addGlobalVariable("v", GlobalDeclaration.create(ValueTypes.NULL_FUNCTION));

        assertThrows(IllegalVariableTypeException.class, () -> ProgramValidator.checkValidity(program.build()));
    }

    @Test
    void testIllegalNullPointerVariableType() {
        Program.Builder program = new Program.Builder();
        program.addGlobalVariable("v", GlobalDeclaration.create(ValueTypes.NULL_POINTER));

        assertThrows(IllegalVariableTypeException.class, () -> ProgramValidator.checkValidity(program.build()));
    }

    @ParameterizedTest(name = "testIllegalPreallocatedAnnotation({arguments})")
    @MethodSource("nonPointerValueTypes")
    void testIllegalPreallocatedAnnotation(ValueType variableType) {
        Program.Builder program = new Program.Builder();
        program.addGlobalVariable("v",
                GlobalDeclaration.create(variableType, ImmutableSet.of(IGlobalAttribute.PREALLOCATED)));

        assertThrows(IllegalVariableAttributeException.class, () -> ProgramValidator.checkValidity(program.build()));
    }

    private static Stream<ValueType> nonPointerValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().filter(v -> !ValueTypes.isPointer(v));
    }

    @ParameterizedTest(name = "testVariableDeclaration({arguments})")
    @ArgumentsSource(VariableValueTypesProvider.class)
    void testVariableDeclaration(ValueType variableType) {
        Program.Builder program = new Program.Builder();
        program.addGlobalVariable("v", GlobalDeclaration.create(variableType));

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program.build()));
    }
}
