package hanto.studentjgasfm.common;

import hanto.common.HantoCoordinate;

public class Coordinate implements HantoCoordinate {
	private int x, y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

}
