package hanto.studentjgasfm.delta;

import hanto.common.HantoPlayerColor;
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.HantoTestGame;
import hanto.studentjgasfm.common.Piece;

public class DeltaTestGame extends DeltaGame implements HantoTestGame {

	public DeltaTestGame(HantoPlayerColor c) {
		super(c);
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		for (PieceLocationPair p: initialPieces){
			pieceList.put(new Coordinate(p.location), new Piece(p.player, p.pieceType));
		}
	}

	@Override
	public void setTurnNumber(int turnNumber) {
		moveCount = (turnNumber * 2) - 1;
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		if(player == player2Color)
			moveCount++;
	}
}
