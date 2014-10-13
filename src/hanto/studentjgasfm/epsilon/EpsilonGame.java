package hanto.studentjgasfm.epsilon;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.BaseHantoGame;
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.Move;
import hanto.studentjgasfm.common.Piece;

public class EpsilonGame extends BaseHantoGame {

	public EpsilonGame(HantoPlayerColor c) {
		super(c);
		player1SparrowCount = 2;
		player2SparrowCount = 2;
		player1CrabCount = 6;
		player2CrabCount = 6;
		player1HorseCount = 4;
		player2HorseCount = 4;
		maxFlyDistance = 4;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result = null;
		
		if(pieceType != null && pieceType != HantoPieceType.BUTTERFLY && pieceType != HantoPieceType.CRAB &&
				pieceType != HantoPieceType.SPARROW && pieceType != HantoPieceType.HORSE){
			throw new HantoException("Invalid Piece Type");
		}
		
		if(from != null){
			if (pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB){
				result = walk(pieceType, from, to);
			}else if (pieceType == HantoPieceType.SPARROW){
				result = fly(pieceType, from, to);
			}else if(pieceType == HantoPieceType.HORSE){
				result = jump(pieceType, from, to);
			}
		}else if(to != null){
			result = placePiece(pieceType, from, to);
		}else{
			result = checkResign();
		}

		return result;
	}

	private MoveResult checkResign() throws HantoPrematureResignationException {
		if(getPossibleMoveList().isEmpty()){
			return resign();
		}else{
			throw new HantoPrematureResignationException();
		}
	}

	public List<Move> getPossibleMoveList() {
		List<Move> moveList = new LinkedList<Move>();
		HantoPlayerColor color = moveCount % 2 == 1 ? player1Color : player2Color;
		
		for(Coordinate c: pieceList.keySet()){
			HantoPiece p = pieceList.get(c);
			
			if(p.getColor() == color){
				switch(p.getType()){
				case BUTTERFLY:
					moveList.addAll(getPossibleButterflyMoves(c));
					break;
				case CRAB:
					moveList.addAll(getPossibleCrabMoves(c));
					break;
				case SPARROW:
					moveList.addAll(getPossibleSparrowMoves(c));
					break;
				case HORSE:
					moveList.addAll(getPossibleHorseMoves(c));
					break;
				}
			}
		}
		
		return moveList;
	}
	private List<Move> getPossibleButterflyMoves(Coordinate c) {
		List<Move> moveList = new LinkedList<Move>();
		
		for(Coordinate d: getAdjacentSpaces(c)){
			MoveResult result;
			try{
				result = walk(HantoPieceType.BUTTERFLY, c, d);
				moveList.add(new Move(HantoPieceType.BUTTERFLY, c, d, result));
			}catch(HantoException e){}
		}
		return moveList;
	}
	private List<Move> getPossibleCrabMoves(Coordinate c) {
		// TODO Auto-generated method stub
		return null;
	}
	private List<Move> getPossibleSparrowMoves(Coordinate c) {
		// TODO Auto-generated method stub
		return null;
	}
	private List<Move> getPossibleHorseMoves(Coordinate c) {
		// TODO Auto-generated method stub
		return null;
	}

	private MoveResult jump(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		// TODO Auto-generated method stub
		return null;
	}

}
