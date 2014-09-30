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
	
	
	/**
	 * This is a helper function to makeMove that determines whether the piece can
	 * be placed a specific location based on the color of the pieces. (Since a piece
	 * must be adjacent to one of it's own color)
	 * 
	 * @param pieceType
	 * @param from -- The location that the piece is coming from (null if piece isn't moving between 
	 * 																two locations)
	 * @param to  -- The location that the piece is moving to
	 * @return -- 
	 * @throws HantoException
	 */
	private MoveResult placePiece(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		HantoPlayerColor color = moveCount % 2 == 1 ? player1Color : player2Color;
		
		if (moveCount > 2){
			for(Coordinate c: getAdjacentPieceList(new Coordinate(to))){
				if(pieceList.get(c).getColor() != color){
					throw new HantoException("Cannot place pieces next to pieces of the opposite color");
				}
			}
		}
		
		return moveValidator(pieceType, from, to);
		
	}

	
	private MoveResult walk(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException{
		MoveResult result; // TODO Actually need to move piece at some point...
		/*
		if(isMovingUpRight(to, from) && (isSpaceOpen(new Coordinate(from.getX(), from.getY() + 1)) ||
										isSpaceOpen(new Coordinate(from.getX() + 1, from.getY() - 1)))){
			result = MoveResult.OK;
		} else if(isMovingDownRight(to, from) && (isSpaceOpen(new Coordinate(from.getX() + 1, from.getY() )) ||
										          isSpaceOpen(new Coordinate(from.getX(), from.getY() - 1)))){
			result = MoveResult.OK;
		} else if(isMovingDownStraight(to, from) && (isSpaceOpen(new Coordinate(from.getX() + 1, from.getY() - 1 )) ||
		          								     isSpaceOpen(new Coordinate(from.getX() - 1, from.getY())))){
		    result = MoveResult.OK;
		} else if(isMovingDownLeft(to, from) && (isSpaceOpen(new Coordinate(from.getX() - 1, from.getY() + 1 )) ||
				     						         isSpaceOpen(new Coordinate(from.getX(), from.getY() - 1)))){
			result = MoveResult.OK;
		}else if(isMovingUpLeft(to, from) && (isSpaceOpen(new Coordinate(from.getX() - 1, from.getY())) ||
											  isSpaceOpen(new Coordinate(from.getX(), from.getY() + 1)))){
			result = MoveResult.OK;
		}else if(isMovingUpStraight(to, from) && (isSpaceOpen(new Coordinate(from.getX() - 1, from.getY() + 1 )) ||
												isSpaceOpen(new Coordinate(from.getX() + 1, from.getY())))){
			result = MoveResult.OK;
		}else{
			throw new HantoException(
					"Cannot walk to this location, pieces are blocking move");
		}*/
		
		Coordinate adjacent1 = null, adjacent2 = null;
		getAdjacentToFromSpaces(from, to, adjacent1, adjacent2);
		if(isSpaceOpen(adjacent1) || isSpaceOpen(adjacent2)){
			result = moveValidator(pieceType, from, to);
		}else{
			throw new HantoException("Cannot walk to this location, pieces are blocking move");
		}
		
		return result;
	}
	/**
	 * Gets the spaces that are adjacent to both the from and to locations. There will always be two coordinates that fit this criteria.
	 * @param from The location the piece is moving from
	 * @param to The location the piece is moving to
	 * @param adj1 One coordinate that is adjacent to both
	 * @param adj2 Another coordinate that is adjacent to both
	 */
	private void getAdjacentToFromSpaces(HantoCoordinate from, HantoCoordinate to, Coordinate adj1, Coordinate adj2){
		adj1 = null;
		adj2 = null;
		Coordinate t = new Coordinate(to);
		for(Coordinate c: getAdjacentSpaces(from)){
			if(t.isAdjacent(c)){
				if(adj1 == null){
					adj1 = c;
				}else{
					adj2 = c;
				}
			}
		}
	}
	private boolean isSpaceOpen(HantoCoordinate space){
		return !pieceList.containsKey(space);
	}
	/*
	private boolean isMovingUpRight(HantoCoordinate to, HantoCoordinate from){
		return (to.getX() - from.getX() == 1) && (to.getY() - from.getY() == 0);
	}
	private boolean isMovingDownRight(HantoCoordinate to, HantoCoordinate from){
		return (to.getX() - from.getX() == 1) && (to.getY() - from.getY() == 1);
	}
	private boolean isMovingDownStraight(HantoCoordinate to, HantoCoordinate from){
		return (to.getX() - from.getX() == 0) && (to.getY() - from.getY() == -1);
	}
	private boolean isMovingDownLeft(HantoCoordinate to, HantoCoordinate from){
		return (to.getX() - from.getX() == -1) && (to.getY() - from.getY() == 0);
	}
	private boolean isMovingUpLeft(HantoCoordinate to, HantoCoordinate from){
		return (to.getX() - from.getX() == -1) && (to.getY() - from.getY() == 1);
	}
	private boolean isMovingUpStraight(HantoCoordinate to, HantoCoordinate from){
		return (to.getX() - from.getX() == 0) && (to.getY() - from.getY() == 1);
	}*/
}
