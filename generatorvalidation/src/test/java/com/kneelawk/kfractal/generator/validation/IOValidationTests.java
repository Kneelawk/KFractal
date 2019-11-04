package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.ComplexAdd;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionCall;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionCreate;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentScope;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import com.kneelawk.kfractal.generator.api.ir.constant.VoidConstant;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;
import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IOValidationTests {
    // TODO: Do validation for new InstructionReferences.
    // This involves both invalid block and invalid index.
    // This also involves references in phi instructions.
    // Phi instruction block references should probably be in their own test though.

    @Test
    void testMissingFunctionReference() {
        // create the program
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
        functionBuilder.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(FunctionCall.create(FunctionCreate.create(100)));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        functionBuilder.addBlock(block.build());
        programBuilder.addFunction(functionBuilder.build());

        Program program = programBuilder.build();

        // test the validator
        assertThrows(MissingFunctionReferenceException.class,
                () -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }

    @Test
    void testIncompatibleFunctionContextVariables() {
        // create the program
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder gFunctionBuilder = new FunctionDefinition.Builder();
        gFunctionBuilder.setReturnType(ValueTypes.COMPLEX);
        var ga = gFunctionBuilder.addContextVariable(ArgumentDeclaration.create(ValueTypes.COMPLEX));
        BasicBlock.Builder gBlock = new BasicBlock.Builder();
        gBlock.addValue(Return.create(ComplexAdd.create(ga, ComplexConstant.create(new Complex(0, 2)))));
        gFunctionBuilder.addBlock(gBlock.build());
        int gIndex = programBuilder.addFunction(gFunctionBuilder.build());

        FunctionDefinition.Builder fFunctionBuilder = new FunctionDefinition.Builder();
        fFunctionBuilder.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder fBlock = new BasicBlock.Builder();
        fBlock.addValue(FunctionCreate.create(gIndex));
        fBlock.addValue(Return.create(VoidConstant.INSTANCE));
        fFunctionBuilder.addBlock(fBlock.build());
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
        var ga = gFunctionBuilder.addContextVariable(ArgumentDeclaration.create(ValueTypes.COMPLEX));
        BasicBlock.Builder gBlock = new BasicBlock.Builder();
        gBlock.addValue(Return.create(ComplexAdd.create(ga, ComplexConstant.create(new Complex(0, 2)))));
        gFunctionBuilder.addBlock(gBlock.build());
        int gIndex = programBuilder.addFunction(gFunctionBuilder.build());

        FunctionDefinition.Builder fFunctionBuilder = new FunctionDefinition.Builder();
        fFunctionBuilder.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder fBlock = new BasicBlock.Builder();
        fBlock.addValue(FunctionCreate.create(gIndex, ImmutableList.of(ComplexConstant.create(new Complex(2, 0)))));
        fBlock.addValue(Return.create(VoidConstant.INSTANCE));
        fFunctionBuilder.addBlock(fBlock.build());
        programBuilder.addFunction(fFunctionBuilder.build());

        // test the validator
        assertDoesNotThrow(() -> ProgramValidator.checkValidity(programBuilder.build()));
    }
}
