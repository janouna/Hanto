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

public class BetaGame implements HantoGame {
	private HashMap<Coordinate, HantoPiece> pieceList;
	private int moveCount;
	private boolean redButterflyPlaced = false;
	private boolean blueButterflyPlaced = false;

	public BetaGame() {
		pieceList = new HashMap<Coordinate, HantoPiece>();
		moveCount = 1;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result;

		result = moveValidator(pieceType, from, to);

		moveCount++;
		return result;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return pieceList.get(new Coordinate(where));
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	private MoveResult moveValidator(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) throws HantoException {

		MoveResult result;

		if (moveCount == 1 && to.getX() == 0 && to.getY() == 0) {
			pieceList.put(new Coordinate(to), new Piece(HantoPlayerColor.BLUE, pieceType));
			result = MoveResult.OK;
		} else if (moveCount == 7 && !blueButterflyPlaced) {
			if (!pieceType.equals(HantoPieceType.BUTTERFLY)) {
				throw new HantoException(
						"Invalid Piece, Butterfly must be placed by fourth move");
			} else {
				pieceList.put(new Coordinate(to), new Piece(HantoPlayerColor.BLUE, pieceType));
				result = MoveResult.OK;
			}
		} else if (moveCount == 8 && !redButterflyPlaced) {
			if (!pieceType.equals(HantoPieceType.BUTTERFLY)) {
				throw new HantoException(
						"Invalid Piece, Butterfly must be placed by fourth move");
			} else {
				pieceList.put(new Coordinate(to), new Piece(HantoPlayerColor.RED, pieceType));
				result = MoveResult.OK;
			}
		} else if (moveCount > 1 && getPieceAt(to) == null
				&& hasAdjacentPiece(to)) {
			if (moveCount % 2 == 0) {
				pieceList.put(new Coordinate(to), new Piece(HantoPlayerColor.RED, pieceType));
			} else {
				pieceList.put(new Coordinate(to), new Piece(HantoPlayerColor.BLUE, pieceType));
			}
			result = MoveResult.OK;
		} else {
			throw new HantoException("Invalid Position " + to.getX() + ","
					+ to.getY());
		}

		return result;
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
	
	public int getMoveCount(){
		return moveCount;
	}

}
