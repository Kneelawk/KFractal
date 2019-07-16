package com.kneelawk.kfractal.util;

public class StringUtils {
	public static final String INDENT_STRING = "  ";

	public static void indent(StringBuilder builder, int indent) {
		// multiple StringBuilder append operations should be faster
		//noinspection StringRepeatCanBeUsed
		for (int i = 0; i < indent; i++) {
			builder.append(INDENT_STRING);
		}
	}
}
