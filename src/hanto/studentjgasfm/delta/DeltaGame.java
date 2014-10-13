/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentjgasfm.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.BaseHantoGame;

/**
 * The fourth iteration of the Hanto game.
 */
public class DeltaGame extends BaseHantoGame {

	public DeltaGame(HantoPlayerColor c) {
		super(c);
		player2SparrowCount = 4;
		player1SparrowCount = 4;
		player2CrabCount = 4;
		player1CrabCount = 4;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		MoveResult result = null;
		
		if(pieceType != null && pieceType != HantoPieceType.BUTTERFLY && pieceType != HantoPieceType.CRAB && pieceType != HantoPieceType.SPARROW){
			throw new HantoException("Invalid Piece Type");
		}
		
		if(from != null){
			if (pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB){
				result = walk(pieceType, from, to);
			}else if (pieceType == HantoPieceType.SPARROW){
				result = fly(pieceType, from, to);
			}
		}else if(to != null){
			result = placePiece(pieceType, from, to);
		}else{
			result = resign();
		}

		return result;
	}
}
