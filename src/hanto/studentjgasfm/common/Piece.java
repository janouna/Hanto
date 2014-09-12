package hanto.studentjgasfm.common;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class Piece implements HantoPiece {
	private HantoPlayerColor color;
	private HantoPieceType type;
	
	public Piece(HantoPlayerColor c, HantoPieceType t){
		color = c;
		type = t;
	}
	
	@Override
	public HantoPlayerColor getColor() {
		return color;
	}

	@Override
	public HantoPieceType getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
