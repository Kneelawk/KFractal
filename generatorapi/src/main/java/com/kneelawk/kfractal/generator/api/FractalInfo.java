package com.kneelawk.kfractal.generator.api;

import com.kneelawk.kfractal.generator.api.ir.Program;

public class FractalInfo {
	private int imageWidth;
	private int imageHeight;
	private double planeWidth;
	private double planeHeight;
	private double planeStartX;
	private double planeStartY;
	private boolean mandelbrot;
	private int iterations;
	private double cReal;
	private double cImaginary;
	private Program generatorFunction;

	public FractalInfo(int imageWidth, int imageHeight, double planeWidth, double planeHeight, double planeStartX,
					   double planeStartY, boolean mandelbrot, int iterations, double cReal, double cImaginary,
					   Program generatorFunction) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.planeWidth = planeWidth;
		this.planeHeight = planeHeight;
		this.planeStartX = planeStartX;
		this.planeStartY = planeStartY;
		this.mandelbrot = mandelbrot;
		this.iterations = iterations;
		this.cReal = cReal;
		this.cImaginary = cImaginary;
		this.generatorFunction = generatorFunction;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public double getPlaneWidth() {
		return planeWidth;
	}

	public double getPlaneHeight() {
		return planeHeight;
	}

	public double getPlaneStartX() {
		return planeStartX;
	}

	public double getPlaneStartY() {
		return planeStartY;
	}

	public boolean isMandelbrot() {
		return mandelbrot;
	}

	public int getIterations() {
		return iterations;
	}

	public double getcReal() {
		return cReal;
	}

	public double getcImaginary() {
		return cImaginary;
	}

	public Program getGeneratorFunction() {
		return generatorFunction;
	}
}
