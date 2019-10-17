package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.phi.Phi;
import com.kneelawk.kfractal.generator.api.ir.phi.PhiBranch;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentScope;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;

import java.util.List;

class TypeLookupValueVisitor implements IValueVisitor<TypeLookupResult> {
    private final TypeCache cache;
    private final TypeLookupVisitorContext context;

    TypeLookupValueVisitor(TypeCache cache,
                           TypeLookupVisitorContext context) {
        this.cache = cache;
        this.context = context;
    }

    @Override
    public TypeLookupResult visitArgumentReference(ArgumentReference argumentReference) {
        ArgumentScope argumentScope = argumentReference.getScope();
        int argumentIndex = argumentReference.getIndex();

        List<ArgumentDeclaration> scope;
        switch (argumentScope) {
            case CONTEXT:
                scope = context.getFunction().getContextVariables();
                break;
            case ARGUMENTS:
                scope = context.getFunction().getArguments();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + argumentScope);
        }

        return new TypeLookupResult.Found(scope.get(argumentIndex).getType());
    }

    @Override
    public TypeLookupResult visitInstructionReference(InstructionReference instructionReference)
            throws FractalException {
        // This node's type depends on others, therefore, it must be checked for reference loops
        if (context.getTraversedNodes().contains(instructionReference)) {
            return new TypeLookupResult.Error(TypeLookupResult.ErrorType.REFERENCE_LOOP);
        }

        TypeLookupVisitorContext newContext = context.builder().addTraversedNode(instructionReference).build();

        BasicBlock block = context.getFunction().getBlocks().get(instructionReference.getBlockIndex());
        switch (instructionReference.getScope()) {
            case PHI:
                return cache.getType(block.getPhis().get(instructionReference.getInstructionIndex()), newContext);
            case BODY:
                return cache.getType(block.getBody().get(instructionReference.getInstructionIndex()), newContext);
            default:
                throw new IllegalStateException("Unexpected value: " + instructionReference.getScope());
        }
    }

    @Override
    public TypeLookupResult visitBoolConstant(BoolConstant constant) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitIntConstant(IntConstant constant) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitRealConstant(RealConstant constant) {
        return new TypeLookupResult.Found(ValueTypes.REAL);
    }

    @Override
    public TypeLookupResult visitComplexConstant(ComplexConstant constant) {
        return new TypeLookupResult.Found(ValueTypes.COMPLEX);
    }

    @Override
    public TypeLookupResult visitNullPointer() {
        return new TypeLookupResult.Found(ValueTypes.NULL_POINTER);
    }

    @Override
    public TypeLookupResult visitNullFunction() {
        return new TypeLookupResult.Found(ValueTypes.NULL_FUNCTION);
    }

    @Override
    public TypeLookupResult visitVoid() {
        return new TypeLookupResult.Found(ValueTypes.VOID);
    }

    @Override
    public TypeLookupResult visitReturn(Return aReturn) {
        return new TypeLookupResult.Found(ValueTypes.VOID);
    }

    @Override
    public TypeLookupResult visitGlobalGet(GlobalGet globalGet) {
        return new TypeLookupResult.Found(
                context.getProgram().getGlobalVariables().get(globalGet.getGlobalIndex()).getType());
    }

    @Override
    public TypeLookupResult visitGlobalSet(GlobalSet globalSet) {
        return new TypeLookupResult.Found(ValueTypes.VOID);
    }

    @Override
    public TypeLookupResult visitBoolNot(BoolNot boolNot) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitBoolAnd(BoolAnd boolAnd) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitBoolOr(BoolOr boolOr) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitBoolIsEqual(BoolIsEqual boolIsEqual) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitIntAdd(IntAdd intAdd) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitIntSubtract(IntSubtract intSubtract) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitIntMultiply(IntMultiply intMultiply) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitIntDivide(IntDivide intDivide) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitIntModulo(IntModulo intModulo) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitIntPower(IntPower intPower) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitIntNot(IntNot intNot) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitIntAnd(IntAnd intAnd) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitIntOr(IntOr intOr) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitIntXor(IntXor intXor) {
        return new TypeLookupResult.Found(ValueTypes.INT);
    }

    @Override
    public TypeLookupResult visitIntIsEqual(IntIsEqual intIsEqual) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitIntIsGreater(IntIsGreater intIsGreater) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitRealAdd(RealAdd realAdd) {
        return new TypeLookupResult.Found(ValueTypes.REAL);
    }

    @Override
    public TypeLookupResult visitRealSubtract(RealSubtract realSubtract) {
        return new TypeLookupResult.Found(ValueTypes.REAL);
    }

    @Override
    public TypeLookupResult visitRealMultiply(RealMultiply realMultiply) {
        return new TypeLookupResult.Found(ValueTypes.REAL);
    }

    @Override
    public TypeLookupResult visitRealDivide(RealDivide realDivide) {
        return new TypeLookupResult.Found(ValueTypes.REAL);
    }

    @Override
    public TypeLookupResult visitRealPower(RealPower realPower) {
        return new TypeLookupResult.Found(ValueTypes.REAL);
    }

    @Override
    public TypeLookupResult visitRealIsEqual(RealIsEqual realIsEqual) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitRealIsGreater(RealIsGreater realIsGreater) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitRealComposeComplex(RealComposeComplex realComposeComplex) {
        return new TypeLookupResult.Found(ValueTypes.COMPLEX);
    }

    @Override
    public TypeLookupResult visitComplexAdd(ComplexAdd complexAdd) {
        return new TypeLookupResult.Found(ValueTypes.COMPLEX);
    }

    @Override
    public TypeLookupResult visitComplexSubtract(ComplexSubtract complexSubtract) {
        return new TypeLookupResult.Found(ValueTypes.COMPLEX);
    }

    @Override
    public TypeLookupResult visitComplexMultiply(ComplexMultiply complexMultiply) {
        return new TypeLookupResult.Found(ValueTypes.COMPLEX);
    }

    @Override
    public TypeLookupResult visitComplexDivide(ComplexDivide complexDivide) {
        return new TypeLookupResult.Found(ValueTypes.COMPLEX);
    }

    @Override
    public TypeLookupResult visitComplexPower(ComplexPower complexPower) {
        return new TypeLookupResult.Found(ValueTypes.COMPLEX);
    }

    @Override
    public TypeLookupResult visitComplexGetReal(ComplexGetReal complexGetReal) {
        return new TypeLookupResult.Found(ValueTypes.REAL);
    }

    @Override
    public TypeLookupResult visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) {
        return new TypeLookupResult.Found(ValueTypes.REAL);
    }

    @Override
    public TypeLookupResult visitComplexModulo(ComplexModulo complexModulo) {
        return new TypeLookupResult.Found(ValueTypes.REAL);
    }

    @Override
    public TypeLookupResult visitComplexIsEqual(ComplexIsEqual complexIsEqual) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitFunctionCreate(FunctionCreate functionCreate) {
        FunctionDefinition function = context.getProgram().getFunctions().get(functionCreate.getFunctionIndex());
        ImmutableList.Builder<ValueType> arguments = ImmutableList.builder();
        for (ArgumentDeclaration argument : function.getArguments()) {
            arguments.add(argument.getType());
        }
        return new TypeLookupResult.Found(ValueTypes.FUNCTION(function.getReturnType(), arguments.build()));
    }

    @Override
    public TypeLookupResult visitFunctionCall(FunctionCall functionCall) throws FractalException {
        if (context.getTraversedNodes().contains(functionCall)) {
            return new TypeLookupResult.Error(TypeLookupResult.ErrorType.REFERENCE_LOOP);
        }
        TypeLookupResult result =
                cache.getType(functionCall.getFunction(), context.builder().addTraversedNode(functionCall).build());
        if (!result.isFound()) {
            return result;
        }
        if (ValueTypes.isFunction(result.getType())) {
            return new TypeLookupResult.Found(ValueTypes.toFunction(result.getType()).getReturnType());
        } else {
            return new TypeLookupResult.Error(TypeLookupResult.ErrorType.INVALID_TYPE);
        }
    }

    @Override
    public TypeLookupResult visitFunctionIsEqual(FunctionIsEqual functionIsEqual) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitPointerAllocate(PointerAllocate pointerAllocate) {
        return new TypeLookupResult.Found(ValueTypes.POINTER(pointerAllocate.getType()));
    }

    @Override
    public TypeLookupResult visitPointerFree(PointerFree pointerFree) {
        return new TypeLookupResult.Found(ValueTypes.VOID);
    }

    @Override
    public TypeLookupResult visitPointerGet(PointerGet pointerGet) throws FractalException {
        if (context.getTraversedNodes().contains(pointerGet)) {
            return new TypeLookupResult.Error(TypeLookupResult.ErrorType.REFERENCE_LOOP);
        }
        TypeLookupResult result =
                cache.getType(pointerGet.getPointer(), context.builder().addTraversedNode(pointerGet).build());
        if (!result.isFound()) {
            return result;
        }
        if (ValueTypes.isPointer(result.getType())) {
            return new TypeLookupResult.Found(ValueTypes.toPointer(result.getType()).getPointerType());
        } else {
            return new TypeLookupResult.Error(TypeLookupResult.ErrorType.INVALID_TYPE);
        }
    }

    @Override
    public TypeLookupResult visitPointerSet(PointerSet pointerSet) {
        return new TypeLookupResult.Found(ValueTypes.VOID);
    }

    @Override
    public TypeLookupResult visitPointerIsEqual(PointerIsEqual pointerIsEqual) {
        return new TypeLookupResult.Found(ValueTypes.BOOL);
    }

    @Override
    public TypeLookupResult visitBranchConditional(BranchConditional branchConditional) {
        return new TypeLookupResult.Found(ValueTypes.VOID);
    }

    @Override
    public TypeLookupResult visitBranch(Branch branch) {
        return new TypeLookupResult.Found(ValueTypes.VOID);
    }

    @Override
    public TypeLookupResult visitPhi(Phi phi) throws FractalException {
        if (context.getTraversedNodes().contains(phi)) {
            return new TypeLookupResult.Error(TypeLookupResult.ErrorType.REFERENCE_LOOP);
        }

        TypeLookupVisitorContext newContext = context.builder().addTraversedNode(phi).build();

        TypeLookupResult branchType = new TypeLookupResult.Error(TypeLookupResult.ErrorType.INVALID_INSTRUCTION);
        for (PhiBranch branch : phi.getBranches()) {
            if (branch.getPreviousBlockIndex() < 0 ||
                    branch.getPreviousBlockIndex() > context.getFunction().getBlocks().size()) {
                continue;
            }

            branchType = cache.getType(phi, newContext);
            if (branchType.isFound()) {
                return branchType;
            }
        }

        return branchType;
    }
}
