package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.collect.ImmutableList;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public interface ValueType extends Comparable<ValueType>, Serializable {
	/* Base type constants */

	ValueType VOID = new VoidType();
	ValueType BOOL = new BoolType();
	ValueType INT = new IntType();
	ValueType REAL = new RealType();
	ValueType COMPLEX = new ComplexType();

	/* Derived type constructors */

	static ValueType FUNCTION(ValueType returnType, List<ValueType> argumentTypes) {
		if (returnType == null)
			throw new NullPointerException();
		return new FunctionType(returnType, ImmutableList.copyOf(argumentTypes));
	}

	static ValueType FUNCTION(ValueType returnType, ValueType... argumentTypes) {
		if (returnType == null)
			throw new NullPointerException();
		return new FunctionType(returnType, ImmutableList.copyOf(argumentTypes));
	}

	static ValueType POINTER(ValueType pointerType) {
		if (pointerType == null)
			throw new NullPointerException();
		return new PointerType(pointerType);
	}

	/* Type check utilities */

	static boolean isVoid(ValueType type) {
		return type == VOID;
	}

	static boolean isBool(ValueType type) {
		return type == BOOL;
	}

	static boolean isInt(ValueType type) {
		return type == INT;
	}

	static boolean isReal(ValueType type) {
		return type == REAL;
	}

	static boolean isComplex(ValueType type) {
		return type == COMPLEX;
	}

	static boolean isFunction(ValueType type) {
		return type instanceof FunctionType;
	}

	static boolean isPointer(ValueType type) {
		return type instanceof PointerType;
	}

	/* Type converter utilities */

	static FunctionType toFunction(ValueType type) {
		return (FunctionType) type;
	}

	static PointerType toPointer(ValueType type) {
		return (PointerType) type;
	}

	/* Abstract functions */

	int compareTo(ValueType type);

	boolean equals(Object other);

	int hashCode();

	String name();

	String toString();

	/* ValueType classes */

	class VoidType implements ValueType {
		private VoidType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (type == VOID)
				return 0;
			return -1;
		}

		@Override
		public String name() {
			return "VOID";
		}

		@Override
		public String toString() {
			return name();
		}
	}

	class BoolType implements ValueType {
		private BoolType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (type == VOID)
				return 1;
			if (type == BOOL)
				return 0;
			return -1;
		}

		@Override
		public String name() {
			return "BOOL";
		}

		@Override
		public String toString() {
			return name();
		}
	}

	class IntType implements ValueType {
		private IntType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (type == VOID || type == BOOL)
				return 1;
			if (type == INT)
				return 0;
			return -1;
		}

		@Override
		public String name() {
			return "INT";
		}

		@Override
		public String toString() {
			return name();
		}
	}

	class RealType implements ValueType {
		private RealType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (type == VOID || type == BOOL || type == INT)
				return 1;
			if (type == REAL)
				return 0;
			return -1;
		}

		@Override
		public String name() {
			return "REAL";
		}

		@Override
		public String toString() {
			return name();
		}
	}

	class ComplexType implements ValueType {
		private ComplexType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (type == VOID || type == BOOL || type == INT || type == REAL)
				return 1;
			if (type == COMPLEX)
				return 0;
			return -1;
		}

		@Override
		public String name() {
			return "COMPLEX";
		}

		@Override
		public String toString() {
			return name();
		}
	}

	class FunctionType implements ValueType {
		private ValueType returnType;
		private List<ValueType> argumentTypes;

		private FunctionType(ValueType returnType,
							 List<ValueType> argumentTypes) {
			this.returnType = returnType;
			this.argumentTypes = argumentTypes;
		}

		public ValueType getReturnType() {
			return returnType;
		}

		public List<ValueType> getArgumentTypes() {
			return argumentTypes;
		}

		@Override
		public int compareTo(ValueType type) {
			if (type == VOID || type == BOOL || type == INT || type == REAL || type == COMPLEX)
				return 1;
			if (type instanceof FunctionType) {
				FunctionType other = (FunctionType) type;
				int result = returnType.compareTo(other.returnType);
				if (result != 0)
					return result;
				int mySize = argumentTypes.size();
				int otherSize = other.argumentTypes.size();
				int size = Math.max(mySize, otherSize);
				for (int i = 0; i < size; i++) {
					if (i >= mySize) {
						return -1;
					} else if (i >= otherSize) {
						return 1;
					} else {
						result = argumentTypes.get(i).compareTo(other.argumentTypes.get(i));
						if (result != 0)
							return result;
					}
				}
				return 0;
			}
			return -1;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			FunctionType that = (FunctionType) o;
			return returnType.equals(that.returnType) &&
					argumentTypes.equals(that.argumentTypes);
		}

		@Override
		public int hashCode() {
			return Objects.hash(returnType, argumentTypes);
		}

		@Override
		public String name() {
			return "FUNCTION(" + returnType + ", " + argumentTypes.toString() + ')';
		}

		@Override
		public String toString() {
			return name();
		}
	}

	class PointerType implements ValueType {
		private ValueType pointerType;

		private PointerType(ValueType pointerType) {
			this.pointerType = pointerType;
		}

		public ValueType getPointerType() {
			return pointerType;
		}

		@Override
		public int compareTo(ValueType type) {
			if (type == VOID || type == BOOL || type == INT || type == REAL || type == COMPLEX || type instanceof FunctionType)
				return 1;
			if (type instanceof PointerType) {
				PointerType other = (PointerType) type;
				return pointerType.compareTo(other.pointerType);
			}
			return -1;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			PointerType that = (PointerType) o;
			return pointerType.equals(that.pointerType);
		}

		@Override
		public int hashCode() {
			return Objects.hash(pointerType);
		}

		@Override
		public String name() {
			return "POINTER(" + pointerType + ')';
		}

		@Override
		public String toString() {
			return name();
		}
	}
}
