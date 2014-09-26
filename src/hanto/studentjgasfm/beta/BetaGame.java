/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentjgasfm.beta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.BaseHantoGame;

/**
 * Second Hanto Variation
 */
public class BetaGame extends BaseHantoGame implements HantoGame {

	/**
	 * Constructor for BetaGame.
	 * 
	 * @param c
	 *            HantoPlayerColor
	 */
	public BetaGame(HantoPlayerColor c) {
		super(c);
		player2SparrowCount = 5;
		player1SparrowCount = 5;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result;
		
		if(from != null){
			throw new HantoException("Piece cannot be moved, only placed");
		}
		result = moveValidator(pieceType, from, to);
		
		if (player1SparrowCount == 0 && player2SparrowCount == 0 && player1ButterflyPlaced && player2ButterflyPlaced && result == MoveResult.OK) {
			result = MoveResult.DRAW;
		}

		return result;
	}
}
