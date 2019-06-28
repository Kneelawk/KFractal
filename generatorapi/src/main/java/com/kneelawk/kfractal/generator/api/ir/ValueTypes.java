package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public final class ValueTypes {
	private ValueTypes() {
	}

	/* Instance Caches */

	private static final LoadingCache<ValueType, PointerType> pointerCache =
			CacheBuilder.newBuilder().weakValues().build(
					new CacheLoader<>() {
						@Override
						public PointerType load(ValueType key) {
							return new PointerType(key);
						}
					});
	private static final LoadingCache<ImmutablePair<ValueType, ImmutableList<ValueType>>, FunctionType> functionCache =
			CacheBuilder.newBuilder().weakValues().build(
					new CacheLoader<>() {
						@Override
						public FunctionType load(ImmutablePair<ValueType, ImmutableList<ValueType>> key) {
							return new FunctionType(key.left, key.right);
						}
					});

	/* Base type constants */

	public static final ValueType VOID = new ValueTypes.VoidType();
	public static final ValueType BOOL = new ValueTypes.BoolType();
	public static final ValueType INT = new ValueTypes.IntType();
	public static final ValueType REAL = new ValueTypes.RealType();
	public static final ValueType COMPLEX = new ValueTypes.ComplexType();
	public static final ValueType NULL_FUNCTION = new ValueTypes.FunctionType(VOID, ImmutableList.of(), true);
	public static final ValueType NULL_POINTER = new ValueTypes.PointerType(VOID, true);

	/* Derived type constructors */

	public static ValueType FUNCTION(ValueType returnType, List<ValueType> argumentTypes) {
		if (returnType == null)
			throw new NullPointerException();
		try {
			return functionCache.get(ImmutablePair.of(returnType, ImmutableList.copyOf(argumentTypes)));
		} catch (ExecutionException e) {
			// this should never happen
			throw new RuntimeException(e);
		}
	}

	public static ValueType FUNCTION(ValueType returnType, ValueType... argumentTypes) {
		if (returnType == null)
			throw new NullPointerException();
		try {
			return functionCache.get(ImmutablePair.of(returnType, ImmutableList.copyOf(argumentTypes)));
		} catch (ExecutionException e) {
			// this should never happen
			throw new RuntimeException(e);
		}
	}

	public static ValueType POINTER(ValueType pointerType) {
		if (pointerType == null)
			throw new NullPointerException();
		try {
			return pointerCache.get(pointerType);
		} catch (ExecutionException e) {
			// this should never happen
			throw new RuntimeException(e);
		}
	}

	/* Type check utilities */

	public static boolean isVoid(ValueType type) {
		return type == VOID;
	}

	public static boolean isBool(ValueType type) {
		return type == BOOL;
	}

	public static boolean isInt(ValueType type) {
		return type == INT;
	}

	public static boolean isReal(ValueType type) {
		return type == REAL;
	}

	public static boolean isComplex(ValueType type) {
		return type == COMPLEX;
	}

	public static boolean isFunction(ValueType type) {
		return type instanceof ValueTypes.FunctionType;
	}

	public static boolean isPointer(ValueType type) {
		return type instanceof ValueTypes.PointerType;
	}

	/* Type converter utilities */

	public static ValueTypes.FunctionType toFunction(ValueType type) {
		return (ValueTypes.FunctionType) type;
	}

	public static ValueTypes.PointerType toPointer(ValueType type) {
		return (ValueTypes.PointerType) type;
	}

	/* ValueType classes */

	public static final class VoidType implements ValueType {
		private VoidType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (isVoid(type))
				return 0;
			return -1;
		}

		@Override
		public boolean isAssignableFrom(ValueType other) {
			return true;
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

	public static final class BoolType implements ValueType {
		private BoolType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (isVoid(type))
				return 1;
			if (isBool(type))
				return 0;
			return -1;
		}

		@Override
		public boolean isAssignableFrom(ValueType other) {
			return other == BOOL;
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

	public static final class IntType implements ValueType {
		private IntType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (isVoid(type) || isBool(type))
				return 1;
			if (isInt(type))
				return 0;
			return -1;
		}

		@Override
		public boolean isAssignableFrom(ValueType other) {
			return other == INT;
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

	public static final class RealType implements ValueType {
		private RealType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (isVoid(type) || isBool(type) || isInt(type))
				return 1;
			if (isReal(type))
				return 0;
			return -1;
		}

		@Override
		public boolean isAssignableFrom(ValueType other) {
			return other == REAL;
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

	public static final class ComplexType implements ValueType {
		private ComplexType() {
		}

		@Override
		public int compareTo(ValueType type) {
			if (isVoid(type) || isBool(type) || isInt(type) || isReal(type))
				return 1;
			if (isComplex(type))
				return 0;
			return -1;
		}

		@Override
		public boolean isAssignableFrom(ValueType other) {
			return other == COMPLEX;
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

	public static final class FunctionType implements ValueType {
		private ValueType returnType;
		private List<ValueType> argumentTypes;
		private boolean nullFunction;

		private FunctionType(ValueType returnType,
							 List<ValueType> argumentTypes) {
			this.returnType = returnType;
			this.argumentTypes = argumentTypes;
		}

		private FunctionType(ValueType returnType,
							 List<ValueType> argumentTypes, boolean nullFunction) {
			this.returnType = returnType;
			this.argumentTypes = argumentTypes;
			this.nullFunction = nullFunction;
		}

		public ValueType getReturnType() {
			return returnType;
		}

		public List<ValueType> getArgumentTypes() {
			return argumentTypes;
		}

		public boolean isNullFunction() {
			return nullFunction;
		}

		@Override
		public int compareTo(ValueType type) {
			if (isVoid(type) || isBool(type) || isInt(type) || isReal(type) || isComplex(type))
				return 1;
			if (isFunction(type)) {
				FunctionType other = (FunctionType) type;
				// compare null-ness
				if (nullFunction)
					return other.nullFunction ? 0 : 1;

				// compare return types
				int result = returnType.compareTo(other.returnType);
				if (result != 0)
					return result;

				// compare argument types
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
			return nullFunction == that.nullFunction &&
					returnType.equals(that.returnType) &&
					argumentTypes.equals(that.argumentTypes);
		}

		@Override
		public boolean isAssignableFrom(ValueType other) {
			if (this == other) return true;
			if (other == null || getClass() != other.getClass())
				return false;
			FunctionType that = (FunctionType) other;
			// everything is assignable from the null function
			if (that.nullFunction)
				return true;
			// the null function is assignable from nothing
			if (nullFunction)
				return false;
			if (!returnType.isAssignableFrom(that.returnType))
				return false;
			if (argumentTypes.size() != that.argumentTypes.size())
				return false;
			int size = argumentTypes.size();
			for (int i = 0; i < size; i++) {
				if (!that.argumentTypes.get(i).isAssignableFrom(argumentTypes.get(i)))
					return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			return Objects.hash(returnType, argumentTypes, nullFunction);
		}

		@Override
		public String name() {
			if (nullFunction)
				return "NULL_FUNCTION";
			return "FUNCTION(" + returnType + ", " + argumentTypes.toString() + ')';
		}

		@Override
		public String toString() {
			return name();
		}
	}

	public static final class PointerType implements ValueType {
		private ValueType pointerType;
		private boolean nullPointer;

		private PointerType(ValueType pointerType) {
			this.pointerType = pointerType;
		}

		public PointerType(ValueType pointerType, boolean nullPointer) {
			this.pointerType = pointerType;
			this.nullPointer = nullPointer;
		}

		public ValueType getPointerType() {
			return pointerType;
		}

		public boolean isNullPointer() {
			return nullPointer;
		}

		@Override
		public int compareTo(ValueType type) {
			if (isVoid(type) || isBool(type) || isInt(type) || isReal(type) || isComplex(type) || isFunction(type))
				return 1;
			if (isPointer(type)) {
				PointerType other = (PointerType) type;
				if (nullPointer)
					return other.nullPointer ? 0 : 1;
				return pointerType.compareTo(other.pointerType);
			}
			return -1;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			PointerType that = (PointerType) o;
			return nullPointer == that.nullPointer &&
					pointerType.equals(that.pointerType);
		}

		@Override
		public boolean isAssignableFrom(ValueType other) {
			if (this == other)
				return true;
			if (other == null || getClass() != other.getClass())
				return false;
			PointerType that = (PointerType) other;
			// everything is assignable from the null pointer
			if (that.nullPointer)
				return true;
			// the null pointer is assignable from nothing
			if (nullPointer)
				return false;
			return pointerType.isAssignableFrom(that.pointerType);
		}

		@Override
		public int hashCode() {
			return Objects.hash(pointerType, nullPointer);
		}

		@Override
		public String name() {
			if (nullPointer)
				return "NULL_POINTER";
			return "POINTER(" + pointerType + ')';
		}

		@Override
		public String toString() {
			return name();
		}
	}
}
