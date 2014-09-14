package hanto.studentjgasfm.alpha;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.Piece;

import java.util.HashMap;


public class AlphaGame implements HantoGame {
	private HashMap<HantoCoordinate, HantoPiece> pieceList;
	private int moveCount;

	public AlphaGame(){
		pieceList = new HashMap<HantoCoordinate, HantoPiece>();
		moveCount = 1;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result;

		if(moveCount == 1 && to.getX() == 0 && to.getY() == 0 && pieceType == HantoPieceType.BUTTERFLY){
			pieceList.put(to, new Piece(HantoPlayerColor.BLUE, pieceType));
			result = MoveResult.OK;
		}else if(moveCount == 2 && isAdjacentToOrigin(to) && pieceType == HantoPieceType.BUTTERFLY){
			pieceList.put(to, new Piece(HantoPlayerColor.RED, pieceType));
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
