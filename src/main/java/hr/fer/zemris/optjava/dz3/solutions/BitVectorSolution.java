package hr.fer.zemris.optjava.dz3.solutions;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.SingleObjectiveSolution;

public class BitVectorSolution extends SingleObjectiveSolution {

	private byte[] bits;
	
	public BitVectorSolution(int size) {
		bits = new byte[size];
	}
	
	public BitVectorSolution(byte[] bits) {
		this.bits = bits;
	}
	
	public byte[] getBits() {
		return bits;
	}
	
	public BitVectorSolution newLikeThis() {
		return new BitVectorSolution(bits.length);
	}
	
	public BitVectorSolution duplicate() {
		return new BitVectorSolution(Arrays.copyOf(bits, bits.length));
	}
	
	public void randomize(Random rand) {
//		for (int i = 0; i < bits.length; i++) {
//			bits[i] = (byte) (rand.nextBoolean() ? 1 : 0);
//		}
		rand.nextBytes(bits);
	}
	
	public byte get(int index) {
		return bits[index];
	}
	
	public void set(int index, byte bit) {
		bits[index] = bit;
	}
	
	public void flip(int index) {
		bits[index] = (byte) ((bits[index] == 1) ? 0 : 1);
	}
	
}
