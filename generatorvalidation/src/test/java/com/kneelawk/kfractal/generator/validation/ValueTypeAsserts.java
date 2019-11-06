package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.VoidConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;

import java.util.List;

import static com.kneelawk.kfractal.generator.validation.ValueTypeUtils.createConstant;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValueTypeAsserts {
    static void assertTwoIncompatibleValueTypes(List<ValueType> argumentTypes, InstructionCreator2 creator) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(creator.create(createConstant(programBuilder, block, argumentTypes.get(0)),
                createConstant(programBuilder, block, argumentTypes.get(1))));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    static void assertTwoCompatibleValueTypes(List<ValueType> argumentTypes, InstructionCreator2 creator) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(creator.create(createConstant(programBuilder, block, argumentTypes.get(0)),
                createConstant(programBuilder, block, argumentTypes.get(1))));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }

    static void assertOneIncompatibleValueType(ValueType argumentType, InstructionCreator1 creator) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(creator.create(createConstant(programBuilder, block, argumentType)));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    static void assertOneCompatibleValueType(ValueType argumentType, InstructionCreator1 creator) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(creator.create(createConstant(programBuilder, block, argumentType)));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }
}
