package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableSet;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VoidConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VariableValidationTests {
    @Test
    void testIllegalVoidVariableType() {
        Program.Builder program = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setName("f");
        function.setReturnType(ValueTypes.VOID);
        function.addLocalVariable(VariableDeclaration.create(ValueTypes.VOID, "v"));
        function.addStatement(Return.create(VariableReference.create("v")));
        program.addFunction(function.build());

        assertThrows(IllegalVariableTypeException.class, () -> ProgramValidator.checkValidity(program.build()));
    }

    @Test
    void testIllegalNullFunctionVariableType() {
        Program.Builder program = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setName("f");
        function.setReturnType(ValueTypes.VOID);
        function.addLocalVariable(VariableDeclaration.create(ValueTypes.NULL_FUNCTION, "v"));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        program.addFunction(function.build());

        assertThrows(IllegalVariableTypeException.class, () -> ProgramValidator.checkValidity(program.build()));
    }

    @Test
    void testIllegalNullPointerVariableType() {
        Program.Builder program = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setName("f");
        function.setReturnType(ValueTypes.VOID);
        function.addLocalVariable(VariableDeclaration.create(ValueTypes.NULL_POINTER, "v"));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        program.addFunction(function.build());

        assertThrows(IllegalVariableTypeException.class, () -> ProgramValidator.checkValidity(program.build()));
    }

    @ParameterizedTest(name = "testIllegalPreallocatedAnnotation({arguments})")
    @MethodSource("nonPointerValueTypes")
    void testIllegalPreallocatedAnnotation(ValueType variableType) {
        Program.Builder program = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setName("f");
        function.setReturnType(ValueTypes.VOID);
        function.addLocalVariable(
                VariableDeclaration.create(variableType, "v", ImmutableSet.of(IAttribute.PREALLOCATED)));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        program.addFunction(function.build());

        assertThrows(IllegalVariableAttributeException.class, () -> ProgramValidator.checkValidity(program.build()));
    }

    private static Stream<ValueType> nonPointerValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().filter(v -> !ValueTypes.isPointer(v));
    }

    @ParameterizedTest(name = "testVariableDeclaration({arguments})")
    @ArgumentsSource(VariableValueTypesProvider.class)
    void testVariableDeclaration(ValueType variableType) {
        Program.Builder program = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setName("f");
        function.setReturnType(variableType);
        function.addLocalVariable(VariableDeclaration.create(variableType, "v"));
        function.addStatement(Return.create(VariableReference.create("v")));
        program.addFunction(function.build());

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program.build()));
    }
}
