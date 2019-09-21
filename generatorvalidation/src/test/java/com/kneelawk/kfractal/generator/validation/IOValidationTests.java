package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.ComplexAdd;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionCreate;
import com.kneelawk.kfractal.generator.api.ir.reference.VariableScope;
import com.kneelawk.kfractal.generator.api.ir.reference.VariableReference;
import com.kneelawk.kfractal.generator.api.ir.constant.VoidConstant;
import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IOValidationTests {
    @Test
    void testInvalidInputVariableReference() {
        // create the program
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
        functionBuilder.setReturnType(ValueTypes.VOID);
        var a = functionBuilder.addLocalVariable(GlobalDeclaration.create(ValueTypes.COMPLEX));
        functionBuilder.addStatement(Assign.create(a, VariableReference.create(VariableScope.LOCAL, 100)));
        functionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(functionBuilder.build());

        // test the validator
        assertThrows(MissingVariableReferenceException.class,
                () -> ProgramValidator.checkValidity(programBuilder.build()));
    }

    @Test
    void testValidInputVariableReference() {
        // create the program
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
        functionBuilder.setReturnType(ValueTypes.VOID);
        var a = functionBuilder.addLocalVariable(GlobalDeclaration.create(ValueTypes.COMPLEX));
        var b = functionBuilder.addLocalVariable(GlobalDeclaration.create(ValueTypes.COMPLEX));
        functionBuilder.addStatement(Assign.create(a, b));
        functionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(functionBuilder.build());

        // test the validator
        assertDoesNotThrow(() -> ProgramValidator.checkValidity(programBuilder.build()));
    }

    @Test
    void testInvalidOutputVariableReference() {
        // create the program
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
        functionBuilder.setReturnType(ValueTypes.VOID);
        functionBuilder.addStatement(
                Assign.create(VariableReference.create(VariableScope.LOCAL, 100), ComplexConstant.create(new Complex(1, 1))));
        functionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(functionBuilder.build());

        // test the validator
        assertThrows(MissingVariableReferenceException.class,
                () -> ProgramValidator.checkValidity(programBuilder.build()));
    }

    @Test
    void testValidOutputVariableReference() {
        // create the program
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
        functionBuilder.setReturnType(ValueTypes.VOID);
        var a = functionBuilder.addLocalVariable(GlobalDeclaration.create(ValueTypes.COMPLEX));
        functionBuilder
                .addStatement(Assign.create(a, ComplexConstant.create(new Complex(1, 1))));
        functionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(functionBuilder.build());

        // test the validator
        assertDoesNotThrow(() -> ProgramValidator.checkValidity(programBuilder.build()));
    }

    @Test
    void testMissingFunctionReference() {
        // create the program
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
        functionBuilder.setReturnType(ValueTypes.VOID);
        var a = functionBuilder.addLocalVariable(
                GlobalDeclaration.create(ValueTypes.FUNCTION(ValueTypes.VOID, ImmutableList.of())));
        functionBuilder.addStatement(Assign.create(a,
                FunctionCreate.create(100, ImmutableList.of())));
        functionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(functionBuilder.build());

        // test the validator
        assertThrows(MissingFunctionReferenceException.class,
                () -> ProgramValidator.checkValidity(programBuilder.build()));
    }

    @Test
    void testIncompatibleFunctionContextVariables() {
        // create the program
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder gFunctionBuilder = new FunctionDefinition.Builder();
        gFunctionBuilder.setReturnType(ValueTypes.COMPLEX);
        var ga = gFunctionBuilder.addContextVariable(GlobalDeclaration.create(ValueTypes.COMPLEX));
        var tmp0 = gFunctionBuilder.addLocalVariable(GlobalDeclaration.create(ValueTypes.COMPLEX));
        gFunctionBuilder.addStatement(ComplexAdd.create(tmp0, ga,
                ComplexConstant.create(new Complex(0, 2))));
        gFunctionBuilder.addStatement(Return.create(tmp0));
        int gIndex = programBuilder.addFunction(gFunctionBuilder.build());

        FunctionDefinition.Builder fFunctionBuilder = new FunctionDefinition.Builder();
        fFunctionBuilder.setReturnType(ValueTypes.VOID);
        var fa = fFunctionBuilder.addLocalVariable(
                GlobalDeclaration.create(ValueTypes.FUNCTION(ValueTypes.COMPLEX, ImmutableList.of())));
        fFunctionBuilder.addStatement(
                Assign.create(fa, FunctionCreate.create(gIndex, ImmutableList.of())));
        fFunctionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(fFunctionBuilder.build());

        // test the validator
        assertThrows(IncompatibleFunctionContextException.class,
                () -> ProgramValidator.checkValidity(programBuilder.build()));
    }

    @Test
    void testValidFunctionReference() {
        // create the program
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder gFunctionBuilder = new FunctionDefinition.Builder();
        gFunctionBuilder.setReturnType(ValueTypes.COMPLEX);
        var ga = gFunctionBuilder.addContextVariable(GlobalDeclaration.create(ValueTypes.COMPLEX));
        var tmp0 = gFunctionBuilder.addLocalVariable(GlobalDeclaration.create(ValueTypes.COMPLEX));
        gFunctionBuilder.addStatement(ComplexAdd.create(tmp0, ga,
                ComplexConstant.create(new Complex(0, 2))));
        gFunctionBuilder.addStatement(Return.create(tmp0));
        int gIndex = programBuilder.addFunction(gFunctionBuilder.build());

        FunctionDefinition.Builder fFunctionBuilder = new FunctionDefinition.Builder();
        fFunctionBuilder.setReturnType(ValueTypes.VOID);
        var fa = fFunctionBuilder.addLocalVariable(
                GlobalDeclaration.create(ValueTypes.FUNCTION(ValueTypes.COMPLEX, ImmutableList.of())));
        fFunctionBuilder.addStatement(Assign.create(fa,
                FunctionCreate.create(gIndex, ImmutableList.of(ComplexConstant.create(new Complex(2, 0))))));
        fFunctionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(fFunctionBuilder.build());

        // test the validator
        assertDoesNotThrow(() -> ProgramValidator.checkValidity(programBuilder.build()));
    }
}
