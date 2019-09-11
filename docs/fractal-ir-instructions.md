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
Assigns the not of the last argument to the variable referenced by the first argument.

`BoolNot(Bool output, Bool input)`

### BoolAnd
Ands the last two arguments together and stores the result in the variable referenced by the first argument.

`BoolAnd(Bool result, Bool left, Bool right)`

### BoolOr
Ors the last two arguments together and stores the result in the variable referenced by the first argument.

`BoolOr(Bool result, Bool left, Bool right)`

### BoolIsEqual
Checks to see if the two last boolean arguments are equal then stores the result in the variable referenced by the 
first argument. This is the same as a boolean XNOR.

`BoolIsEqual(Bool result, Bool left, Bool right)`

## Int Instructions

### IntAdd
Adds the last two arguments as ints and stores the result in the variable referenced by the first argument.

`IntAdd(Int sum, Int leftAddend, Int rightAddend)`

### IntSubtract
Subtracts the last argument from the second to last argument and stores the result in the variable referenced by the 
first argument.

`IntSubtract(Int difference, Int minuend, Int subtrahend)`

### IntMultiply
Multiplies the last two arguments together and stores the result in the variable referenced by the first argument.

`IntMultiply(Int product, Int leftFactor, Int rightFactor)`

### IntDivide
Divides the second to last argument by the last argument and stores the result in the variable referenced by the 
first argument.

`IntDivide(Int quotient, Int dividend, Int divisor)`

### IntModulo
Finds the remainder after dividing the second to last argument by the last argument and stores the result in the 
variable referenced by the first argument.

`IntModulo(Int remainder, Int dividend, Int divisor)`

### IntPower
Finds the result of raising the second to last argument to the power of the last argument and stores that result in 
the variable referenced by the first argument.

`IntPower(Int result, Int base, Int exponent)`

### IntNot
Finds the bitwise not of the last argument ans stores it in the variable referenced by the first argument.

`IntNot(Int output, Int input)`

### IntAnd
Bitwise ands the last two arguments together and stores the result in the variable referenced by the first argument.

`IntAnd(Int result, Int left, Int right)`

### IntOr
Bitwise ors the last two arguments together and stores the result in the variable referenced by the first argument.

`IntOr(Int result, Int left, Int right)`

### IntXor
Bitwise xors the last two arguments together and stores the result in the variable referenced by the first argument.

`IntXor(Int result, Int left, Int right)`

### IntIsEqual
Checks to see if the last two arguments have the same integer value and stores the resulting boolean in the variable 
referenced by the first argument.

`IntIsEqual(Bool result, Int left, Int right)`

### IntIsGreater
Checks to see if the second to last argument is greater than the last arguments and stores the resulting boolean in 
the variable referenced by the first argument.

`IntIsGreater(Bool result, Int subject, Int basis)`

### IntIsGreaterOrEqual
Checks to see if the second to last argument is greater than or equal to the last argument and stores the resulting 
boolean in the variable referenced by the first argument.

`IntIsGreaterOrEqual(Bool result, Int subject, Int basis)`

## Real Instructions

### RealAdd
Adds the last two arguments together and stores the result in the variable referenced by the first argument.

`RealAdd(Real sum, Real leftAddend, Real rightAddend)`

### RealSubtract
Subtracts the last argument from the second to last argument and stores the result in the variable referenced by the 
first argument.

`RealSubtract(Real difference, Real minuend, Real subtrahend)`

### RealMultiply
Multiplies the last two arguments together and stores the result in the variable referenced by the first argument.

`RealMultiply(Real product, Real leftFactor, Real rightFactor)`

### RealDivide
Divides the second to last argument by the last argument and stores the result in the variable referenced by the 
first argument.

`RealDivide(Real quotient, Real dividend, Real divisor)`

### RealPower
Raises the second to last argument to the power of the last argument and stores the result in the variable referenced
by the first argument.

`RealPower(Real result, Real base, Real exponent)`

### RealIsEqual
Checks to see if the last two arguments have the same real value and stores the resulting boolean in the variable 
referenced by the first argument.

`RealIsEqual(Bool result, Real left, Real right)`

### RealIsGreater
Checks to see if the second to last argument has a greater real value than the last argument and stores the resulting
boolean in the variable referenced by the first argument.

`RealIsGreater(Bool result, Real subject, Real basis)`

### RealIsGreaterOrEqual
Checks to see if the second to last argument has a greater real value than or equal real value to the last argument 
and stores the resulting boolean value in the variable referenced by the first argument.

`RealIsGreaterOrEqual(Bool result, Real subject, Real basis)`

### RealComposeComplex
Composes a complex number with the second to last argument as its real component and the last argument as its 
imaginary component and stores it in the variable referenced by the first argument.

`RealComposeComplex(Complex complex, Real real, Real imaginary)`

## Complex Instructions

### ComplexAdd
Adds the last two arguments together and stores the result in the variable referenced by the first argument.

`ComplexAdd(Complex sum, Complex leftAddend, Complex rightAddend)`

### ComplexSubtract
Subtracts the last argument from the second to last argument and stores the result in the variable referenced by the 
first argument.

`ComplexSubtract(Complex difference, Complex minuend, Complex subtrahend)`

### ComplexMultiply
Multiplies the last two arguments together and stores the result in the variable referenced by the first argument.

`ComplexMultiply(Complex product, Complex leftFactor, Complex rightFactor)`

### ComplexDivide
Divides the second to last argument by the last argument and stores the result in the variable referenced by the 
first argument.

`ComplexDivide(Complex quotient, Complex dividend, Complex divisor)`

### ComplexPower
Raises the second to last argument to the power of the last argument and stores the result in the variable referenced
by the first argument.

`ComplexPower(Complex result, Complex base, Complex exponent)`

### ComplexGetReal
Gets the real component of the last argument and stores it in the variable referenced by the first argument.

`ComplexGetReal(Real real, Complex complex)`

### ComplexGetImaginary
Gets the imaginary component of the last argument and stores it in the variable referenced by the first argument.

`ComplexGetImaginary(Real imaginary, Complex complex)`

### ComplexModulo
Computes the complex modulo of the last argument and stores it in the variable referenced by the first argument. A 
complex number's modulus is that complex number's absolute distance from the origin as computed by
sqrt(&lt;real-component&gt;^2 + &lt;imaginary-component&gt;^2).

`ComplexModulo(Real modulus, Complex complex)`

### ComplexIsEqual
Checks to see if the two arguments have the same real and imaginary values.

`ComplexIsEqual(Complex left, Complex right)`

## Function Instructions

### FunctionCreate
Constructs a function context variable using the given function name and context variable values.

`FunctionCreate(functionName, [ ** contextVariables ])`

### FunctionCall
Calls the function represented by the function context that is the second argument with arguments in the list that is 
the third argument and stores the function's return value in the variable referenced by the first argument. The 
variable that the return value is stored in must be of the same type as the function's return value and each of the 
arguments in the specified argument list must be the same types as the arguments in the function.

`FunctionCall(* result, Function(*, [ ** ]) function, [ ** arguments ])`

### FunctionIsEqual
Checks to see if the last two function arguments reference the same function and hold the same context variable 
values and stores the result in the variable referenced by the first argument.

`FunctionIsEqual(Bool result, Function(*, [ ** ]) left, Function(*, [ ** ]) right)`

## Pointer Instructions

### PointerAllocate
Allocates data for a pointer on the language-backed heap and stores its handle in the variable referenced by the only 
argument.

`PointerAllocate(Pointer(*) pointer)`

### PointerFree
Frees the data pointed to by the handle in the only argument. This also sets the pointer's handle value to NullPointer.

`PointerFree(Pointer(*) pointer)`

### PointerGet
Retrieves the data pointed to by the handle in the second argument and stores it in the variable referenced by the 
first argument.

`PointerGet(* data, Pointer(*) pointer)`

### PointerSet
Sets the value of the data pointed to by the handle in the first argument to the data in the second argument.

`PointerSet(Pointer(*) pointer, * data)`

### PointerIsEqual
Checks to see if the last two pointer arguments hold handles to the same data and stores the result in the variable 
referenced by the first argument.

`PointerIsEqual(Bool result, Pointer(*) left, Pointer(*) right)`

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
