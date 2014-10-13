package hanto.studentjgasfm.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;

public class Move {
	public final HantoCoordinate to, from;
	public final HantoPieceType piece;
	public final MoveResult result;
	
	public Move(HantoPieceType piece, HantoCoordinate from, HantoCoordinate to, MoveResult result){
		this.to = to;
		this.from = from;
		this.piece = piece;
		this.result = result;
	}
}
