package org.util.math;

/**
 * Rational objects represent rational numbers. These are numbers
 * that can be represented as fractions using only whole numbers.
 * 
 * This class cannot represent rational numbers whose numerator or
 * denominator exceed the values representable by the int type.
 */
public class Rational implements Comparable<Rational> {
  
	/**
	 * A Rational's number value is represented by:
	 * 	numerator / denominator
	 * 
	 * Representation Invariant:
	 *  denominator > 0;
	 *  gcd(numerator, denominator) == 1
	 * 
	 * In English:
	 *  The denominator can never be 0
	 * 	Negative Rationals are represented by a negative numerator
	 *  Rationals will always be in their reduced form
	 */
	
	private final int num;
	private final int den;
	
	//////////////////////////////////////////////////
	// Basics
	//////////////////////////////////////////////////
	
	public static final Rational ZERO = new Rational(0, 1);
	
	/**
	 * Creates a new rational number
	 * @param n the numerator of the rational number
	 * @param d the denominator of the rational number
	 * 		<UL><LI> d must not be 0. </UL>
	 */
	public Rational(int n, int d){
		if (d == 0)
			throw new IllegalArgumentException();
		
		if (d < 0) {
			n *= -1;
			d *= -1;
		}
		
		int gcd = MyMath.gcd(n, d);
		num = n / gcd;
		den = d / gcd;
	}
	  
	/**
	 * @return the numerator of this rational number
	 */
	public int numer() {
	  return num;
	}
  
	/**
	 * @return the denominator of this rational number
	 */
	public int denom() {
		return den;
	}
	
	/**
	 * Checks if an object is equal to this
	 * 
	 * @param obj the other object
	 * @return <UL>
	 * 		<LI> true if this and obj are both Rational and represent the same numeric value
	 * 		<LI> false otherwise
	 * 	</UL>
	 */
	@Override
	public boolean equals(Object obj) {
		Rational other = null;
		if (obj instanceof Rational) {
			other = (Rational) obj;
		} else {
			return false;
		}
		return (this.num == other.num) && (this.den == other.den);
	}
	
	@Override
	public int hashCode() {
		//65536 = 2^13
		return num  + (65536 * den);
	}
	
	@Override
	public String toString() {
		return "(" + num + " / " + den + ")";
	}
	
	/**
	 * Compares two RatNums.
	 *  
	 * @param other The RatNum to be compared
	 * @requires other != null
	 * @return <UL>
	 * 		<LI>a negative number if this < rn,
	 *   	<LI>0 if this = rn,
	 *   	<LI>a positive number if this > rn.
	 * 	</UL>
	 */
	public int compareTo(Rational other) {
		Rational diff = this.sub(other);
		return diff.num;
	}
	
	//////////////////////////////////////////////////
	// Arithmetic
	//////////////////////////////////////////////////
	
	/**
	 * Checks for a positive value
	 * 
	 * @return true if this > 0, false otherwise
	 */
	public boolean isPos() {
		return (num > 0);
	}
	
	/**
	 * Checks for a zero value
	 * 
	 * @return true if this == 0, false otherwise
	 */
	public boolean isZero() {
		return (num == 0);
	}
	
	/** 
	 * Returns the additive inverse of this RatNum.
	 * 
	 * @return a Rational equal to (0 - this).
	 */
	public Rational negate() {
		return new Rational(- this.num, this.den);
	}
	
	/** 
	 * Addition operation
	 * 
	 * @param arg The other value to be added
	 *	 	<UL><LI> arg cannot be null </UL>
	 * @return a RatNum equal to (this + arg)
	*/
	public Rational add(Rational arg) {
		// a/b + x/y = ay/by + bx/by = (ay + bx)/by
		return new Rational((this.num * arg.den) + (arg.num * this.den),
				this.den * arg.den);
	}
	
	/**
	 * Subtraction operation.
	 * 
	 * @param arg The value to be subtracted.
	 * 		<UL><LI> arg cannot be null </UL>
	 * @return a RatNum equal to (this - arg).
	 */
	public Rational sub(Rational arg) {
		// a/b - x/y = a/b + -x/y
		return this.add(arg.negate());
	}
	
	/**
	 * Multiplication operation.
	 * 
	 * @param arg The other value to be multiplied.
	 * 		<UL><LI> arg cannot be null </UL>
	 * @return a RatNum equal to (this * arg).
	*/
	public Rational mul(Rational arg) {
	// (a/b) * (x/y) = ax/by
	return new Rational(this.num * arg.num, this.den * arg.den);
	}
	
	/**
	 * Division operation.
	 * 
	 * @param arg The divisor
	 * 		<UL><LI> arg cannot be null </UL>
	 * @return a RatNum equal to (this / arg)
	 */
	public Rational div(Rational arg) {
		// (a/b) / (x/y) = ay/bx
		return new Rational(this.num * arg.den, this.den * arg.num);
	}
	
	//////////////////////////////////////////////////
	// Conversions
	//////////////////////////////////////////////////
	
	/**
	 * Rational to Integer conversion
	 * 
	 * @returns an integer rounded down from this Rational's numeric value
	 */
	public int toInt() {
		return this.num / this.den;
	}
	
	/**
	 * Rational to Integer conversion
	 * 
	 * @returns a double representation of this Rational's numeric value (currently unsure of
	 * 		exact way doubles are computed).
	 * 	
	 */
	public double toDouble() {
		return ((double)this.num / (double)this.den);
	}
	
	/**
	 * Integer to Rational conversion
	 * 
	 * @param val the value to convert
	 * @return a Rational whose numeric value is equal to val
	 */
	public static Rational convertInt(int val) {
		return new Rational(val, 1);
	}
	
	/**
	 * Double to Rational conversion
	 * 
	 * @param val the value to convert
	 * @return a Rational whose numeric value is within [val - 1/(2^10), val + 1/(2^10)],
	 * 		<UL><LI> 1/(2^20) = 0.0009765625 </UL>
	 */
	public static Rational convertDouble(double val) {
		return convertDouble(val, 0.0009765625);
	}
	
	/**
	 * Double to Rational conversion within given error bounds
	 * 
	 * @param val the value to convert
	 * @param error the acceptable error margin for the conversion
	 * 		<UL><LI> error must be non-negative and non-zero </UL>
	 * @return a Rational whose numeric value is within [x - error, x + error]
	 */
	public static Rational convertDouble(double val, double error) {
		if (error <= 0)
			throw new IllegalArgumentException("Error value of " + error + " given");
		
		int h1 = 1;
		int h2 = 0;
		int k1 = 0;
		int k2 = 1;
		double b = val;
		int counter = 0;
		
		do {
			int a = (int)Math.floor(b);
			int aux = h1;
			h1 = a * h1 + h2;
			h2 = aux;
			
			aux = k1;
			k1 = a * k1 + k2;
			k2 = aux;
			b = 1 / (b - a);
			
			counter++;
			if (counter == 50) {
				throw new RuntimeException("Fraction approximator forcestop reached for"
						+ " value " + val + " with error " + error);
			}
		} while (Math.abs(val - ((1.0 * h1) / (1.0 * k1))) > error);
		
		return new Rational(h1, k1);
	}
}
