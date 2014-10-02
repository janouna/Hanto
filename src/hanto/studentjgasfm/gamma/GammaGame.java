/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentjgasfm.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.BaseHantoGame;
import hanto.studentjgasfm.common.Coordinate;


public class GammaGame extends BaseHantoGame {

	public GammaGame(HantoPlayerColor c) {
		super(c);
		player2SparrowCount = 5;
		player1SparrowCount = 5;
		turnLimit = 20;
		moveLimit = turnLimit * 2;
	}
	
	/**
	 * 
	 * This method takes in a piece, as well as a previous location for that piece
	 * (which is null if it's being placed for the first time), and the location that
	 * the piece is trying to be moved.
	 * It will then make sure that the move is valid and return the appropriate move result.
	 * 
	 * @param pieceType
	 * @param from -- The location that the piece is coming from (null if piece isn't moving between 
	 * 																two locations)
	 * @param to  -- The location that the piece is moving to
	 * @return -- Returns the result of the move, whether a player has won or not and if the move is valid.
	 * 				Throws exception if the move is not valid.
	 * @throws HantoException
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		MoveResult result;
		
		if(from != null){
			if(pieceList.get(new Coordinate(from)).getType() != pieceType){
				throw new HantoException("Piece type does not match the piece at the from location");
			}else{
				result = walk(pieceType, from, to);
			}
		}else{
			result = placePiece(pieceType, from, to);
		}

		return result;
	}
}
