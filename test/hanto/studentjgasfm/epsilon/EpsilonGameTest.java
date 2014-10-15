package hanto.studentjgasfm.epsilon;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.HantoTestGame;
import hanto.studentjgasfm.common.HantoTestGame.PieceLocationPair;
import hanto.studentjgasfm.common.HantoTestGameFactory;

import org.junit.Before;
import org.junit.Test;

public class EpsilonGameTest {
	private HantoTestGame epsilonTest;
	private HantoGame epsilon;

	@Before
	public void setUp() throws Exception {
		HantoTestGameFactory factory = HantoTestGameFactory.getInstance();
		epsilonTest = factory.makeHantoTestGame(HantoGameID.EPSILON_HANTO);
		epsilon = epsilonTest;
	}

	@Test
	public void placeHorseAndJump() throws HantoException{
		assertEquals(MoveResult.OK, epsilon.makeMove(HantoPieceType.HORSE, null, new Coordinate(0,0)));
		assertTrue(epsilon.getPieceAt(new Coordinate(0,0)).getType() == HantoPieceType.HORSE);
		assertEquals(MoveResult.OK, epsilon.makeMove(HantoPieceType.CRAB, null, new Coordinate(1,0)));
		assertEquals(MoveResult.OK, epsilon.makeMove(HantoPieceType.HORSE, new Coordinate(0,0), new Coordinate(2,0)));
		assertTrue(epsilon.getPieceAt(new Coordinate(2,0)).getType() == HantoPieceType.HORSE);
	}
	
	@Test
	public void flyValidDistance() throws HantoException{
		assertEquals(MoveResult.OK, epsilon.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0,0)));
		assertEquals(MoveResult.OK, epsilon.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1,0)));
		assertEquals(MoveResult.OK, epsilon.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(-1,1)));
		assertEquals(MoveResult.OK, epsilon.makeMove(HantoPieceType.CRAB, null, new Coordinate(2,0)));
		assertEquals(MoveResult.OK, epsilon.makeMove(HantoPieceType.BUTTERFLY, new Coordinate(-1,1), new Coordinate(0,1)));
		assertEquals(MoveResult.OK, epsilon.makeMove(HantoPieceType.CRAB, null, new Coordinate(2,1)));
		assertEquals(MoveResult.OK, epsilon.makeMove(HantoPieceType.SPARROW, new Coordinate(0,0), new Coordinate(3,1)));
		assertTrue(epsilon.getPieceAt(new Coordinate(3,1)).getType() == HantoPieceType.SPARROW);
	}
	@Test (expected=HantoException.class)
	public void flyInvalidDistance() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
				new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(-1,0)),
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
				new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new Coordinate(1,0)),
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(2,0)),
				new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(3,0)),
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(4,0))
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setPlayerMoving(HantoPlayerColor.RED);
		epsilon.makeMove(HantoPieceType.SPARROW, new Coordinate(-1,0), new Coordinate(5,0));
	}
	
	@Test
	public void resignNoMovesLeft1() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(13,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(14,0)),
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		assertEquals(MoveResult.RED_WINS, epsilon.makeMove(null, null, null));
	}
	
	@Test
	public void resignNoMovesLeft2() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(13,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(14,0)),
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilonTest.setPlayerMoving(HantoPlayerColor.RED);
		assertEquals(MoveResult.BLUE_WINS, epsilon.makeMove(null, null, null));
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignOnePlaceLeft1() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(13,0)),
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignOnePlaceLeft2() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(13,0)),
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignOnePlaceLeft3() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(13,0)),
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignOnePlaceLeft4() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(13,0)),
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignOnePlaceLeft5() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(13,0)),
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilonTest.setPlayerMoving(HantoPlayerColor.RED);
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignOnePlaceLeft6() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(13,0)),
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilonTest.setPlayerMoving(HantoPlayerColor.RED);
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignOnePlaceLeft7() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(13,0)),
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilonTest.setPlayerMoving(HantoPlayerColor.RED);
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignOnePlaceLeft8() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(13,0)),
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilonTest.setPlayerMoving(HantoPlayerColor.RED);
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignResignMovesLeft1() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.HORSE, new Coordinate(13,0))
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilonTest.setPlayerMoving(HantoPlayerColor.RED);
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignResignMovesLeft2() throws HantoException{
		PieceLocationPair[] pieceList = new PieceLocationPair[] {
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(2,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(3,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(4,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(5,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(6,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(7,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(8,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new Coordinate(9,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(10,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(11,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(12,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, new Coordinate(13,0))
		};
		epsilonTest.initializeBoard(pieceList);
		epsilonTest.setTurnNumber(2);
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoPrematureResignationException.class)
	public void resignFirstTurn() throws HantoException{
		epsilon.makeMove(null, null, null);
	}
	
	@Test (expected=HantoException.class)
	public void addInvalidPiece() throws HantoException{
		epsilon.makeMove(HantoPieceType.CRANE, null, new Coordinate(0,0));
	}
}
