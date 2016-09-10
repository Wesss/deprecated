package org.util.math;

/**
 *  The Math class contains static methods for computing various functions
 */
public class Math {
	
	/**
	 * Returns the greatest common denominator between the given integers
	 * 
	 * @param a
	 * @param b
	 * @return the largest positive integer that can divide both a and b into whole numbers
	 */
	public static int gcd(int a, int b) {
		if (a == 0)
			return b;
		
		return gcd (b % a, a);
	}
	
	/**
	 * Returns the truncated length of the hypotenuse of a right triangle
	 * @param x the length of the triangle's base
	 * @param y the length of the triangle's height
	 * @return the truncated length of the triangle's hypotenuse 
	 */
	public static int hypotenuse(int x, int y) {
		return (int) java.lang.Math.sqrt((x * x) + (y * y));
	}

    /**
     *
     * @param x
     * @param modulus
     * @return the value, y, in the range [0, modulus) such that (y mod modulus == x mod modulus)
     */
	public static int posMod(int x, int modulus) {
        int result = x;

        while (result < 0) {
            result += modulus;
        }
        while (result >= modulus) {
            result -= modulus;
        }

        return result;
    }
}
