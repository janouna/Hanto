/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentjgasfm.beta;

import static org.junit.Assert.assertTrue;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.HantoGameFactory;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing BetaGame
 */
public class BetaGameTest {
	HantoGame beta;
	HantoGame betaRedFirst;
	Map<HantoCoordinate, HantoPiece> pieceList;

	/**
	 * Method setUp.
	 */
	@Before
	public void setUp() {
		HantoGameFactory factory = HantoGameFactory.getInstance();
		beta = factory.makeHantoGame(HantoGameID.BETA_HANTO);
		pieceList = new HashMap<HantoCoordinate, HantoPiece>();
		betaRedFirst = factory.makeHantoGame(HantoGameID.BETA_HANTO, HantoPlayerColor.RED);
	}

	/**
	 * Method findPieceTest.
	 * 
	 * @throws HantoException */
	@Test
	public void findPieceTest() throws HantoException{
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0)) == MoveResult.OK);
		assertTrue(beta.getPieceAt(new Coordinate(0, 0)).getColor().equals(HantoPlayerColor.BLUE));
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1)) == MoveResult.OK);
		assertTrue(beta.getPieceAt(new Coordinate(0, 1)).getColor().equals(HantoPlayerColor.RED));
	}
	
	
	/**
	 * Method testMoveOntoAnotherPiece.
	 * @throws HantoException */
	@Test (expected = HantoException.class)
	public void testMoveOntoAnotherPiece() throws HantoException{
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0)) == MoveResult.OK);
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1)) == MoveResult.OK);
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0));
	}
	
	/**
	 * Method testCheckAdjacentPieces.
	 * @throws HantoException */
	@Test (expected = HantoException.class)
	public void testCheckAdjacentPieces() throws HantoException{
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 2)) == MoveResult.OK);
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(3, 3));
	}
	
	/**
	 * Method testThatPlayerCannotPlaceMultipleButterflies.
	 * @throws HantoException */
	@Test (expected = HantoException.class)
	public void testThatPlayerCannotPlaceMultipleButterflies() throws HantoException{
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1));
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 2));
	}
	
	/**
	 * Method testButterflyPlacedByFourthMove.
	 * @throws HantoException */
	@Test (expected = HantoException.class)
	public void testButterflyPlacedByFourthMove() throws HantoException{
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 2));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 3));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 4));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 5));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 6));
	}
	
	/**
	 * Method butterflyIsPlacedByFourthMoveOfPlayer2.
	 * @throws HantoException
	 */
	@Test
	public void butterflyIsPlacedByFourthMoveOfPlayer2() throws HantoException{
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 2));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 3));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 4));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 5));
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 6));
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 7));
	}
	
	/**
	 * Method testbutterflyIsPlacedByFourthMoveOfPlayer2.
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class)
	public void testbutterflyIsPlacedByFourthMoveOfPlayer2() throws HantoException{
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 2));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 3));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 4));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 5));
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 6));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 7));
	}
	
	/**
	 * Method testFullGameToDraw.
	 * @throws HantoException */
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
	
	/**
	 * Method testGameRedWins.
	 * @throws HantoException
	 */
	@Test
	public void testGameRedWins() throws HantoException{
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, -1));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 1));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, -1));
		beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, 0));
		assertTrue(beta.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0)) == MoveResult.RED_WINS);
	}

	/**
	 * Method testGameBlueWins.
	 * @throws HantoException
	 */
	@Test
	public void testGameBlueWins() throws HantoException{
		betaRedFirst.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		betaRedFirst.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1, 0));
		betaRedFirst.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 1));
		betaRedFirst.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, -1));
		betaRedFirst.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, -1));
		betaRedFirst.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		assertTrue(betaRedFirst.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0)) == MoveResult.BLUE_WINS);
	}
	
	/**
	 * Method testBoard1.
	 * @throws HantoException */
	@Test
	public void testBoard1() throws HantoException{
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1));
		assertTrue(beta.getPrintableBoard().contains("0,0 BLUE Butterfly\n"));
		assertTrue(beta.getPrintableBoard().contains("0,1 RED Butterfly\n"));
	}
	
	/**
	 * Method testBoard2.
	 * @throws HantoException */
	@Test
	public void testBoard2() throws HantoException{
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		beta.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1, -1));
		assertTrue(beta.getPrintableBoard().contains("0,0 BLUE Butterfly\n"));
		assertTrue(beta.getPrintableBoard().contains("1,-1 RED Butterfly\n"));
	}
}
