package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.collect.ImmutableList;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public interface ValueType extends Comparable<ValueType>, Serializable {
	ValueType BOOL = new BoolType();
	ValueType COMPLEX = new ComplexType();

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

	static boolean isBool(ValueType type) {
		return type == BOOL;
	}

	static boolean isComplex(ValueType type) {
		return type == COMPLEX;
	}

	static boolean isFunction(ValueType type) {
		return type instanceof FunctionType;
	}

	static FunctionType toFunction(ValueType type) {
		return (FunctionType) type;
	}

	int compareTo(ValueType type);

	boolean equals(Object other);

	int hashCode();

	String name();

	String toString();

	class BoolType implements ValueType {
		private BoolType() {
		}

		@Override
		public int compareTo(ValueType type) {
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

	class ComplexType implements ValueType {
		private ComplexType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (type == BOOL)
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
			if (type == BOOL || type == COMPLEX)
				return 1;
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
}
