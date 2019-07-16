package com.kneelawk.kfractal.generator.api.ir;

import java.io.Serializable;

public interface ValueType extends Comparable<ValueType>, Serializable {
    int compareTo(ValueType type);

    boolean equals(Object other);

    boolean isAssignableFrom(ValueType other);

    int hashCode();

    String name();

    String toString();
}
