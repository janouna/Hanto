package hanto.studentjgasfm.tournament;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.epsilon.EpsilonGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

import org.junit.Before;
import org.junit.Test;

public class HantoPlayerTest {
	HantoGamePlayer player1, player2;

	@Before
	public void setUp() throws Exception {
		player1 = new HantoPlayer();
		player2 = new HantoPlayer();
		player1.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, true);
		player2.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED, false);
	}

	@Test
	public void testInitialMoves() {
		HantoMoveRecord record1 = player1.makeMove(null);
		assertNull(record1.getFrom());
		assertEquals(new Coordinate(0,0), new Coordinate(record1.getTo()));
		HantoMoveRecord record2 = player2.makeMove(record1);
		assertNull(record2.getFrom());
		assertTrue(new Coordinate(record2.getTo()).isAdjacent(new Coordinate(0,0)));
	}
	
	@Test
	public void testLaterMoves(){
		HantoMoveRecord player1Record = player1.makeMove(null);
		HantoMoveRecord player2Record = player2.makeMove(player1Record);
		player1Record = player1.makeMove(player2Record);
		assertNotNull(player1Record.getPiece());
		assertNotNull(player1Record.getTo());
		player2Record = player2.makeMove(player1Record);
		assertNotNull(player2Record.getPiece());
		assertNotNull(player2Record.getTo());
		player1Record = player1.makeMove(player2Record);
		assertNotNull(player1Record.getPiece());
		assertNotNull(player1Record.getTo());
		player2Record = player2.makeMove(player1Record);
		assertNotNull(player2Record.getPiece());
		assertNotNull(player2Record.getTo());
		player1Record = player1.makeMove(player2Record);
		assertNotNull(player1Record.getPiece());
		assertNotNull(player1Record.getTo());
		player2Record = player2.makeMove(player1Record);
		assertNotNull(player2Record.getPiece());
		assertNotNull(player2Record.getTo());
		player1Record = player1.makeMove(player2Record);
		assertNotNull(player1Record.getPiece());
		assertNotNull(player1Record.getTo());
		player2Record = player2.makeMove(player1Record);
		assertNotNull(player2Record.getPiece());
		assertNotNull(player2Record.getTo());
		player1Record = player1.makeMove(player2Record);
		assertNotNull(player1Record.getPiece());
		assertNotNull(player1Record.getTo());
		player2Record = player2.makeMove(player1Record);
		assertNotNull(player2Record.getPiece());
		assertNotNull(player2Record.getTo());
		player1Record = player1.makeMove(player2Record);
		assertNotNull(player1Record.getPiece());
		assertNotNull(player1Record.getTo());
		player2Record = player2.makeMove(player1Record);
		assertNotNull(player2Record.getPiece());
		assertNotNull(player2Record.getTo());
		player1Record = player1.makeMove(player2Record);
		assertNotNull(player1Record.getPiece());
		assertNotNull(player1Record.getTo());
		player2Record = player2.makeMove(player1Record);
		assertNotNull(player2Record.getPiece());
		assertNotNull(player2Record.getTo());
		player1Record = player1.makeMove(player2Record);
		assertNotNull(player1Record.getPiece());
		assertNotNull(player1Record.getTo());
		player2Record = player2.makeMove(player1Record);
		assertNotNull(player2Record.getPiece());
		assertNotNull(player2Record.getTo());
	}
	
	@Test
	public void testTurnByTurn() throws HantoException{
		EpsilonGame game = new EpsilonGame(HantoPlayerColor.BLUE);
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0,0)));
		assertTrue(game.getPieceAt(new Coordinate(0,0)).getColor() == HantoPlayerColor.BLUE);
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1,0)));
		assertTrue(game.getPieceAt(new Coordinate(0,0)).getColor() == HantoPlayerColor.BLUE);
		assertTrue(game.getPieceAt(new Coordinate(-1,0)).getColor() == HantoPlayerColor.RED);
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.CRAB, null, new Coordinate(1,-1)));
		assertTrue(game.getPieceAt(new Coordinate(0,0)).getColor() == HantoPlayerColor.BLUE);
		assertTrue(game.getPieceAt(new Coordinate(-1,0)).getColor() == HantoPlayerColor.RED);
		assertTrue(game.getPieceAt(new Coordinate(1,-1)).getColor() == HantoPlayerColor.BLUE);
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, new Coordinate(-1,0), new Coordinate(1,0)));
		assertTrue(game.getPieceAt(new Coordinate(0,0)).getColor() == HantoPlayerColor.BLUE);
		assertTrue(game.getPieceAt(new Coordinate(1,0)).getColor() == HantoPlayerColor.RED);
		assertTrue(game.getPieceAt(new Coordinate(1,-1)).getColor() == HantoPlayerColor.BLUE);
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.CRAB, null, new Coordinate(0,-1)));
		assertTrue(game.getPieceAt(new Coordinate(0,0)).getColor() == HantoPlayerColor.BLUE);
		assertTrue(game.getPieceAt(new Coordinate(1,0)).getColor() == HantoPlayerColor.RED);
		assertTrue(game.getPieceAt(new Coordinate(1,-1)).getColor() == HantoPlayerColor.BLUE);
		assertTrue(game.getPieceAt(new Coordinate(0,-1)).getColor() == HantoPlayerColor.BLUE);
	}
}
