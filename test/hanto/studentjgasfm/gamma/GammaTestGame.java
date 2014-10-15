/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentjgasfm.gamma;

import hanto.common.HantoPlayerColor;
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.HantoTestGame;
import hanto.studentjgasfm.common.Piece;

public class GammaTestGame extends GammaGame implements HantoTestGame {

	public GammaTestGame(HantoPlayerColor c) {
		super(c);
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		for (PieceLocationPair p: initialPieces){
			pieceList.put(new Coordinate(p.location), new Piece(p.player, p.pieceType));
			piecePlaced(p.pieceType, p.player, p.location);
		}
	}

	@Override
	public void setTurnNumber(int turnNumber) {
		moveCount = (turnNumber * 2) - (moveCount % 2);
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		if(player == player2Color)
			moveCount++;
	}
}
