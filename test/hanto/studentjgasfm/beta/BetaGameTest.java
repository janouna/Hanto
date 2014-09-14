package hanto.studentjgasfm.beta;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import hanto.HantoGameFactory;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.Coordinate;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class BetaGameTest {
	HantoGame beta;
	HashMap<HantoCoordinate, HantoPiece> pieceList;

	@Before
	public void setUp() throws Exception {
		HantoGameFactory factory = HantoGameFactory.getInstance();
		beta = factory.makeHantoGame(HantoGameID.BETA_HANTO);
		pieceList = new HashMap<HantoCoordinate, HantoPiece>();
	}

	@Test
	public void findPieceTest() throws HantoException{
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0)) == MoveResult.OK);
		assertTrue(beta.getPieceAt(new Coordinate(0,0)).getColor().equals(HantoPlayerColor.BLUE));
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0,1)) == MoveResult.OK);
		assertTrue(beta.getPieceAt(new Coordinate(0,1)).getColor().equals(HantoPlayerColor.RED));
		
	}
	
	
	@Test (expected = HantoException.class)
	public void testMoveOntoAnotherPiece() throws HantoException{
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0)) == MoveResult.OK);
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0,1)) == MoveResult.OK);
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0,0));
		
	}
	
	@Test (expected = HantoException.class)
	public void testCheckAdjacentPieces() throws HantoException{
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0,2)) == MoveResult.OK);
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(3,3));
		assertTrue(beta.getPieceAt(new Coordinate(3,3)).equals(null));
	}
	
	@Test
	public void testButterflyPlacedByFourthMove() throws HantoException{
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 2));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 3));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 4));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 5));
		assertTrue(beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 6)) == MoveResult.OK);
		try{
			beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 7));
			fail();
		}catch(HantoException e){	}
	}
	
	@Test
	public void testFullGameToDraw() throws HantoException{
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 2));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 3));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 4));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 5));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 6));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 7));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 8));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 9));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 10));
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 11)) == MoveResult.DRAW);
	}

	/*
	 * @Test public void addRedButterflyFirst(){
	 * 
	 * }
	 */

}
