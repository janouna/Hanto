/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentjgasfm.alpha;

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


public class AlphaGame implements HantoGame {
	private Map<Coordinate, HantoPiece> pieceList;
	private int moveCount;

	public AlphaGame(){
		pieceList = new HashMap<Coordinate, HantoPiece>();
		moveCount = 1;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result;

		if(moveCount == 1 && to.getX() == 0 && to.getY() == 0 && pieceType == HantoPieceType.BUTTERFLY){
			pieceList.put(new Coordinate(to), new Piece(HantoPlayerColor.BLUE, pieceType));
			result = MoveResult.OK;
		}else if(moveCount == 2 && isAdjacentToOrigin(to) && pieceType == HantoPieceType.BUTTERFLY){
			pieceList.put(new Coordinate(to), new Piece(HantoPlayerColor.RED, pieceType));
			result = MoveResult.DRAW;
		}else{
			throw new HantoException("Invalid Position " + to.getX() + "," + to.getY());
		}
		
		moveCount++;
		return result;
	}

	private boolean isAdjacentToOrigin(HantoCoordinate to) {
		//True 0,1 ; 1,0 ; 0,-1 ; -1,0 ; 1,-1 ; -1,1;  False for 0,0 ; 1,1 ; -1,-1; everything greater
		return !(to.getX() == 0 && to.getY() == 0) && Math.abs(to.getX() + to.getY()) < 2 && Math.abs(to.getX()) <= 1 && Math.abs(to.getY()) <= 1;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return pieceList.get(new Coordinate(where));
	}

	@Override
	public String getPrintableBoard() {
		String board = "";
		for(Coordinate c: pieceList.keySet()){
			HantoPiece p = pieceList.get(c);
			board += c.getX() + "," + c.getY() + " " + p.getColor() + " " + p.getType() + "\n";
		}
		
		return board;
	}

}
