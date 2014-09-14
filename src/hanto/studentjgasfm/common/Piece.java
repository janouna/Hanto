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

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Implementation of HantoPiece
 */
public class Piece implements HantoPiece {
	private HantoPlayerColor color;
	private HantoPieceType type;
	
	/**
	 * Constructor for Piece.
	 * @param c HantoPlayerColor
	 * @param t HantoPieceType
	 */
	public Piece(HantoPlayerColor c, HantoPieceType t){
		color = c;
		type = t;
	}
	
	@Override
	public HantoPlayerColor getColor() {
		return color;
	}

	@Override
	public HantoPieceType getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
