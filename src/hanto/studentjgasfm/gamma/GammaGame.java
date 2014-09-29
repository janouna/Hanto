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
		// TODO Auto-generated constructor stub
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private MoveResult walk(HantoCoordinate to, HantoCoordinate from) throws HantoException{
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
		if(isSpaceOpen(adjacent1) || isSpaceOpen(adjacent2))
			result = MoveResult.OK;
		else
			throw new HantoException("Cannot walk to this location, pieces are blocking move");

		if(!isConnected(to)){
			//TODO Revert Piece back to where it was
			throw new HantoException("Move causes a break in the piece chain");
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
				if(adj1 == null)
					adj1 = c;
				else
					adj2 = c;
			}
		}
	}
	private boolean isSpaceOpen(HantoCoordinate space){
		return !pieceList.containsKey(space);
	}
	
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
	}
}
