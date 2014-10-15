/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentjgasfm.epsilon;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

/**
 * The fifth iteration of the Hanto game.
 */
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

	/**
	 * Gets a list of all possible moves for the current color
	 * @return A list of all possible moves
	 */
	public List<Move> getPossibleMoveList() {
		final List<Move> moveList = new LinkedList<Move>();
		final HantoPlayerColor color = moveCount % 2 == 1 ? player1Color : player2Color;
		
		if(moveCount <= 2){
			moveList.addAll(getPossiblePlacedPieces(new Coordinate(0, 0)));
		}
		
		final Set<Coordinate> keySet = new HashSet<Coordinate>(pieceList.keySet());
		for(Coordinate c: keySet){
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
				
				moveList.addAll(getPossiblePlacedPieces(c));
			}
		}
		
		return moveList;
	}
	private List<Move> getPossibleWalkMoves(HantoPieceType piece, Coordinate from, HantoPlayerColor color) {
		final List<Move> moveList = new LinkedList<Move>();
		
		for(Coordinate to: getAdjacentSpaces(from)){
			boolean completed;
			MoveResult result = null;
			try{
				result = walk(piece, from, to);
				completed = true;
			}catch(HantoException e){
				completed = false;
			}
			
			if(completed){
				moveList.add(new Move(piece, from, to, result));
				revertTestMove(piece, from, to, color);
			}
		}
		return moveList;
	}
	private List<Move> getPossibleFlyMoves(HantoPieceType piece, Coordinate from, HantoPlayerColor color) {
		final List<Move> moveList = new LinkedList<Move>();
		final List<Coordinate> validFlyList = getValidFlyArea(from);
		
		for(Coordinate to: validFlyList){
			boolean completed;
			MoveResult result = null;
			try{
				result = fly(piece, from, to);
				completed = true;
			}catch(HantoException e){
				completed = false;
			}
			
			if(completed){
				moveList.add(new Move(piece, from, to, result));
				revertTestMove(piece, from, to, color);
			}
		}
		
		return moveList;
	}
	private List<Move> getPossibleJumpMoves(HantoPieceType piece, Coordinate from, HantoPlayerColor color) {
		final List<Move> moveList = new LinkedList<Move>();
		
		for(Coordinate to: getAdjacentPieceList(from)){
			Coordinate prevCoordinate = from;
			Coordinate farCoordinate = to;
			while(pieceList.containsKey(farCoordinate)){
				Coordinate temp = farCoordinate;
				farCoordinate = getFarCoordinate(prevCoordinate, temp);
				prevCoordinate = temp;
			}
			
			boolean completed;
			MoveResult result = null;
			try{
				result = jump(piece, from, farCoordinate);
				completed = true;
			}catch(HantoException e){
				completed = false;
			}
			
			if(completed){
				moveList.add(new Move(piece, from, farCoordinate, result));
				revertTestMove(piece, from, farCoordinate, color);
			}
		}
		
		return moveList;
	}
	private static Coordinate getFarCoordinate(Coordinate from, Coordinate to) {
		final int xDiff = to.getX() - from.getX();
		final int yDiff = to.getY() - from.getY();
		return new Coordinate(to.getX() + xDiff, to.getY() + yDiff);
	}
	private void revertTestMove(HantoPieceType piece, Coordinate from, Coordinate to, HantoPlayerColor color) {
		revertMove(piece, from, to, color);
		moveCount--;
	}
	
	private List<Move> getPossiblePlacedPieces(Coordinate c) {
		final List<Move> moveList = new LinkedList<Move>();
		final HantoPlayerColor color = moveCount % 2 == 1 ? player1Color : player2Color;
		List<Coordinate> adjacentList;
		
		if(moveCount == 1){
			adjacentList = new LinkedList<>();
			adjacentList.add(new Coordinate(0, 0));
		}else {
			adjacentList = getAdjacentSpaces(c);
		}
		
		for(Coordinate coordinate: adjacentList){
			if(!pieceList.containsKey(coordinate)){
				boolean opponentPieces = false;
				for(Coordinate piece: getAdjacentPieceList(coordinate)){
					if(pieceList.get(piece).getColor() != color){
						opponentPieces = true;
					}
				}
				if(!opponentPieces || moveCount <= 2){
					testPlacePiece(moveList, coordinate, HantoPieceType.BUTTERFLY);
					testPlacePiece(moveList, coordinate, HantoPieceType.SPARROW);
					testPlacePiece(moveList, coordinate, HantoPieceType.CRAB);
					testPlacePiece(moveList, coordinate, HantoPieceType.HORSE);
				}
			}
		}
		
		return moveList;
	}
	private void testPlacePiece(List<Move> moveList, Coordinate coordinate, HantoPieceType piece) {
		boolean completed;
		MoveResult result = null;
		
		try{
			result = moveValidator(piece, null, coordinate);
			completed = true;
		}catch(HantoException e){
			completed = false;
		}
		
		if(completed){
			moveList.add(new Move(piece, null, coordinate, result));
			revertPlacePiece(piece, coordinate);
		}
	}
	private void revertPlacePiece(HantoPieceType piece, Coordinate coordinate) {
		moveCount--;
		final HantoPlayerColor color = moveCount % 2 == 1 ? player1Color : player2Color;
		
		pieceList.remove(coordinate);
		switch(piece){
		case BUTTERFLY:
			if(color == player1Color){
				player1ButterflyLocation = null;
				player1ButterflyPlaced = false;
			}else{
				player2ButterflyLocation = null;
				player2ButterflyPlaced = false;
			}
			break;
		case SPARROW:
			if(color == player1Color){
				player1SparrowCount += 1;
			}else{
				player2SparrowCount += 1;
			}
			break;
		case CRAB:
			if(color == player1Color){
				player1CrabCount += 1;
			}else{
				player2CrabCount += 1;
			}
			break;
		case HORSE:
			if(color == player1Color){
				player1HorseCount += 1;
			}else{
				player2HorseCount += 1;
			}
			break;
		default:
			break;
		}
	}

	private MoveResult jump(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		boolean validJump = true;
		
		if(getAdjacentSpaces(from).contains(to)){
			throw new HantoException("Can't jump to an adjacent space");
		}
		
		if(from.getX() == to.getX()){
			final int min = Math.min(from.getY(), to.getY());
			final int max = Math.max(from.getY(), to.getY());
			final int x = from.getX();
			for(int i = min + 1; i < max; i++){
				if(!pieceList.containsKey(new Coordinate(x, i))){
					validJump = false;
				}
			}
		}else if(from.getY() == to.getY()){
			final int min = Math.min(from.getX(), to.getX());
			final int max = Math.max(from.getX(), to.getX());
			final int y = from.getY();
			for(int i = min + 1; i < max; i++){
				if(!pieceList.containsKey(new Coordinate(i, y))){
					validJump = false;
				}
			}
		}else if((from.getX() - to.getX()) == -1 * (from.getY() - to.getY())){
			final int xMin = Math.min(from.getX(), to.getX());
			final int xMax = Math.max(from.getX(), to.getX());
			final int yMax = Math.max(from.getY(), to.getY());
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

	public Map<Coordinate, HantoPiece> getPieceList() {
		return new HashMap<>(pieceList);
	}

}
