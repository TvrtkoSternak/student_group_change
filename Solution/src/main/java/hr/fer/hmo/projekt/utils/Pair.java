package hr.fer.hmo.projekt.utils;

public class Pair {
	private long var1;
	private long var2;
	
	public Pair() {
		
	}
	
	public Pair(long var1, long var2) {
		super();
		this.var1 = var1;
		this.var2 = var2;
	}
	
	/**
	 * @return the var1
	 */
	public long getVar1() {
		return var1;
	}
	/**
	 * @param var1 the var1 to set
	 */
	public void setVar1(long var1) {
		this.var1 = var1;
	}
	/**
	 * @return the var2
	 */
	public long getVar2() {
		return var2;
	}
	/**
	 * @param var2 the var2 to set
	 */
	public void setVar2(long var2) {
		this.var2 = var2;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (var1 ^ (var1 >>> 32));
		result = prime * result + (int) (var2 ^ (var2 >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pair))
			return false;
		Pair other = (Pair) obj;
		if (var1 != other.var1 && var1 != other.var2)
			return false;
		if (var2 != other.var2 && var2 != other.var1)
			return false;
		return true;
	}
	
}
