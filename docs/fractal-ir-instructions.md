# Fractal IR Instructions

The Fractal IR Instructions are:
* [Miscellaneous Instructions](#miscellaneous-instructions)
    * [`Return`](#return)
* [Boolean Instructions](#boolean-instructions)
    * [`BoolNot`](#boolnot)
    * [`BoolAnd`](#booland)
    * [`BoolOr`](#boolor)
    * [`BoolIsEqual`](#boolisequal)
* [Int Instructions](#int-instructions)
    * [`IntAdd`](#intadd)
    * [`IntSubtract`](#intsubtract)
    * [`IntMultiply`](#intmultiply)
    * [`IntDivide`](#intdivide)
    * [`IntModulo`](#intmodulo)
    * [`IntPower`](#intpower)
    * [`IntNot`](#intnot)
    * [`IntAnd`](#intand)
    * [`IntOr`](#intor)
    * [`IntXor`](#intxor)
    * [`IntIsEqual`](#intisequal)
    * [`IntIsGreater`](#intisgreater)
    * [`IntIsGreaterOrEqual`](#intisgreaterorequal)
* [Real Instructions](#real-instructions)
    * [`RealAdd`](#realadd)
    * [`RealSubtract`](#realsubtract)
    * [`RealMultiply`](#realmultiply)
    * [`RealDivide`](#realdivide)
    * [`RealPower`](#realpower)
    * [`RealIsEqual`](#realisequal)
    * [`RealIsGreater`](#realisgreater)
    * [`RealIsGreaterOrEqual`](#realisgreaterorequal)
    * [`RealComposeComplex`](#realcomposecomplex)
* [Complex Instructions](#complex-instructions)
    * [`ComplexAdd`](#complexadd)
    * [`ComplexSubtract`](#complexsubtract)
    * [`ComplexMultiply`](#complexmultiply)
    * [`ComplexDivide`](#complexdivide)
    * [`ComplexPower`](#complexpower)
    * [`ComplexGetReal`](#complexgetreal)
    * [`ComplexGetImaginary`](#complexgetimaginary)
    * [`ComplexModulo`](#complexmodulo)
    * [`ComplexIsEqual`](#complexisequal)
* [Function Instructions](#function-instructions)
    * [`FunctionCreate`](#functioncreate)
    * [`FunctionCall`](#functioncall)
    * [`FunctionIsEqual`](#functionisequal)
* [Pointer Instructions](#pointer-instructions)
    * [`PointerAllocate`](#pointerallocate)
    * [`PointerFree`](#pointerfree)
    * [`PointerGet`](#pointerget)
    * [`PointerSet`](#pointerset)
    * [`PointerIsEqual`](#pointerisequal)
* [Control-Flow Instructions](#control-flow-instructions)
    * [`Branch`](#branch)
    * [`BranchConditional`](#branchconditional)

## Miscellaneous Instructions

### Return
Returns a value or void constant from a function, exiting the current function.

`Return(* returnValue)`

## Boolean Instructions

### BoolNot
Gets the not value of the argument.

`BoolNot(Bool input)`

### BoolAnd
Ands the two arguments together.

`BoolAnd(Bool left, Bool right)`

### BoolOr
Ors the two arguments together.

`BoolOr(Bool left, Bool right)`

### BoolIsEqual
Checks to see if the two boolean arguments are equal. This is the same as a boolean XNOR.

`BoolIsEqual(Bool left, Bool right)`

## Int Instructions

### IntAdd
Adds the two arguments as ints.

`IntAdd(Int leftAddend, Int rightAddend)`

### IntSubtract
Subtracts the second argument from the first argument.

`IntSubtract(Int minuend, Int subtrahend)`

### IntMultiply
Multiplies the two arguments together.

`IntMultiply(Int leftFactor, Int rightFactor)`

### IntDivide
Divides the first argument by the second argument.

`IntDivide(Int dividend, Int divisor)`

### IntModulo
Finds the modulus of dividing the first argument by the second argument.

`IntModulo(Int dividend, Int divisor)`

### IntPower
Finds the result of raising the first argument to the power of the second argument.

`IntPower(Int base, Int exponent)`

### IntNot
Finds the bitwise not of the argument.

`IntNot(Int input)`

### IntAnd
Bitwise ands the two arguments together.

`IntAnd(Int left, Int right)`

### IntOr
Bitwise ors the two arguments together.

`IntOr(Int left, Int right)`

### IntXor
Bitwise xors the two arguments together.

`IntXor(Int left, Int right)`

### IntIsEqual
Checks to see if the two arguments have the same integer value.

`IntIsEqual(Int left, Int right)`

### IntIsGreater
Checks to see if the first argument is greater than the second argument.

`IntIsGreater(Int subject, Int basis)`

### IntIsGreaterOrEqual
Checks to see if the first argument is greater than or equal to the second argument.

`IntIsGreaterOrEqual(Int subject, Int basis)`

## Real Instructions

### RealAdd
Adds the two arguments together.

`RealAdd(Real leftAddend, Real rightAddend)`

### RealSubtract
Subtracts the second argument from the first argument.

`RealSubtract(Real minuend, Real subtrahend)`

### RealMultiply
Multiplies the two arguments together.

`RealMultiply(Real leftFactor, Real rightFactor)`

### RealDivide
Divides the first argument by the second argument.

`RealDivide(Real dividend, Real divisor)`

### RealPower
Raises the first argument to the power of the second argument.

`RealPower(Real base, Real exponent)`

### RealIsEqual
Checks to see if the two arguments have the same real value.

`RealIsEqual(Real left, Real right)`

### RealIsGreater
Checks to see if the first argument has a greater real value than the second argument.

`RealIsGreater(Real subject, Real basis)`

### RealIsGreaterOrEqual
Checks to see if the first argument has a real value greater than or equal to the real value of the second argument.

`RealIsGreaterOrEqual(Real subject, Real basis)`

### RealComposeComplex
Composes a complex number with the first argument as its real component and the second argument as its imaginary
component.

`RealComposeComplex(Real real, Real imaginary)`

## Complex Instructions

### ComplexAdd
Adds the two complex numbers.

`ComplexAdd(Complex leftAddend, Complex rightAddend)`

### ComplexSubtract
Subtracts the second argument from the first argument.

`ComplexSubtract(Complex minuend, Complex subtrahend)`

### ComplexMultiply
Multiplies the two complex numbers.

`ComplexMultiply(Complex leftFactor, Complex rightFactor)`

### ComplexDivide
Divides the first argument by the second argument.

`ComplexDivide(Complex dividend, Complex divisor)`

### ComplexPower
Raises the first argument to the power of the second argument.

`ComplexPower(Complex base, Complex exponent)`

### ComplexGetReal
Gets the real component of the argument.

`ComplexGetReal(Complex complex)`

### ComplexGetImaginary
Gets the imaginary component of the argument.

`ComplexGetImaginary(Complex complex)`

### ComplexModulo
Computes the complex modulo of the argument. A complex number's modulus is that complex number's absolute distance
from the origin as computed by sqrt(&lt;real-component&gt;^2 + &lt;imaginary-component&gt;^2).

`ComplexModulo(Complex complex)`

### ComplexIsEqual
Checks to see if the two arguments have the same real and imaginary values.

`ComplexIsEqual(Complex left, Complex right)`

## Function Instructions

### FunctionCreate
Constructs a function context variable using the given function name and context variable values.

`FunctionCreate(functionName, [ ** contextVariables ])`

### FunctionCall
Calls the function represented by the function context that is the first argument with arguments in the list that is
the second argument. This instruction's return value is that of the called function, or void if the called function
returned void. Each of the arguments in the specified argument list must be the same types as the arguments in the
function.

`FunctionCall(Function(*, [ ** ]) function, [ ** arguments ])`

### FunctionIsEqual
Checks to see if the last two function arguments reference the same function and hold the same context variable values.

`FunctionIsEqual(Function(*, [ ** ]) left, Function(*, [ ** ]) right)`

## Pointer Instructions

### PointerAllocate
Allocates data for a pointer on the language-backed heap. All allocations that have not been freed should be freed
 when the program exits.

`PointerAllocate(type)`

### PointerFree
Frees the data pointed to by the handle in the only argument if the current language backend supports explicitly
freeing pointers.

`PointerFree(Pointer(*) pointer)`

### PointerGet
Retrieves the data pointed to by the handle in the only argument.

`PointerGet(Pointer(*) pointer)`

### PointerSet
Sets the value of the data pointed to by the handle in the first argument to the data in the second argument.

`PointerSet(Pointer(*) pointer, * data)`

### PointerIsEqual
Checks to see if the two pointer arguments hold handles to the same data.

`PointerIsEqual(Pointer(*) left, Pointer(*) right)`

## Control-Flow Instructions

### Branch
Unconditionally jumps to the basic block referenced by its argument.

`Branch(blockIndex)`

### BranchConditional
Jumps to one of the two BasicBlocks referenced by the two last arguments depending on the boolean value of the first
argument.

`BranchConditional(Bool condition, trueBlockIndex, falseBlockIndex)`

### Phi
Merges two or more values declared in different BasicBlocks by selecting one depending on which basic block was
executed previously.

`Phi([ PhiBranch(* value, previousBlockIndex)... ])`
