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

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public abstract class BaseHantoGame implements HantoGame {
	protected Map<Coordinate, HantoPiece> pieceList;
	protected int moveCount;
	protected boolean player1ButterflyPlaced, player2ButterflyPlaced;
	protected HantoPlayerColor player1Color, player2Color;
	protected Coordinate player1ButterflyLocation;
	protected Coordinate player2ButterflyLocation;
	protected int player1SparrowCount, player2SparrowCount;
	
	public BaseHantoGame(HantoPlayerColor c){
		pieceList = new HashMap<Coordinate, HantoPiece>();
		moveCount = 1;
		player1Color = c;
		player2Color = (player1Color == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED
				: HantoPlayerColor.BLUE;
		player1ButterflyPlaced = false;
		player2ButterflyPlaced = false;
		
	}
	
	@Override
	public abstract MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException;

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
	
	protected MoveResult moveValidator(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		MoveResult result = MoveResult.OK;
		
		HantoPlayerColor color = moveCount % 2 == 1 ? player1Color : player2Color;
		int sparrowCount = moveCount % 2 == 1 ? player1SparrowCount : player2SparrowCount;
		boolean butterflyPlaced = moveCount % 2 == 1 ? player1ButterflyPlaced : player2ButterflyPlaced;
		
		if(from != null){
			throw new HantoException("Piece cannot be moved, only placed");
		}else if (getPieceAt(to) == null) {
			if (moveCount == 1 && to.getX() == 0 && to.getY() == 0) {
				playerTurn(pieceType, from, to, color);
			} else if ((moveCount == 7 && !player1ButterflyPlaced) || (moveCount == 8 && !player2ButterflyPlaced)) {
				if (!pieceType.equals(HantoPieceType.BUTTERFLY)) {
					throw new HantoException(
							"Invalid Piece, Butterfly must be placed by fourth move");
				} else {
					playerTurn(pieceType, from, to, color);
				}
			} else if (hasAdjacentPiece(to)) {
				if((pieceType.equals(HantoPieceType.SPARROW) && sparrowCount != 0) || (pieceType.equals(HantoPieceType.BUTTERFLY) && !butterflyPlaced)){
					playerTurn(pieceType, from, to, color);
				} else {
					throw new HantoException("No more of that piece remain");
				}
			} else {
				throw new HantoException("Invalid Position " + to.getX() + "," + to.getY());
			}
		} else {
			throw new HantoException("Invalid from position; pieces may only be placed, not moved");
		}
		
		result = isButterflyTrapped(result);
		
		return result;
	}
	
	protected boolean isPieceTrapped(HantoCoordinate to) {
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
	protected MoveResult isButterflyTrapped(MoveResult result){
		boolean player1Wins = false;
		boolean player2Wins = false;
		
		if (player1ButterflyLocation != null){
			player2Wins = isPieceTrapped(player1ButterflyLocation);
		}
		if(player2ButterflyLocation != null){
			player1Wins = isPieceTrapped(player2ButterflyLocation);
		}
		
		if (player2Wins){
			result = getWinner(player2Color);
		} else if(player1Wins){
			result = getWinner(player1Color);
		}
		
		return result;
	}
	private MoveResult getWinner(HantoPlayerColor playerColor){
		MoveResult result = null;
		switch (playerColor){
		case RED: result = MoveResult.RED_WINS; break;
		case BLUE: result = MoveResult.BLUE_WINS; break;
		}
		
		return result;
	}
	
	protected boolean hasAdjacentPiece(HantoCoordinate to) {
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
	
	protected void playerTurn(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to, HantoPlayerColor color){
		if(from != null)
			pieceList.remove(new Coordinate(from));
		pieceList.put(new Coordinate(to), new Piece(color, pieceType));
		piecePlaced(pieceType, color, to);
		moveCount++;
	}
	protected void piecePlaced(HantoPieceType pieceType,
			HantoPlayerColor playerColor, HantoCoordinate to) {
		switch (pieceType){
			case BUTTERFLY: butterflyPlaced(playerColor, to);
							break;
			case SPARROW: sparrowPlaced(playerColor);
						  break;
			default:
				break;
		}
	}
	protected void sparrowPlaced(HantoPlayerColor playerColor) {
		if (playerColor.equals(player1Color)) {
			player1SparrowCount -= 1;
		} else {
			player2SparrowCount -= 1;
		}
	}
	protected void butterflyPlaced(HantoPlayerColor playerColor, HantoCoordinate to) {
		if (playerColor.equals(player1Color)) {
			player1ButterflyPlaced = true;
			player1ButterflyLocation = new Coordinate(to);
		} else if(playerColor.equals(player2Color)) {
			player2ButterflyPlaced = true;
			player2ButterflyLocation = new Coordinate(to);
		}
	}
}
