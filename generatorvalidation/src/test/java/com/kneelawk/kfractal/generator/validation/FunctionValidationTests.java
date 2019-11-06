package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static com.kneelawk.kfractal.generator.validation.ValueTypeUtils.createConstant;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FunctionValidationTests {
    // TODO: add missing return instruction tests.

    // should the incompatible context variable tests be moved here?

    @Test
    void testIllegalEmptyFunction() {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertThrows(FractalIRValidationException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @Test
    void testIllegalBasicBlockMissingTerminator() {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        function.addBlock(new BasicBlock.Builder().build());
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertThrows(FractalIRValidationException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatibleFunctionReturnType({arguments})")
    @ArgumentsSource(IncompatibleValueTypesProvider.class)
    void testIncompatibleFunctionReturnType(ImmutablePair<ValueType, ValueType> valueTypes) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(valueTypes.left);

        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(Return.create(createConstant(programBuilder, block, valueTypes.right)));

        function.addBlock(block.build());
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }
}
