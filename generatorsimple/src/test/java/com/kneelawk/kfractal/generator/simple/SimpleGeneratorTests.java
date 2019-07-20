package com.kneelawk.kfractal.generator.simple;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.IProgramEngine;
import com.kneelawk.kfractal.generator.api.engine.value.*;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.VariableDeclaration;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;
import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Kneelawk on 7/19/19.
 */
class SimpleGeneratorTests {
    @Test
    void testSimpleArithmetic() throws FractalException {
        // build the program
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setName("f");
        function.setReturnType(ValueTypes.COMPLEX);
        function.addArgument(VariableDeclaration.create(ValueTypes.COMPLEX, "z"));
        function.addStatement(ComplexMultiply
                .create(VariableReference.create("z"), VariableReference.create("z"), VariableReference.create("z")));
        function.addStatement(Return.create(VariableReference.create("z")));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        // setup the engine
        IProgramEngine engine = new SimpleProgramEngineFactory().newProgramEngine();
        engine.initialize(program);
        IEngineValueFactory valueFactory = engine.getValueFactory();

        // get the function
        IFunctionValue f = engine.getFunction("f", ImmutableList.of());

        // invoke the function
        IEngineValue result = f.invoke(ImmutableList.of(valueFactory.newComplex(new Complex(1, 1))));

        // test the results
        assertTrue(result instanceof IComplexValue, "Result should be a complex");
        assertEquals(((IComplexValue) result).getValue(), new Complex(0, 2), "(1 + 1i)^2 should equal (0 + 2i)");
    }

    @Test
    void testComplicatedArithmetic() throws FractalException {
        // build the program
        /*
         * If this program were in c++, it would essentially be:
         * using namespace std;
         * int f(complex<double> z, complex<double> c) {
         *     int iterations;
         *     for (iterations = 0; iterations < 500; iterations++) {
         *         z = z * z * z * z * z + c;
         *         if (z.abs() > 4) {
         *             break;
         *         }
         *     }
         *     return iterations;
         * }
         */
        Program.Builder programBuilder = new Program.Builder();
        // setup the function
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setName("f");
        function.setReturnType(ValueTypes.INT);
        // add the arguments
        function.addArgument(VariableDeclaration.create(ValueTypes.COMPLEX, "z"));
        function.addArgument(VariableDeclaration.create(ValueTypes.COMPLEX, "c"));
        // add the local variables
        function.addLocalVariable(VariableDeclaration.create(ValueTypes.INT, "iterations"));
        function.addLocalVariable(VariableDeclaration.create(ValueTypes.REAL, "abs"));
        function.addLocalVariable(VariableDeclaration.create(ValueTypes.BOOL, "continue"));
        function.addLocalVariable(VariableDeclaration.create(ValueTypes.COMPLEX, "zTmp"));
        // make continue 'true' because we know the loop has to do a calculation at least once
        function.addStatement(Assign.create(VariableReference.create("continue"), BoolConstant.create(true)));
        // start on the loop
        While.Builder loop = new While.Builder();
        // the loop only continues if the 'continue' variable is 'true'
        loop.setCondition(VariableReference.create("continue"));
        // do the z * z * z * z * z
        loop.addWhileTrue(ComplexMultiply.create(VariableReference.create("zTmp"), VariableReference.create("z"),
                VariableReference.create("z")));
        loop.addWhileTrue(ComplexMultiply.create(VariableReference.create("zTmp"), VariableReference.create("zTmp"),
                VariableReference.create("zTmp")));
        loop.addWhileTrue(ComplexMultiply.create(VariableReference.create("z"), VariableReference.create("zTmp"),
                VariableReference.create("z")));
        // do the + c and store the whole thing back in z
        loop.addWhileTrue(ComplexAdd
                .create(VariableReference.create("z"), VariableReference.create("z"), VariableReference.create("c")));
        // get the 'abs()' of z
        loop.addWhileTrue(ComplexModulo.create(VariableReference.create("abs"), VariableReference.create("z")));
        // set continue to false if the abs is greater than 4
        loop.addWhileTrue(RealIsGreaterOrEqual
                .create(VariableReference.create("continue"), RealConstant.create(4), VariableReference.create("abs")));
        // do the for-loop functionality of incrementing iterations if we've gotten this far and then checking whether
        // or not to continue based on the new iterations value
        If.Builder continueCheck = new If.Builder();
        continueCheck.setCondition(VariableReference.create("continue"));
        // increment iterations
        continueCheck.addIfTrue(
                IntAdd.create(VariableReference.create("iterations"), VariableReference.create("iterations"),
                        IntConstant.create(1)));
        // check to see if we've iterated too many times
        continueCheck.addIfTrue(IntIsGreater.create(VariableReference.create("continue"), IntConstant.create(500),
                VariableReference.create("iterations")));
        loop.addWhileTrue(continueCheck.build());
        function.addStatement(loop.build());
        // return the final value of iterations
        function.addStatement(Return.create(VariableReference.create("iterations")));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        // setup the engine
        IProgramEngine engine = new SimpleProgramEngineFactory().newProgramEngine();
        engine.initialize(program);
        IEngineValueFactory valueFactory = engine.getValueFactory();

        // get the function
        IFunctionValue f = engine.getFunction("f", ImmutableList.of());

        // invoke the function
        IEngineValue result = f.invoke(ImmutableList.of(valueFactory.newComplex(new Complex(0, 0)),
                valueFactory.newComplex(new Complex(0.7288, -0.532))));

        // test the results
        assertTrue(result instanceof IIntValue, "Result should be an int");
        assertEquals(((IIntValue) result).getValue(), 73, "The result should be 73");
    }
}
