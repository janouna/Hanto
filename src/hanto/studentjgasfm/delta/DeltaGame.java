package hanto.studentjgasfm.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.BaseHantoGame;
import hanto.studentjgasfm.common.Coordinate;

public class DeltaGame extends BaseHantoGame {

	public DeltaGame(HantoPlayerColor c) {
		super(c);
		player2SparrowCount = 4;
		player1SparrowCount = 4;
		player2CrabCount = 4;
		player1CrabCount = 4;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		MoveResult result;
		
		if(from != null){
			if(pieceList.get(new Coordinate(from)).getType() != pieceType){
				throw new HantoException("Piece type does not match the piece at the from location");
			}else if (pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB){
				result = walk(pieceType, from, to);
			}else if (pieceType == HantoPieceType.SPARROW)
				result = fly(pieceType, from, to);
			else
				throw new HantoException("Invalid Move");
		}else if(to != null){
			result = placePiece(pieceType, from, to);
		}else{
			result = resign();
		}

		return result;
	}

	private MoveResult resign() {
		MoveResult result;
		HantoPlayerColor color = moveCount % 2 == 1 ? player1Color : player2Color;
		
		if(color == HantoPlayerColor.RED){
			result = MoveResult.BLUE_WINS;
		}else{
			result = MoveResult.RED_WINS;
		}
		
		return result;
	}

	private MoveResult fly(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		return moveValidator(pieceType, from, to);
	}

}
