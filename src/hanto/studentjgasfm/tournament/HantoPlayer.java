/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentjgasfm.tournament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.BaseHantoGame;
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.Move;
import hanto.studentjgasfm.epsilon.EpsilonGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

/**
 * An implementation of the HantoGamePlayer interface
 */
public class HantoPlayer implements HantoGamePlayer {
	HantoPlayerColor myColor, opposingColor;
	boolean player1;
	EpsilonGame game;
	MoveResult winColor;

	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor, boolean doIMoveFirst) {
		this.myColor = myColor;
		opposingColor = myColor == HantoPlayerColor.BLUE ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		winColor = myColor == HantoPlayerColor.BLUE ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		player1 = doIMoveFirst;

		final HantoPlayerColor firstColor = doIMoveFirst ? myColor : opposingColor;

		game = new EpsilonGame(firstColor);
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		HantoMoveRecord myMove = new HantoMoveRecord(null, null, null);
		final List<Move> winList = new LinkedList<Move>();
		final Map<Coordinate, List<Move>> okList = new HashMap<Coordinate, List<Move>>();
		final List<Move> moveButterflyList = new LinkedList<Move>();
		final List<Move> surroundEnemyList = new LinkedList<Move>();
		Coordinate enemyButterfly = null;
		
		try{
			if(opponentsMove != null){
				game.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
			}
			
			final Map<Coordinate, HantoPiece> pieceList = game.getPieceList();
			for(Coordinate c: pieceList.keySet()){
				if(pieceList.get(c).getType() == HantoPieceType.BUTTERFLY && pieceList.get(c).getColor() == opposingColor){
					enemyButterfly = c;
				}
			}
			
			final List<Move> moveList = game.getPossibleMoveList();
			
			for(Move m: moveList){
				if(m.getResult() == winColor){
					winList.add(m);
				}else if(m.getResult() == MoveResult.OK){
					List<Move> temp;
					Coordinate from = m.getFrom() == null ? null : new Coordinate(m.getFrom());
					if(okList.containsKey(from)){
						temp = new LinkedList<Move>(okList.get(from));
						okList.remove(from);
					}else{
						temp = new LinkedList<Move>();
					}
					temp.add(m);
					okList.put(from, temp);
				}
				
				if(m.getType() == HantoPieceType.BUTTERFLY && m.getFrom() != null){
					if(game.getAdjacentPieceList(new Coordinate(m.getFrom())).size() > 3){
						moveButterflyList.add(m);
					}
				}
				
				if(enemyButterfly != null && BaseHantoGame.getAdjacentSpaces(enemyButterfly).contains(new Coordinate(m.getTo()))){
					if(m.getFrom() == null || !BaseHantoGame.getAdjacentSpaces(enemyButterfly).contains(new Coordinate(m.getFrom()))){
						surroundEnemyList.add(m);
					}
				}
			}
			
			if(!winList.isEmpty()){
				final Move move = winList.get((int) Math.floor(Math.random() * winList.size()));
				myMove = new HantoMoveRecord(move.getType(), move.getFrom(), move.getTo());
			}else if(!moveButterflyList.isEmpty()){
				final Move move = moveButterflyList.get((int) Math.floor(Math.random() * moveButterflyList.size()));
				myMove = new HantoMoveRecord(move.getType(), move.getFrom(), move.getTo());
			}else if(!surroundEnemyList.isEmpty()){
				final Move move = surroundEnemyList.get((int) Math.floor(Math.random() * surroundEnemyList.size()));
				myMove = new HantoMoveRecord(move.getType(), move.getFrom(), move.getTo());
			}else if(!okList.isEmpty()){
				final List<Coordinate> coordinateList = new ArrayList<Coordinate>(okList.keySet());
				final Coordinate coordinate = coordinateList.get((int) Math.floor(Math.random() * coordinateList.size()));
				final List<Move> moveFrom = okList.get(coordinate);
				final Move move = moveFrom.get((int) Math.floor(Math.random() * moveFrom.size()));
				myMove = new HantoMoveRecord(move.getType(), move.getFrom(), move.getTo());
			}else if(!moveList.isEmpty()){
				final Move move = moveList.get((int) Math.floor(Math.random() * moveList.size()));
				myMove = new HantoMoveRecord(move.getType(), move.getFrom(), move.getTo());
			}
			
			game.makeMove(myMove.getPiece(), myMove.getFrom(), myMove.getTo());
		}catch (HantoException e){
			myMove = new HantoMoveRecord(null, null, null);
		}
		
		return myMove;
	}
}
