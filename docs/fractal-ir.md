# Fractal IR
Fractal IR is an intermediate representation language designed to be shared between fractal generator modules and 
efficiently compiled into a variety of target languages and for a variety of systems. Some of these targets include: 
Java, C, LLVM IR, and SPIR-V bytecode. The language is also intended to be able to sent across networked systems so 
each system can accurately generate its own part of the same fractal.

## Structure
A Fractal IR program is composed of a global variable table and a function table. All variable declarations consist 
of a name, a type, and a set of attributes. All functions consist of a name, a return value, a table of context 
variables, a table of arguments, a table of local variables, and a list of instructions.

#### Program Structure:
* Global variables
* Functions

#### Variable Declaration Structure:
* Variable Name (same as its key in tables)
* Variable Type
* Variable Attribute Set

#### Function Structure:
* Function Name (same as its key in the function table)
* Function Return Type (can be any valid type as well as Void)
* Function Context Variable Table
* Function Argument Table
* Function Local Variable Table
* Instruction List