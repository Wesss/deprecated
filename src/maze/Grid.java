package maze;


public class Grid {
	private int x;
	private int y;
	
	/**
	 * Creates a coordinate point at the origin
	 */
	public Grid() {
		x = 0;
		y = 0;
	}
	
	/**
	 * Creates a cordinate point at the specified coordinates
	 * @param x
	 * @param y
	 */
	public Grid(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**************** Getter methods **********************/
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	/**************** Object Definition Methods *********************/
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Grid))
			return false;
		
		Grid othr = ((Grid)other);
		return (othr.x == x && othr.y == y);
	}
	
	@Override
	public int hashCode() {
		return x + (101 * y);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
