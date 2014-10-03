/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentjgasfm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

/**
 * The Base Hanto class.  All game classes except Alpha extend this class.
 */
public abstract class BaseHantoGame implements HantoGame {
	protected Map<Coordinate, HantoPiece> pieceList;
	protected int moveCount, moveLimit, turnLimit;
	protected boolean player1ButterflyPlaced, player2ButterflyPlaced;
	protected HantoPlayerColor player1Color, player2Color;
	protected Coordinate player1ButterflyLocation;
	protected Coordinate player2ButterflyLocation;
	protected int player1SparrowCount, player2SparrowCount;
	protected int player2CrabCount;
	protected int player1CrabCount;
	protected boolean resigned;

	/**
	 * Constructor for BaseHantoGame.
	 * @param c HantoPlayerColor
	 */
	protected BaseHantoGame(HantoPlayerColor c){
		pieceList = new HashMap<Coordinate, HantoPiece>();
		moveCount = 1;
		player1Color = c;
		player2Color = (player1Color == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		player1ButterflyPlaced = false;
		player2ButterflyPlaced = false;
		player1SparrowCount = 0;
		player2SparrowCount = 0;
		player2CrabCount = 0;
		player1CrabCount = 0;
		turnLimit = 0;
		moveLimit = turnLimit * 2;
		resigned = false;
	}

	@Override
	public abstract MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException;

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return pieceList.get(new Coordinate(where));
	}


	/**
	 * Returns a string that contains the values of each piece on the board and their locations
	 */
	@Override
	public String getPrintableBoard() {
		String board = "";
		for (Coordinate c : pieceList.keySet()) {
			HantoPiece p = pieceList.get(c);
			board += c.getX() + "," + c.getY() + " " + p.getColor() + " "
					+ p.getType() + "\n";
		}

		return board;
	}

	/**
	 * This is a helper function to makeMove that determines whether the piece can
	 * be placed a specific location based on the color of the pieces. (Since a piece
	 * must be adjacent to one of it's own color)
	 * @param pieceType The type of the piece
	 * @param from The location that the piece is coming from (null for this method generally)
	 * @param to The location that the piece is being placed
	 * @return The result of the piece placement
	 * @throws HantoException
	 */
	protected MoveResult placePiece(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		HantoPlayerColor color = moveCount % 2 == 1 ? player1Color : player2Color;

		if (moveCount > 2){
			for(Coordinate c: getAdjacentPieceList(new Coordinate(to))){
				if(pieceList.get(c).getColor() != color){
					throw new HantoException("Cannot place pieces next to pieces of the opposite color");
				}
			}
		}

		return moveValidator(pieceType, from, to);
	}

	/**
	 * Called when a piece is walking
	 * @param pieceType The type of the piece
	 * @param from The location that the piece is coming from (null if piece isn't moving between two locations)
	 * @param to The location that the piece is moving to
	 * @return The result of the walking
	 * @throws HantoException
	 */
	protected MoveResult walk(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException{
		MoveResult result;
		AdjacentCoordinatePair p = getAdjacentToFromSpaces(from, to);
		Coordinate f = new Coordinate(from);
		
		if((isSpaceOpen(p.c1) || isSpaceOpen(p.c2)) && f.isAdjacent(to)){
			result = moveValidator(pieceType, from, to);
		}else{
			throw new HantoException("Cannot walk to this location");
		}

		return result;
	}
	/**
	 * Gets the spaces that are adjacent to both the from and to locations. There will always be two coordinates that fit this criteria.
	 * @param from The location the piece is moving from
	 * @param to The location the piece is moving to
	 * @return The AdjacentCoordinatePair containing the two coordinates adjacent to both to and from
	 */
	private AdjacentCoordinatePair getAdjacentToFromSpaces(HantoCoordinate from, HantoCoordinate to){
		Coordinate adj1 = null;
		Coordinate adj2 = null;
		Coordinate t = new Coordinate(to);
		for(Coordinate c: getAdjacentSpaces(from)){
			if(t.isAdjacent(c)){
				if(adj1 == null){
					adj1 = c;
				}else{
					adj2 = c;
				}
			}
		}
		
		return new AdjacentCoordinatePair(adj1, adj2);
	}
	private boolean isSpaceOpen(Coordinate space){
		return !pieceList.containsKey(space);
	}

	/**
	 * This method holds all of the most basic things that must be true in order
	 * for a move to be valid. This includes whether the space is already occupied,
	 * if the butterfly has been placed by the 4th move, and making sure that the player 
	 * has the piece that he/she is trying to place.
	 * @param pieceType The type of the piece
	 * @param from The location that the piece is coming from (null if piece isn't moving between two locations)
	 * @param to The location that the piece is moving to
	 * @return The result of the movement
	 * @throws HantoException
	 */
	protected MoveResult moveValidator(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		MoveResult result = MoveResult.OK;

		HantoPlayerColor color = moveCount % 2 == 1 ? player1Color : player2Color;
		
		if(isButterflyTrapped(result) != MoveResult.OK || resigned){
			throw new HantoException("Game is already over");
		}
		if (getPieceAt(to) != null) {
			throw new HantoException("Invalid to position: occupied");
		}

		if (moveCount == 1 && to.getX() == 0 && to.getY() == 0) { // First Move must be at 0,0
			completeTurn(pieceType, from, to, color);
		} else if ((moveCount == 7 && !player1ButterflyPlaced) || (moveCount == 8 && !player2ButterflyPlaced)) {
			// 4th move of each player, butterfly must be or have been placed
			if (!pieceType.equals(HantoPieceType.BUTTERFLY)) {
				throw new HantoException("Invalid Piece, Butterfly must be placed by fourth move");
			} else {
				completeTurn(pieceType, from, to, color);
			}
		} else if (hasAdjacentPiece(to)) { // Piece must have adjacent piece.
			completeTurn(pieceType, from, to, color);
		} else {
			throw new HantoException("Invalid Position " + to.getX() + "," + to.getY());
		}

		result = isButterflyTrapped(result);

		return result;
	}
	
	/**
	 * Called by moveValidator for the final checks before completing the move
	 * @param pieceType The type of the piece
	 * @param from The location that the piece is coming from (null if piece isn't moving between two locations)
	 * @param to The location that the piece is moving to
	 * @param color The color of the current player
	 * @throws HantoException
	 */
	private void completeTurn(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to, HantoPlayerColor color) throws HantoException{
		int sparrowCount = moveCount % 2 == 1 ? player1SparrowCount : player2SparrowCount;
		int crabCount = moveCount % 2 == 1 ? player1CrabCount : player2CrabCount;
		boolean butterflyPlaced = moveCount % 2 == 1 ? player1ButterflyPlaced : player2ButterflyPlaced;
		
		if(from != null){
			HantoPiece p = pieceList.get(new Coordinate(from));
			if(p == null || p.getType() != pieceType){
				throw new HantoException("Piece type does not match the piece at the from location");
			}else{
				pieceList.remove(new Coordinate(from));
			}
		}else if((pieceType.equals(HantoPieceType.SPARROW) && sparrowCount == 0) || 
				(pieceType.equals(HantoPieceType.CRAB) && crabCount == 0) ||
				(pieceType.equals(HantoPieceType.BUTTERFLY) && butterflyPlaced)){
			throw new HantoException("No more of that piece remain");
		}
		
		pieceList.put(new Coordinate(to), new Piece(color, pieceType));

		if(!isConnected(to)){
			revertMove(pieceType, from, to, color);
			throw new HantoException("Move causes a break in the piece chain");
		}else{				
			piecePlaced(pieceType, color, to);
			moveCount++;
		}
	}

	/**
	 * This reverts a piece back to its original (valid) location 
	 * in the event that it is moved and is not connected. 
	 * @param pieceType The type of the piece
	 * @param from The location that the piece is coming from (null if piece isn't moving between two locations)
	 * @param to The location that the piece is moving to
	 * @param color The color of the current player
	 */
	private void revertMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to, HantoPlayerColor color) {
		pieceList.remove(new Coordinate(to));
		if(from != null){
			pieceList.put(new Coordinate(from), new Piece(color, pieceType));
		}
	}

	/**
	 * This makes sure that when a piece is moved it is properly
	 * recorded to ensure that pieces are kept track of.
	 * @param pieceType The type of the piece
	 * @param playerColor The color of the current player
	 * @param to The location that the piece is moving to
	 */
	protected void piecePlaced(HantoPieceType pieceType,
			HantoPlayerColor playerColor, HantoCoordinate to) {
		switch (pieceType){
		case BUTTERFLY: 
			butterflyPlaced(playerColor, to);
			break;
		case SPARROW: 
			sparrowPlaced(playerColor);
			break;
		case CRAB:
			crabPlaced(playerColor);
			break;
		default:
			break;
		}
	}
	private void butterflyPlaced(HantoPlayerColor playerColor, HantoCoordinate to) {
		if (playerColor.equals(player1Color)) {
			player1ButterflyPlaced = true;
			player1ButterflyLocation = new Coordinate(to);
		} else if(playerColor.equals(player2Color)) {
			player2ButterflyPlaced = true;
			player2ButterflyLocation = new Coordinate(to);
		}
	}
	private void sparrowPlaced(HantoPlayerColor playerColor) {
		if (playerColor.equals(player1Color)) {
			player1SparrowCount -= 1;
		} else {
			player2SparrowCount -= 1;
		}
	}
	private void crabPlaced(HantoPlayerColor playerColor) {
		if (playerColor.equals(player1Color)) {
			player1CrabCount -= 1;
		} else {
			player2CrabCount -= 1;
		}
	}

	private boolean hasAdjacentPiece(HantoCoordinate to) {
		boolean hasPieceAdjacent = false;

		for(Coordinate c: getAdjacentSpaces(to)){
			if(pieceList.containsKey(c)){
				hasPieceAdjacent = true;
			}
		}

		return hasPieceAdjacent;
	}
	
	/**
	 * Does a graph search of all of the pieces on the board to make sure 
	 * that all pieces are still connected after a move has been completed.
	 * @param to The place the piece is moving to.  Used as the starting position of the graph search.
	 * @return Whether or not the graph of pieces is one connected blob.
	 */
	private boolean isConnected(HantoCoordinate to){
		List<Coordinate> reachable = new LinkedList<Coordinate>();
		List<Coordinate> toExplore = new ArrayList<Coordinate>();

		reachable.add(new Coordinate(to));
		toExplore.add(new Coordinate(to));

		while(!toExplore.isEmpty()){
			Coordinate x = toExplore.get(0);
			toExplore.remove(0);
			for(Coordinate i: getAdjacentPieceList(x)){
				if(!reachable.contains(i)){
					toExplore.add(i);
					reachable.add(i);
				}
			}
		}

		return reachable.size() == pieceList.keySet().size();

	}
	/**
	 * Gets the list of coordinates that are adjacent and contain pieces
	 * @param to The current coordinate
	 * @return The list of coordinates adjacent that contain pieces
	 */
	protected List<Coordinate> getAdjacentPieceList(Coordinate to){
		List<Coordinate> l = new LinkedList<Coordinate>();

		for(Coordinate i: getAdjacentSpaces(to)){
			if(pieceList.containsKey(i)){
				l.add(i);
			}
		}
		return l;
	}
	/**
	 * Gets the list of coordinates that are adjacent to the given coordinate
	 * @param to The current coordinate
	 * @return The list of coordinates adjacent
	 */
	protected static List<Coordinate> getAdjacentSpaces(HantoCoordinate to){
		List<Coordinate> l = new LinkedList<Coordinate>();

		Coordinate oneUp = new Coordinate(to.getX(), to.getY() + 1);
		Coordinate oneDown = new Coordinate(to.getX(), to.getY() - 1);
		Coordinate leftUp = new Coordinate(to.getX() - 1, to.getY() + 1);
		Coordinate leftDown = new Coordinate(to.getX() - 1, to.getY());
		Coordinate rightUp = new Coordinate(to.getX() + 1, to.getY());
		Coordinate rightDown = new Coordinate(to.getX() + 1, to.getY() - 1);

		l.add(oneUp);
		l.add(oneDown);
		l.add(leftUp);
		l.add(leftDown);
		l.add(rightUp);
		l.add(rightDown);

		return l;		
	}

	private MoveResult isButterflyTrapped(MoveResult result){
		boolean player1Wins = false;
		boolean player2Wins = false;

		if (player1ButterflyLocation != null){
			player2Wins = isPieceTrapped(player1ButterflyLocation);
		}
		if(player2ButterflyLocation != null){
			player1Wins = isPieceTrapped(player2ButterflyLocation);
		}

		if ((player1Wins && player2Wins) || (moveLimit > 0 && moveCount >= moveLimit)){
			result = MoveResult.DRAW;
		} else if (player2Wins){
			result = getWinner(player2Color);
		} else if (player1Wins){
			result = getWinner(player1Color);
		}

		return result;
	}
	private boolean isPieceTrapped(HantoCoordinate to) {
		HantoCoordinate oneUp = new Coordinate(to.getX(), to.getY() + 1);
		HantoCoordinate oneDown = new Coordinate(to.getX(), to.getY() - 1);
		HantoCoordinate leftUp = new Coordinate(to.getX() - 1, to.getY() + 1);
		HantoCoordinate leftDown = new Coordinate(to.getX() - 1, to.getY());
		HantoCoordinate rightUp = new Coordinate(to.getX() + 1, to.getY());
		HantoCoordinate rightDown = new Coordinate(to.getX() + 1, to.getY() - 1);

		boolean hasPieceAdjacent = pieceList.containsKey(oneUp)
				&& pieceList.containsKey(oneDown)
				&& pieceList.containsKey(leftUp)
				&& pieceList.containsKey(leftDown)
				&& pieceList.containsKey(rightUp)
				&& pieceList.containsKey(rightDown);

		return hasPieceAdjacent;
	}
	/**
	 * Returns the winner
	 * @param playerColor The color of the winner
	 * @return The MoveResult corresponding to the winner
	 */
	protected MoveResult getWinner(HantoPlayerColor playerColor){
		MoveResult result = null;
		switch (playerColor){
		case RED: 
			result = MoveResult.RED_WINS; 
			break;
		case BLUE: 
			result = MoveResult.BLUE_WINS; 
			break;
		}

		return result;
	}
	
	/**
	 * Contains a pair of coordinates adjacent to a to and from pair of coordinates.
	 */
	private class AdjacentCoordinatePair{
		private final Coordinate c1, c2;
		
		/**
		 * Constructor for AdjacentCoordinatePair.
		 * @param c1 Coordinate
		 * @param c2 Coordinate
		 */
		private AdjacentCoordinatePair(Coordinate c1, Coordinate c2){
			this.c1 = c1;
			this.c2 = c2;
		}
	}
}
