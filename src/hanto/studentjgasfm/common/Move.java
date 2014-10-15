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
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;

/**
 * The Move class stores information about possible moves
 */
public class Move {
	private final HantoCoordinate to, from;
	private final HantoPieceType piece;
	private final MoveResult result;
	
	/**
	 * The Move object
	 * @param piece The type of the piece
	 * @param from The from location
	 * @param to The to location
	 * @param result The result of the movement
	 */
	public Move(HantoPieceType piece, HantoCoordinate from, HantoCoordinate to, MoveResult result){
		this.to = to;
		this.from = from;
		this.piece = piece;
		this.result = result;
	}
	
	public HantoCoordinate getFrom(){
		return from;
	}
	
	public HantoCoordinate getTo(){
		return to;
	}
	
	public HantoPieceType getType(){
		return piece;
	}
	
	public MoveResult getResult(){
		return result;
	}
}
