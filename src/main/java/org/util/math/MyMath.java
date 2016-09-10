package org.util.math;

/**
 *  The MyMath class contains static methods for computing various functions
 * 
 * @author Wesley Cox
 * @last_edited 11/11/15
 */
public class MyMath {
	
	/**
	 * Returns the greatest common denominator between the given integers
	 * 
	 * @param a first integer
	 * @param b second integer
	 * @return the largest positive integer that can divide both x and y into whole numbers
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
		return (int)Math.sqrt((x * x) + (y * y));
	}
}
