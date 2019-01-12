package hr.fer.zemris.optjava.dz3.schedules;

import hr.fer.zemris.optjava.dz3.ITempSchedule;

public class GeometricTempSchedule implements ITempSchedule {

	private double alpha;
	@SuppressWarnings("unused")
	private double tInitial;
	private double tCurrent;
	private int innerLimit;
	private int outerLimit;
	
	public GeometricTempSchedule(double alpha, double tInitial, int innerLimit, int outerLimit) {
		this.alpha = alpha;
		this.tInitial = tInitial;
		this.tCurrent = tInitial;
		this.innerLimit = innerLimit;
		this.outerLimit = outerLimit;
	}
	
	@Override
	public double getNextTemperature() {
		double temp = tCurrent;
		
		tCurrent *= alpha;
		
		return temp;
	}

	@Override
	public int getInnerLoopCounter() {
		return innerLimit;
	}

	@Override
	public int getOuterLoopCounter() {
		return outerLimit;
	}

	
}
