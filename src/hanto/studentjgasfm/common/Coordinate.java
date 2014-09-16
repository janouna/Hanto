/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentjgasfm.common;

import hanto.common.HantoCoordinate;

/**
 * Implements HantoCoordinate and changes Hashcode and Equals methods
 */
public class Coordinate implements HantoCoordinate {
	private int x, y;

	/**
	 * Constructor for Coordinate.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor for Coordinate.
	 * 
	 * @param c
	 *            HantoCoordinate
	 */
	public Coordinate(HantoCoordinate c) {
		x = c.getX();
		y = c.getY();
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
		boolean equals = false;

		if (this == obj) {
			equals = true;
		} else if (obj != null && getClass() == obj.getClass()) {
			Coordinate other = (Coordinate) obj;
			if (x == other.x && y == other.y) {
				equals = true;
			}
		}
		return equals;
	}

}
