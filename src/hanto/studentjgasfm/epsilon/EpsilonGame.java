package hanto.studentjgasfm.epsilon;

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
					moveList.addAll(getPossibleWalkMoves(c));
					break;
				case CRAB:
					moveList.addAll(getPossibleWalkMoves(c));
					break;
				case SPARROW:
					moveList.addAll(getPossibleFlyMoves(c));
					break;
				case HORSE:
					moveList.addAll(getPossibleJumpMoves(c));
					break;
				}
			}
		}
		
		return moveList;
	}
	private List<Move> getPossibleWalkMoves(Coordinate c) {
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
	private List<Move> getPossibleFlyMoves(Coordinate c) {
		// TODO Auto-generated method stub
		return null;
	}
	private List<Move> getPossibleJumpMoves(Coordinate c) {
		// TODO Auto-generated method stub
		return null;
	}

	private MoveResult jump(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		boolean validJump = true;
		
		if(from.getX() == to.getX()){
			int min = Math.min(from.getY(), to.getY());
			int max = Math.max(from.getY(), to.getY());
			int x = from.getX();
			for(int i = min + 1; i < max; i++){
				if(!pieceList.containsKey(new Coordinate(x,i))){
					validJump = false;
				}
			}
		}else if(from.getY() == to.getY()){
			int min = Math.min(from.getX(), to.getX());
			int max = Math.max(from.getX(), to.getX());
			int y = from.getY();
			for(int i = min + 1; i < max; i++){
				if(!pieceList.containsKey(new Coordinate(i,y))){
					validJump = false;
				}
			}
		}else if((from.getX() - to.getX()) == -1 * (from.getY() - to.getY())){
			int xMin = Math.min(from.getX(), to.getX());
			int xMax = Math.max(from.getX(), to.getX());
			int yMax = Math.max(from.getY(), to.getY());
			for(int i = 1; i < xMax - xMin; i++){
				if(!pieceList.containsKey(new Coordinate(xMin + i, yMax - i))){
					validJump = false;
				}
			}
		}else{
			throw new HantoException("Invalid jump direction");
		}
		
		if(validJump){
			return moveValidator(pieceType, from, to);
		}else{
			throw new HantoException("Jump path not continuous");
		}
	}

}
