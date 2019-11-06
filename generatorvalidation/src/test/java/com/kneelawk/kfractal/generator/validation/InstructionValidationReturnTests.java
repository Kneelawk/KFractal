package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.VoidConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static com.kneelawk.kfractal.generator.validation.ValueTypeUtils.createConstant;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InstructionValidationReturnTests {
    @ParameterizedTest(name = "testIncompatibleReturnTypes({arguments})")
    @ArgumentsSource(IncompatibleValueTypesProvider.class)
    void testIncompatibleReturnTypes(Pair<ValueType, ValueType> valueTypes) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(valueTypes.getLeft());
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(Return.create(createConstant(programBuilder, block, valueTypes.getRight())));
        function.addBlock(block.build());
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testReturnTypes({arguments})")
    @ArgumentsSource(VariableValueTypesProvider.class)
    void testReturnTypes(ValueType valueType) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(valueType);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(Return.create(createConstant(programBuilder, block, valueType)));
        function.addBlock(block.build());
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }

    @Test
    void testVoidReturnType() {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }
}
