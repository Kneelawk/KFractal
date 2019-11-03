package com.kneelawk.kfractal.generator.simple;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.IProgramEngine;
import com.kneelawk.kfractal.generator.api.engine.value.*;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.phi.Phi;
import com.kneelawk.kfractal.generator.api.ir.phi.PhiBranch;
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
        function.setReturnType(ValueTypes.COMPLEX);
        var z = function.addArgument(ArgumentDeclaration.create(ValueTypes.COMPLEX));
        BasicBlock.Builder block = function.addBlock();
        block.addValue(Return.create(ComplexMultiply.create(z, z)));
        int fIndex = programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        // setup the engine
        IProgramEngine engine = new SimpleProgramEngineFactory().newProgramEngine();
        engine.initialize(program);
        IEngineValueFactory valueFactory = engine.getValueFactory();

        // get the function
        IFunctionValue f = engine.getFunction(fIndex, ImmutableList.of());

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
        function.setReturnType(ValueTypes.INT);
        // add the arguments
        var zArgument = function.addArgument(ArgumentDeclaration.create(ValueTypes.COMPLEX));
        var c = function.addArgument(ArgumentDeclaration.create(ValueTypes.COMPLEX));

        BasicBlock.Builder loopCondition = function.addBlock();
        BasicBlock.Builder loopArithmetic = function.addBlock();
        BasicBlock.Builder loopIncrement = function.addBlock();
        BasicBlock.Builder ret = function.addBlock();

        Phi.Builder iterationsBuilder = new Phi.Builder();
        iterationsBuilder.addBranch(PhiBranch.create(IntConstant.create(0), -1));
        var iterations = loopCondition.addPhi(iterationsBuilder::build);

        Phi.Builder zBuilder = new Phi.Builder();
        zBuilder.addBranch(PhiBranch.create(zArgument, -1));
        var z = loopCondition.addPhi(zBuilder::build);

        loopCondition.addValue(BranchConditional
                .create(BoolNot.create(IntIsGreater.create(IntConstant.create(500), iterations)), ret.getBlockIndex(),
                        loopArithmetic.getBlockIndex()));

        var z1 = loopArithmetic.addValue(ComplexMultiply.create(z, z));
        var z2 = loopArithmetic.addValue(ComplexMultiply.create(z1, z1));
        var res = loopArithmetic.addValue(ComplexAdd.create(ComplexMultiply.create(z2, z), c));
        loopArithmetic.addValue(BranchConditional
                .create(RealIsGreater.create(ComplexModulo.create(res), RealConstant.create(4)),
                        loopIncrement.getBlockIndex(), ret.getBlockIndex()));

        var iterationsIncrement = loopIncrement.addValue(IntAdd.create(iterations, IntConstant.create(1)));
        loopIncrement.addValue(Branch.create(loopCondition.getBlockIndex()));
        iterationsBuilder.addBranch(PhiBranch.create(iterationsIncrement, loopIncrement.getBlockIndex()));
        zBuilder.addBranch(PhiBranch.create(res, loopIncrement.getBlockIndex()));

        ret.addValue(Return.create(iterations));

        int fIndex = programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        // setup the engine
        IProgramEngine engine = new SimpleProgramEngineFactory().newProgramEngine();
        engine.initialize(program);
        IEngineValueFactory valueFactory = engine.getValueFactory();

        // get the function
        IFunctionValue f = engine.getFunction(fIndex, ImmutableList.of());

        // invoke the function
        IEngineValue result = f.invoke(ImmutableList.of(valueFactory.newComplex(new Complex(0, 0)),
                valueFactory.newComplex(new Complex(0.7288, -0.532))));

        // test the results
        assertTrue(result instanceof IIntValue, "Result should be an int");
        assertEquals(73, ((IIntValue) result).getValue(), "The result should be 73");
    }
}
