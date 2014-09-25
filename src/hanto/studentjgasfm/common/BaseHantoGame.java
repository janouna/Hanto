package hanto.studentjgasfm.common;

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public abstract class BaseHantoGame implements HantoGame {
	protected Map<Coordinate, HantoPiece> pieceList;
	protected int moveCount;
	protected boolean player1ButterflyPlaced, player2ButterflyPlaced;
	protected HantoPlayerColor player1Color, player2Color;
	protected Coordinate player1ButterflyLocation;
	protected Coordinate player2ButterflyLocation;
	
	public BaseHantoGame(HantoPlayerColor c){
		pieceList = new HashMap<Coordinate, HantoPiece>();
		moveCount = 1;
		player1Color = c;
		player2Color = (player1Color == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED
				: HantoPlayerColor.BLUE;
		player1ButterflyPlaced = false;
		player2ButterflyPlaced = false;
		
	}
	
	@Override
	public abstract MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException;

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return pieceList.get(new Coordinate(where));
	}

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
	
	
	
	protected MoveResult isButterflyTrapped(MoveResult result){
		boolean player1Wins = false;
		boolean player2Wins = false;
		
		if (player1ButterflyLocation != null){
			player2Wins = isPieceTrapped(player1ButterflyLocation);
		}
		if(player2ButterflyLocation != null){
			player1Wins = isPieceTrapped(player2ButterflyLocation);
		}
		
		if (player2Wins){
			result = getWinner(player2Color);
		} else if(player1Wins){
			result = getWinner(player1Color);
		}
		
		return result;
	}
	
	private MoveResult getWinner(HantoPlayerColor playerColor){
		MoveResult result = null;
		switch (playerColor){
		case RED: result = MoveResult.RED_WINS; break;
		case BLUE: result = MoveResult.BLUE_WINS; break;
		}
		
		return result;
	}
	
	
	protected boolean isPieceTrapped(HantoCoordinate to) {
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
	
	
	
}
