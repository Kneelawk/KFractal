package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.BasicBlock;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.constant.VoidConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.BoolNot;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CyclicInstructionTests {
    @Test
    void testCyclicInstructionDetection() throws NoSuchFieldException, IllegalAccessException {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();

        // mimic mutability
        BoolNot not = BoolNot.create(VoidConstant.INSTANCE);
        Field input = BoolNot.class.getDeclaredField("input");
        input.setAccessible(true);
        input.set(not, BoolNot.create(not));

        block.addValue(not);
        function.addBlock(block.build());
        programBuilder.addFunction("f", function.build());

        Program program = programBuilder.build();

        assertThrows(CyclicProceduralInstructionException.class, () -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }
}
