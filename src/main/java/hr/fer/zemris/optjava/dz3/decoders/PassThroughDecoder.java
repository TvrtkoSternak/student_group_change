//package hr.fer.zemris.optjava.dz3.decoders;
//
//import hr.fer.zemris.optjava.dz3.IDecoder;
//import hr.fer.zemris.optjava.dz3.solutions.DoubleArraySolution;
//
//public class PassThroughDecoder implements IDecoder<DoubleArraySolution>{
//
//	public PassThroughDecoder() {
//	}
//
//	@Override
//	public double[] decode(DoubleArraySolution solution) {
//		return solution.getValues();
//	}
//
//	@Override
//	public void decode(DoubleArraySolution solution, double[] decoded) {
//		double[] values = solution.getValues();
//		
//		for (int i = 0; i < values.length; i++) {
//			decoded[i] = values[i];
//		}
//	}
//	
//}
