package hr.fer.zemris.optjava.dz3;

public class SingleObjectiveSolution implements Comparable<SingleObjectiveSolution> {

	private double fitness;
	private double value;
	
	public SingleObjectiveSolution() {
		
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public int compareTo(SingleObjectiveSolution other) {
		return Double.compare(this.getFitness(), other.getFitness());
	}
}
