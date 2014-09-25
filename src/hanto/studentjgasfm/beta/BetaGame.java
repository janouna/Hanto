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
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.Piece;

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

		result = moveValidator(pieceType, from, to);

		return result;
	}

	private MoveResult moveValidator(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) throws HantoException {

		MoveResult result;
		
		if(from != null){
			throw new HantoException("Piece cannot be moved, only placed");
		}else if (getPieceAt(to) == null) {
			if (moveCount == 1 && to.getX() == 0 && to.getY() == 0) {
				pieceList.put(new Coordinate(to), new Piece(player1Color, pieceType));
				decrementPieceType(pieceType, player1Color, to);

				result = MoveResult.OK;
				moveCount++;
			} else if (moveCount == 7 && !player1ButterflyPlaced) {
				if (!pieceType.equals(HantoPieceType.BUTTERFLY)) {
					throw new HantoException(
							"Invalid Piece, Butterfly must be placed by fourth move");
				} else {
					pieceList.put(new Coordinate(to), new Piece(player1Color,
							pieceType));
					decrementPieceType(pieceType, player1Color, to);
					result = MoveResult.OK;
					moveCount++;
				}
			} else if (moveCount == 8 && !player2ButterflyPlaced) {
				if (!pieceType.equals(HantoPieceType.BUTTERFLY)) {
					throw new HantoException(
							"Invalid Piece, Butterfly must be placed by fourth move");
				} else {
					pieceList.put(new Coordinate(to), new Piece(player2Color,
							pieceType));
					decrementPieceType(pieceType, player2Color, to);
					result = MoveResult.OK;
					moveCount++;
				}
			} else if (hasAdjacentPiece(to)) {
				if (moveCount % 2 == 1
						&& ((pieceType.equals(HantoPieceType.SPARROW) && player1SparrowCount != 0) || (pieceType
								.equals(HantoPieceType.BUTTERFLY) && !player1ButterflyPlaced))) {
					pieceList.put(new Coordinate(to), new Piece(player1Color,
							pieceType));
					decrementPieceType(pieceType, player1Color, to);
					result = MoveResult.OK;
					moveCount++;
				} else if (moveCount % 2 == 0
						&& ((pieceType.equals(HantoPieceType.SPARROW) && player2SparrowCount != 0) || (pieceType
								.equals(HantoPieceType.BUTTERFLY) && !player2ButterflyPlaced))) {
					pieceList.put(new Coordinate(to), new Piece(player2Color,
							pieceType));
					decrementPieceType(pieceType, player2Color, to);
					result = MoveResult.OK;
					moveCount++;
				} else {
					throw new HantoException("No more of that piece remain");
				}
			} else {
				throw new HantoException("Invalid Position " + to.getX() + ","
						+ to.getY());
			}
		} else {
			throw new HantoException(
					"Invalid from position; pieces may only be placed, not moved");
		}

		if (player1SparrowCount == 0 && player2SparrowCount == 0
				&& player1ButterflyPlaced && player2ButterflyPlaced) {
			result = MoveResult.DRAW;
		}

		result = isButterflyTrapped(result);

		return result;
	}
	


}
