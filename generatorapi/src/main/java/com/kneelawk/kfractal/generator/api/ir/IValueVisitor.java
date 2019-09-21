package com.kneelawk.kfractal.generator.api.ir;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;
import com.kneelawk.kfractal.generator.api.ir.reference.VariableReference;

public interface IValueVisitor<R> {
    /* References */

    R visitVariableReference(VariableReference variableReference) throws FractalException;

    R visitInstructionReference(InstructionReference instructionReference) throws FractalException;

    /* Constants */

    R visitBoolConstant(BoolConstant constant) throws FractalException;

    R visitIntConstant(IntConstant constant) throws FractalException;

    R visitRealConstant(RealConstant constant) throws FractalException;

    R visitComplexConstant(ComplexConstant constant) throws FractalException;

    R visitNullPointer() throws FractalException;

    R visitNullFunction() throws FractalException;

    R visitVoid() throws FractalException;

    /* General Purpose Operations */

    R visitReturn(Return aReturn) throws FractalException;

    /* Global Operations */

    R visitGlobalGet(GlobalGet globalGet) throws FractalException;

    R visitGlobalSet(GlobalSet globalSet) throws FractalException;

    /* Bool Operations */

    R visitBoolNot(BoolNot boolNot) throws FractalException;

    R visitBoolAnd(BoolAnd boolAnd) throws FractalException;

    R visitBoolOr(BoolOr boolOr) throws FractalException;

    R visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalException;

    /* Int Operations */

    R visitIntAdd(IntAdd intAdd) throws FractalException;

    R visitIntSubtract(IntSubtract intSubtract) throws FractalException;

    R visitIntMultiply(IntMultiply intMultiply) throws FractalException;

    R visitIntDivide(IntDivide intDivide) throws FractalException;

    R visitIntModulo(IntModulo intModulo) throws FractalException;

    R visitIntPower(IntPower intPower) throws FractalException;

    R visitIntNot(IntNot intNot) throws FractalException;

    R visitIntAnd(IntAnd intAnd) throws FractalException;

    R visitIntOr(IntOr intOr) throws FractalException;

    R visitIntXor(IntXor intXor) throws FractalException;

    R visitIntIsEqual(IntIsEqual intIsEqual) throws FractalException;

    R visitIntIsGreater(IntIsGreater intIsGreater) throws FractalException;

    R visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalException;

    /* Real Operations */

    R visitRealAdd(RealAdd realAdd) throws FractalException;

    R visitRealSubtract(RealSubtract realSubtract) throws FractalException;

    R visitRealMultiply(RealMultiply realMultiply) throws FractalException;

    R visitRealDivide(RealDivide realDivide) throws FractalException;

    R visitRealPower(RealPower realPower) throws FractalException;

    R visitRealIsEqual(RealIsEqual realIsEqual) throws FractalException;

    R visitRealIsGreater(RealIsGreater realIsGreater) throws FractalException;

    R visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalException;

    R visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalException;

    /* Complex Operations */

    R visitComplexAdd(ComplexAdd complexAdd) throws FractalException;

    R visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalException;

    R visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalException;

    R visitComplexDivide(ComplexDivide complexDivide) throws FractalException;

    R visitComplexPower(ComplexPower complexPower) throws FractalException;

    R visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalException;

    R visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalException;

    R visitComplexModulo(ComplexModulo complexModulo) throws FractalException;

    R visitComplexIsEqual(ComplexIsEqual complexIsEqual) throws FractalException;

    /* Function Operations */

    R visitFunctionCreate(FunctionCreate functionCreate) throws FractalException;

    R visitFunctionCall(FunctionCall functionCall) throws FractalException;

    R visitFunctionIsEqual(FunctionIsEqual functionIsEqual) throws FractalException;

    /* Pointer Operations */

    R visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalException;

    R visitPointerFree(PointerFree pointerFree) throws FractalException;

    R visitPointerGet(PointerGet pointerGet) throws FractalException;

    R visitPointerSet(PointerSet pointerSet) throws FractalException;

    R visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalException;

    /* Control-Flow Operations */

    R visitBranchConditional(BranchConditional branchConditional) throws FractalException;

    R visitBranch(Branch branch) throws FractalException;
}
