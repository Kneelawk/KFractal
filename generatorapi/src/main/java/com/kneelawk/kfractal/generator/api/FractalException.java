package com.kneelawk.kfractal.generator.api;

public class FractalException extends Exception {
	public FractalException() {
	}

	public FractalException(String message) {
		super(message);
	}

	public FractalException(String message, Throwable cause) {
		super(message, cause);
	}

	public FractalException(Throwable cause) {
		super(cause);
	}
}
