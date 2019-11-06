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

class TypeLookupValueVisitor implements IValueVisitor<ValueType> {
    private final TypeCache cache;
    private final TypeLookupVisitorContext context;

    TypeLookupValueVisitor(TypeCache cache,
                           TypeLookupVisitorContext context) {
        this.cache = cache;
        this.context = context;
    }

    @Override
    public ValueType visitArgumentReference(ArgumentReference argumentReference) {
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

        return scope.get(argumentIndex).getType();
    }

    @Override
    public ValueType visitInstructionReference(InstructionReference instructionReference)
            throws FractalException {
        // This node's type depends on others, therefore, it must be checked for reference loops
        if (context.getTraversedNodes().contains(instructionReference)) {
            throw new CyclicTypeLookupException("InstructionReference references one of its ancestors");
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
    public ValueType visitBoolConstant(BoolConstant constant) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitIntConstant(IntConstant constant) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitRealConstant(RealConstant constant) {
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitComplexConstant(ComplexConstant constant) {
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitNullPointer() {
        return ValueTypes.NULL_POINTER;
    }

    @Override
    public ValueType visitNullFunction() {
        return ValueTypes.NULL_FUNCTION;
    }

    @Override
    public ValueType visitVoid() {
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitReturn(Return aReturn) {
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitGlobalGet(GlobalGet globalGet) throws FractalException {
        String name = globalGet.getGlobalName();
        if (!context.getProgram().getGlobalVariables().containsKey(name)) {
            throw new MissingVariableReferenceException("Cannot determine the type of a global variable that does not exist");
        }
        return context.getProgram().getGlobalVariables().get(name).getType();
    }

    @Override
    public ValueType visitGlobalSet(GlobalSet globalSet) {
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitBoolNot(BoolNot boolNot) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitBoolAnd(BoolAnd boolAnd) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitBoolOr(BoolOr boolOr) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitBoolIsEqual(BoolIsEqual boolIsEqual) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitIntAdd(IntAdd intAdd) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntSubtract(IntSubtract intSubtract) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntMultiply(IntMultiply intMultiply) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntDivide(IntDivide intDivide) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntModulo(IntModulo intModulo) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntPower(IntPower intPower) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntNot(IntNot intNot) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntAnd(IntAnd intAnd) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntOr(IntOr intOr) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntXor(IntXor intXor) {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntIsEqual(IntIsEqual intIsEqual) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitIntIsGreater(IntIsGreater intIsGreater) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitRealAdd(RealAdd realAdd) {
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitRealSubtract(RealSubtract realSubtract) {
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitRealMultiply(RealMultiply realMultiply) {
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitRealDivide(RealDivide realDivide) {
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitRealPower(RealPower realPower) {
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitRealIsEqual(RealIsEqual realIsEqual) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitRealIsGreater(RealIsGreater realIsGreater) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitRealComposeComplex(RealComposeComplex realComposeComplex) {
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexAdd(ComplexAdd complexAdd) {
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexSubtract(ComplexSubtract complexSubtract) {
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexMultiply(ComplexMultiply complexMultiply) {
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexDivide(ComplexDivide complexDivide) {
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexPower(ComplexPower complexPower) {
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexGetReal(ComplexGetReal complexGetReal) {
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) {
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitComplexModulo(ComplexModulo complexModulo) {
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitComplexIsEqual(ComplexIsEqual complexIsEqual) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitFunctionCreate(FunctionCreate functionCreate) throws FractalException {
        String name = functionCreate.getFunctionName();
        if (!context.getProgram().getFunctions().containsKey(name)) {
            throw new MissingFunctionReferenceException("Cannot determine type of a function that does not exist");
        }

        FunctionDefinition function = context.getProgram().getFunctions().get(name);
        ImmutableList.Builder<ValueType> arguments = ImmutableList.builder();
        for (ArgumentDeclaration argument : function.getArguments()) {
            arguments.add(argument.getType());
        }
        return ValueTypes.FUNCTION(function.getReturnType(), arguments.build());
    }

    @Override
    public ValueType visitFunctionCall(FunctionCall functionCall) throws FractalException {
        if (context.getTraversedNodes().contains(functionCall)) {
            throw new CyclicTypeLookupException("FunctionCall type depends on that of one of its ancestors");
        }
        ValueType result =
                cache.getType(functionCall.getFunction(), context.builder().addTraversedNode(functionCall).build());
        if (ValueTypes.isFunction(result)) {
            return ValueTypes.toFunction(result).getReturnType();
        } else {
            throw new IncompatibleValueTypeException("FunctionCall cannot get the return type of something that is not a function");
        }
    }

    @Override
    public ValueType visitFunctionIsEqual(FunctionIsEqual functionIsEqual) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitPointerAllocate(PointerAllocate pointerAllocate) {
        return ValueTypes.POINTER(pointerAllocate.getType());
    }

    @Override
    public ValueType visitPointerFree(PointerFree pointerFree) {
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitPointerGet(PointerGet pointerGet) throws FractalException {
        if (context.getTraversedNodes().contains(pointerGet)) {
            throw new CyclicTypeLookupException("PointerGet type depends on that of one of its ancestors");
        }
        ValueType result =
                cache.getType(pointerGet.getPointer(), context.builder().addTraversedNode(pointerGet).build());
        if (ValueTypes.isPointer(result)) {
            return ValueTypes.toPointer(result).getPointerType();
        } else {
            throw new IncompatibleValueTypeException("PointerGet cannot get the enclosed type of something that is not a pointer");
        }
    }

    @Override
    public ValueType visitPointerSet(PointerSet pointerSet) {
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitPointerIsEqual(PointerIsEqual pointerIsEqual) {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitBranchConditional(BranchConditional branchConditional) {
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitBranch(Branch branch) {
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitPhi(Phi phi) throws FractalException {
        if (context.getTraversedNodes().contains(phi)) {
            throw new CyclicTypeLookupException("Phi instruction is one of its own ancestors");
        }

        TypeLookupVisitorContext newContext = context.builder().addTraversedNode(phi).build();

        ValueType branchType = null;
        PhiInputValidationException exception = new PhiInputValidationException("Unable to determine phi instruction type");
        for (PhiBranch branch : phi.getBranches()) {
            if (branch.getPreviousBlockIndex() < 0 ||
                    branch.getPreviousBlockIndex() > context.getFunction().getBlocks().size()) {
                continue;
            }

            try {
                branchType = cache.getType(phi, newContext);
            } catch (FractalIRValidationException e) {
                exception.addSuppressed(e);
            }
        }

        if (branchType == null) {
            throw exception;
        }

        return branchType;
    }
}
