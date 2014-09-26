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
	
	public MoveResult walk(HantoCoordinate to, HantoCoordinate from) throws HantoException{
		MoveResult result;
		if(isMovingUpRight(to, from) && (isSpaceOpen(new Coordinate(from.getX(), from.getY() + 1)) ||
										isSpaceOpen(new Coordinate(from.getX() +1, from.getY() -1)))){
			result = MoveResult.OK;
		} else if(isMovingDownRight(to, from) && (isSpaceOpen(new Coordinate(from.getX()+1, from.getY() )) ||
										          isSpaceOpen(new Coordinate(from.getX(), from.getY() -1)))){
			result = MoveResult.OK;
		} else if(isMovingDownStraight(to, from) && (isSpaceOpen(new Coordinate(from.getX()+1, from.getY() -1 )) ||
		          								     isSpaceOpen(new Coordinate(from.getX() - 1, from.getY())))){
		    result = MoveResult.OK;
		} else if(isMovingDownLeft(to, from) && (isSpaceOpen(new Coordinate(from.getX()-1, from.getY() + 1 )) ||
				     						         isSpaceOpen(new Coordinate(from.getX(), from.getY() -1)))){
			result = MoveResult.OK;
		}else if(isMovingUpLeft(to, from) && (isSpaceOpen(new Coordinate(from.getX()-1, from.getY())) ||
											  isSpaceOpen(new Coordinate(from.getX(), from.getY()+1)))){
			result = MoveResult.OK;
		}else if(isMovingUpStraight(to, from) && (isSpaceOpen(new Coordinate(from.getX()-1, from.getY() + 1 )) ||
												isSpaceOpen(new Coordinate(from.getX()+1, from.getY())))){
			result = MoveResult.OK;
		}else{
			throw new HantoException(
					"Cannot slide to this location, pieces are blocking move");
		}

		if(!isConnected(to)){
			//TODO Revert Piece back to where it was
			throw new HantoException(
					"Move causes a break in the piece chain");
		}
		
		return result;
	}
	
	
	protected boolean isSpaceOpen(HantoCoordinate space){
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
