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
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.Piece;

import java.util.HashMap;

import java.util.Map;

/**
 * Second Hanto Variation
 */
public class BetaGame implements HantoGame {
	private Map<Coordinate, HantoPiece> pieceList;
	private int moveCount, player1SparrowCount, player2SparrowCount;
	private boolean player1ButterflyPlaced, player2ButterflyPlaced;
	private HantoPlayerColor player1Color, player2Color;

	/**
	 * Constructor for BetaGame.
	 * 
	 * @param c
	 *            HantoPlayerColor
	 */
	public BetaGame(HantoPlayerColor c) {
		pieceList = new HashMap<Coordinate, HantoPiece>();
		moveCount = 1;
		player1Color = c;
		player2Color = (player1Color == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED
				: HantoPlayerColor.BLUE;
		player2SparrowCount = 5;
		player1SparrowCount = 5;
		player1ButterflyPlaced = false;
		player2ButterflyPlaced = false;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result;

		result = moveValidator(pieceType, from, to);

		return result;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return pieceList.get(new Coordinate(where));
	}

	@Override
	public String getPrintableBoard() {
		String board = "";
		for (Coordinate c : pieceList.keySet()) {
			HantoPiece p = pieceList.get(c);
			board += c.getX() + "," + c.getY() + " " + p.getColor() + " "
					+ p.getType() + "\n";
		}

		return board;
	}

	private MoveResult moveValidator(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) throws HantoException {

		MoveResult result;

		if (getPieceAt(to) == null) {
			if (moveCount == 1 && to.getX() == 0 && to.getY() == 0) {
<<<<<<< HEAD
				pieceList.put(new Coordinate(to), new Piece(player1Color, pieceType));
=======
				pieceList.put(new Coordinate(to), new Piece(player1Color,
						pieceType));
>>>>>>> d9bb07743759274b84712e199eaf54a6319e0ed5
				decrementPieceType(pieceType, player1Color);

				result = MoveResult.OK;
				moveCount++;
			} else if (moveCount == 7 && !player1ButterflyPlaced) {
				if (!pieceType.equals(HantoPieceType.BUTTERFLY)) {
					throw new HantoException(
							"Invalid Piece, Butterfly must be placed by fourth move");
				} else {
					pieceList.put(new Coordinate(to), new Piece(player1Color,
							pieceType));
					decrementPieceType(pieceType, player1Color);
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
					decrementPieceType(pieceType, player2Color);
					result = MoveResult.OK;
					moveCount++;
				}
			} else if (hasAdjacentPiece(to)) {
				if (moveCount % 2 == 1
						&& ((pieceType.equals(HantoPieceType.SPARROW) && player1SparrowCount != 0) || (pieceType
								.equals(HantoPieceType.BUTTERFLY) && !player1ButterflyPlaced))) {
					pieceList.put(new Coordinate(to), new Piece(player1Color,
							pieceType));
					decrementPieceType(pieceType, player1Color);
					result = MoveResult.OK;
					moveCount++;
				} else if (moveCount % 2 == 0
						&& ((pieceType.equals(HantoPieceType.SPARROW) && player2SparrowCount != 0) || (pieceType
								.equals(HantoPieceType.BUTTERFLY) && !player2ButterflyPlaced))) {
					pieceList.put(new Coordinate(to), new Piece(player2Color,
							pieceType));
					decrementPieceType(pieceType, player2Color);
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

		result = isButterflyTrapped(to, result);

		return result;
	}

	private void decrementPieceType(HantoPieceType pieceType,
			HantoPlayerColor playerColor) {
		if (pieceType.equals(HantoPieceType.BUTTERFLY)) {
			if (playerColor.equals(player1Color)) {
				player1ButterflyPlaced = true;
			} else {
				player2ButterflyPlaced = true;
			}
		} else if (pieceType.equals(HantoPieceType.SPARROW)) {
			if (playerColor.equals(player1Color)) {
				player1SparrowCount -= 1;
			} else {
				player2SparrowCount -= 1;
			}
		}

	}

	private boolean hasAdjacentPiece(HantoCoordinate to) {
		HantoCoordinate oneUp = new Coordinate(to.getX(), to.getY() + 1);
		HantoCoordinate oneDown = new Coordinate(to.getX(), to.getY() - 1);
		HantoCoordinate leftUp = new Coordinate(to.getX() - 1, to.getY() + 1);
		HantoCoordinate leftDown = new Coordinate(to.getX() - 1, to.getY());
		HantoCoordinate rightUp = new Coordinate(to.getX() + 1, to.getY());
		HantoCoordinate rightDown = new Coordinate(to.getX() + 1, to.getY() - 1);

		boolean hasPieceAdjacent = pieceList.containsKey(oneUp)
				|| pieceList.containsKey(oneDown)
				|| pieceList.containsKey(leftUp)
				|| pieceList.containsKey(leftDown)
				|| pieceList.containsKey(rightUp)
				|| pieceList.containsKey(rightDown);

		return hasPieceAdjacent;
	}
	
	private MoveResult isButterflyTrapped(HantoCoordinate to, MoveResult result){
		HantoCoordinate oneUp = new Coordinate(to.getX(), to.getY() + 1);
		HantoCoordinate oneDown = new Coordinate(to.getX(), to.getY() - 1);
		HantoCoordinate leftUp = new Coordinate(to.getX() - 1, to.getY() + 1);
		HantoCoordinate leftDown = new Coordinate(to.getX() - 1, to.getY());
		HantoCoordinate rightUp = new Coordinate(to.getX() + 1, to.getY());
		HantoCoordinate rightDown = new Coordinate(to.getX() + 1, to.getY() - 1);
		
		if(isPieceTrapped(oneUp) && pieceList.get(oneUp).getType().equals(HantoPieceType.BUTTERFLY)){
			result = colorOfWinner(oneUp);
		}else if(isPieceTrapped(oneUp) && pieceList.get(oneDown).getType().equals(HantoPieceType.BUTTERFLY)){
			result = colorOfWinner(oneUp);
		}
		else if(isPieceTrapped(leftUp) && pieceList.get(leftUp).getType().equals(HantoPieceType.BUTTERFLY)){
			result = colorOfWinner(leftUp);
		}
		else if(isPieceTrapped(leftDown) && pieceList.get(leftDown).getType().equals(HantoPieceType.BUTTERFLY)){
			result = colorOfWinner(leftDown);
		}
		else if(isPieceTrapped(rightUp) && pieceList.get(rightUp).getType().equals(HantoPieceType.BUTTERFLY)){
			result = colorOfWinner(rightUp);
		}else if(isPieceTrapped(rightDown) && pieceList.get(rightDown).getType().equals(HantoPieceType.BUTTERFLY)){
			result = colorOfWinner(rightDown);
		}
		
		return result;
	}
	
	private MoveResult colorOfWinner(HantoCoordinate pieceCoordinate) {
		if(pieceList.get(pieceCoordinate).getColor().equals(HantoPlayerColor.BLUE)){
			return MoveResult.RED_WINS;
		}else{
			return MoveResult.BLUE_WINS;
		}
	}

	private boolean isPieceTrapped(HantoCoordinate to) {
		HantoCoordinate oneUp = new Coordinate(to.getX(), to.getY() + 1);
		HantoCoordinate oneDown = new Coordinate(to.getX(), to.getY() - 1);
		HantoCoordinate leftUp = new Coordinate(to.getX() - 1, to.getY() + 1);
		HantoCoordinate leftDown = new Coordinate(to.getX() - 1, to.getY());
		HantoCoordinate rightUp = new Coordinate(to.getX() + 1, to.getY());
		HantoCoordinate rightDown = new Coordinate(to.getX() + 1, to.getY() - 1);

		boolean hasPieceAdjacent = pieceList.containsKey(oneUp)
				&& pieceList.containsKey(oneDown)
				&& pieceList.containsKey(leftUp)
				&& pieceList.containsKey(leftDown)
				&& pieceList.containsKey(rightUp)
				&& pieceList.containsKey(rightDown);

		return hasPieceAdjacent;
	}

	


}
