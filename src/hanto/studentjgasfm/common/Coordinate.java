package hanto.studentjgasfm.common;

import hanto.common.HantoCoordinate;

public class Coordinate implements HantoCoordinate {
	private int x, y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Coordinate(HantoCoordinate c){
		this.x = c.getX();
		this.y = c.getY();
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
}
