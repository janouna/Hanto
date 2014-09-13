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

		if(to.getX() == 0 && to.getY() == 0 && moveCount == 1){
			pieceList.put(to, new Piece(HantoPlayerColor.BLUE, pieceType));
			result = MoveResult.OK;
		}else if(to.getX() == 0 && to.getY() == 1 && moveCount == 2){
			pieceList.put(to, new Piece(HantoPlayerColor.RED, pieceType));
			result = MoveResult.DRAW;
		}else{
			throw new HantoException("Invalid Position " + to.getX() + "," + to.getY());
		}
		
		moveCount++;
		return result;
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
