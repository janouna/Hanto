package hanto.studentjgasfm.delta;

import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.HantoTestGame;
import hanto.studentjgasfm.common.HantoTestGameFactory;
import hanto.studentjgasfm.common.HantoTestGame.PieceLocationPair;

import org.junit.Before;
import org.junit.Test;

public class DeltaGameTest {
	HantoGame delta;
	HantoTestGame deltaTest;

	@Before
	public void setUp() throws Exception {
		HantoTestGameFactory factory = HantoTestGameFactory.getInstance();
		deltaTest = factory.makeHantoTestGame(HantoGameID.DELTA_HANTO);
		delta = deltaTest;
	}

	@Test
	public void testAddCrab() throws HantoException {
		assertEquals(MoveResult.OK, delta.makeMove(HantoPieceType.CRAB, null, new Coordinate(0,0)));
	}
	
	@Test
	public void testMoveCrab() throws HantoException {
		assertEquals(MoveResult.OK, delta.makeMove(HantoPieceType.CRAB, null, new Coordinate(0,0)));
		delta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1,0));
		assertEquals(MoveResult.OK, delta.makeMove(HantoPieceType.CRAB, new Coordinate(0,0), new Coordinate(0,1)));
	}
	
	@Test
	public void testFly() throws HantoException{
		PieceLocationPair[] initialPieces = {
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(1,0)),
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(2,0)),
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,0)),
			};
		deltaTest.initializeBoard(initialPieces);
		assertEquals(MoveResult.OK, delta.makeMove(HantoPieceType.SPARROW, new Coordinate(0,0), new Coordinate(3,0)));
	}
	
	@Test
	public void testResign1() throws HantoException{
		assertEquals(MoveResult.RED_WINS, delta.makeMove(null, null, null));
	}
	
	@Test
	public void testResign2() throws HantoException{
		deltaTest.setTurnNumber(1);
		deltaTest.setPlayerMoving(HantoPlayerColor.RED);
		assertEquals(MoveResult.BLUE_WINS, delta.makeMove(null, null, null));
	}
	
	@Test (expected = HantoException.class)
	public void testPlayAfterResign() throws HantoException{
		assertEquals(MoveResult.RED_WINS, delta.makeMove(null, null, null));
		delta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0,0));
	}
	
	@Test (expected = HantoException.class)
	public void addInvalidPiece() throws HantoException{
		delta.makeMove(HantoPieceType.HORSE, null, new Coordinate(0,0));
	}
}
