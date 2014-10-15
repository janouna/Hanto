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
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.Move;
import hanto.studentjgasfm.epsilon.EpsilonGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

public class HantoPlayer implements HantoGamePlayer {
	HantoPlayerColor myColor, opposingColor;
	boolean player1;
	EpsilonGame game;
	MoveResult winColor;

	public HantoPlayer() {}

	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor, boolean doIMoveFirst) {
		this.myColor = myColor;
		opposingColor = myColor == HantoPlayerColor.BLUE ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		winColor = myColor == HantoPlayerColor.BLUE ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		player1 = doIMoveFirst;

		HantoPlayerColor firstColor = doIMoveFirst ? myColor : opposingColor;

		game = new EpsilonGame(firstColor);
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		HantoMoveRecord myMove = new HantoMoveRecord(null, null, null);
		List<Move> winList = new LinkedList<Move>();
		Map<Coordinate, List<Move>> okList = new HashMap<Coordinate, List<Move>>();
		List<Move> moveButterflyList = new LinkedList<Move>();
		List<Move> surroundEnemyList = new LinkedList<Move>();
		Coordinate enemyButterfly = null;
		
		try{
			if(opponentsMove != null){
				game.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
			}
			
			Map<Coordinate, HantoPiece> pieceList = game.getPieceList();
			for(Coordinate c: pieceList.keySet()){
				if(pieceList.get(c).getType() == HantoPieceType.BUTTERFLY && pieceList.get(c).getColor() == opposingColor){
					enemyButterfly = c;
				}
			}
			
			List<Move> moveList = game.getPossibleMoveList();
			
			for(Move m: moveList){
				if(m.result == winColor){
					winList.add(m);
				}else if(m.result == MoveResult.OK){
					List<Move> temp;
					Coordinate from = m.from == null ? null : new Coordinate(m.from);
					if(okList.containsKey(from)){
						temp = new LinkedList<Move>(okList.get(from));
						okList.remove(from);
					}else
						temp = new LinkedList<Move>();
					temp.add(m);
					okList.put(from, temp);
				}
				
				if(m.piece == HantoPieceType.BUTTERFLY && m.from != null){
					if(game.getAdjacentPieceList(new Coordinate(m.from)).size() > 3){
						moveButterflyList.add(m);
					}
				}
				
				if(enemyButterfly != null && EpsilonGame.getAdjacentSpaces(enemyButterfly).contains(new Coordinate(m.to))){
					if(m.from == null || !EpsilonGame.getAdjacentSpaces(enemyButterfly).contains(new Coordinate(m.from))){
						surroundEnemyList.add(m);
					}
				}
			}
			
			if(!winList.isEmpty()){
				Move move = winList.get((int) Math.floor(Math.random() * winList.size()));
				myMove = new HantoMoveRecord(move.piece, move.from, move.to);
			}else if(!moveButterflyList.isEmpty()){
				Move move = moveButterflyList.get((int) Math.floor(Math.random() * moveButterflyList.size()));
				myMove = new HantoMoveRecord(move.piece, move.from, move.to);
			}else if(!surroundEnemyList.isEmpty()){
				Move move = surroundEnemyList.get((int) Math.floor(Math.random() * surroundEnemyList.size()));
				myMove = new HantoMoveRecord(move.piece, move.from, move.to);
			}else if(!okList.isEmpty()){
				List<Coordinate> coordinateList = new ArrayList<Coordinate>(okList.keySet());
				Coordinate coordinate = coordinateList.get((int) Math.floor(Math.random() * coordinateList.size()));
				List<Move> moveFrom = okList.get(coordinate);
				Move move = moveFrom.get((int) Math.floor(Math.random() * moveFrom.size()));
				myMove = new HantoMoveRecord(move.piece, move.from, move.to);
			}else if(!moveList.isEmpty()){
				Move move = moveList.get((int) Math.floor(Math.random() * moveList.size()));
				myMove = new HantoMoveRecord(move.piece, move.from, move.to);
			}
			
			game.makeMove(myMove.getPiece(), myMove.getFrom(), myMove.getTo());
		}catch (HantoException e){ }
		
		return myMove;
	}
}
