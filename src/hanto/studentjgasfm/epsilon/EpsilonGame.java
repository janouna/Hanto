package hanto.studentjgasfm.epsilon;

import java.util.ArrayList;
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

	public List<Move> getPossibleMoveList() { // TODO Add place pieces
		List<Move> moveList = new LinkedList<Move>();
		HantoPlayerColor color = moveCount % 2 == 1 ? player1Color : player2Color;
		
		for(Coordinate c: pieceList.keySet()){
			HantoPiece p = pieceList.get(c);
			
			if(p.getColor() == color){
				switch(p.getType()){
				case BUTTERFLY:
					moveList.addAll(getPossibleWalkMoves(HantoPieceType.BUTTERFLY, c, color));
					break;
				case CRAB:
					moveList.addAll(getPossibleWalkMoves(HantoPieceType.CRAB, c, color));
					break;
				case SPARROW:
					moveList.addAll(getPossibleFlyMoves(HantoPieceType.SPARROW, c, color));
					break;
				case HORSE:
					moveList.addAll(getPossibleJumpMoves(HantoPieceType.HORSE, c, color));
					break;
				default:
					break;
				}
			}
		}
		
		return moveList;
	}
	private List<Move> getPossibleWalkMoves(HantoPieceType piece, Coordinate c, HantoPlayerColor color) {
		List<Move> moveList = new LinkedList<Move>();
		
		for(Coordinate d: getAdjacentSpaces(c)){
			MoveResult result;
			try{
				result = walk(piece, c, d);
				moveList.add(new Move(piece, c, d, result));
				revertMove(piece, c, d, color);
			}catch(HantoException e){}
		}
		return moveList;
	}
	private List<Move> getPossibleFlyMoves(HantoPieceType piece, Coordinate c, HantoPlayerColor color) {
		List<Move> moveList = new LinkedList<Move>();
		
		List<Coordinate> validFlyList = new LinkedList<Coordinate>();
		List<Coordinate> toVisitList = getAdjacentSpaces(c);
		
		for(int i = 0; i < 3; i++){
			ArrayList<Coordinate> tempList = new ArrayList<Coordinate>(toVisitList);
			while(tempList.size() > 0){
				Coordinate tempCoordinate = tempList.get(0);
				validFlyList.addAll(getAdjacentSpaces(tempCoordinate));
				toVisitList.addAll(getAdjacentSpaces(tempCoordinate));
			}
		}
		
		for(Coordinate coordinate: validFlyList){
			MoveResult result;
			try{
				result = fly(piece, c, coordinate);
				moveList.add(new Move(piece, c, coordinate, result));
				revertMove(piece, c, coordinate, color);
			}catch(HantoException e){}
		}
		
		return moveList;
	}
	private List<Move> getPossibleJumpMoves(HantoPieceType piece, Coordinate c, HantoPlayerColor color) {
		List<Move> moveList = new LinkedList<Move>();
		
		for(Coordinate d: getAdjacentPieceList(c)){
			Coordinate prevCoordinate = c;
			Coordinate farCoordinate = d;
			while(pieceList.containsKey(farCoordinate)){
				Coordinate temp = farCoordinate;
				farCoordinate = getFarCoordinate(prevCoordinate, temp);
				prevCoordinate = temp;
			}
			
			MoveResult result;
			try{
				result = jump(piece, c, farCoordinate);
				moveList.add(new Move(piece, c, farCoordinate, result));
				revertMove(piece, c, farCoordinate, color);
			}catch(HantoException e){}
		}
		
		return moveList;
	}
	private Coordinate getFarCoordinate(Coordinate from, Coordinate to) {
		// TODO Auto-generated method stub
		return null;
	}

	private MoveResult jump(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		boolean validJump = true;
		
		if(getAdjacentSpaces(from).contains(to)){
			throw new HantoException("Can't jump to an adjacent space");
		}
		
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
