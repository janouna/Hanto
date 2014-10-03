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

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.Coordinate;
import hanto.studentjgasfm.common.HantoTestGame;
import hanto.studentjgasfm.common.HantoTestGameFactory;
import hanto.studentjgasfm.common.HantoTestGame.PieceLocationPair;
import org.junit.Before;
import org.junit.Test;

public class GammaGameTest {

	HantoTestGame gammaTest;
	HantoTestGame gammaTestRedFirst;
	HantoGame gamma;
	HantoGame gammaRedFirst;

	/**
	 * Method setUp.
	 */
	@Before
	public void setUp() {
		HantoTestGameFactory factory = HantoTestGameFactory.getInstance();
		gammaTest = factory.makeHantoTestGame(HantoGameID.GAMMA_HANTO);
		gammaTestRedFirst = factory.makeHantoTestGame(HantoGameID.GAMMA_HANTO, HantoPlayerColor.RED);
		gamma = gammaTest;
		gammaRedFirst = gammaTestRedFirst;
	}
	
	
	/**
	 * Method addBlueButterfly.
	 * @throws HantoException
	 */
	@Test
	public void addBlueButterfly() throws HantoException{
		assertTrue(gamma.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		HantoPiece p = gamma.getPieceAt(new Coordinate(0, 0));
		assertTrue(p.getColor() == HantoPlayerColor.BLUE);
		assertTrue(p.getType() == HantoPieceType.BUTTERFLY);
	}
	
	/**
	 * Method 
	 * @throws HantoException
	 */
	public void testWalk() throws HantoException{
		assertTrue(gamma.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		assertTrue(gamma.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1)) == MoveResult.OK);
		assertTrue(gamma.makeMove(HantoPieceType.BUTTERFLY, new Coordinate(0, 0), new Coordinate(1, 0)) == MoveResult.OK);
		assertTrue(gamma.getPieceAt(new Coordinate(0, 0)).equals(null));
		assertTrue(gamma.getPieceAt(new Coordinate(1, 0)).getColor().equals(HantoPlayerColor.RED));
	}
	
	/**
	 * Method testMoveOntoAnotherPiece.
	 * @throws HantoException */
	@Test (expected = HantoException.class)
	public void testWalkBrokenChain() throws HantoException{
		assertTrue(gamma.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		assertTrue(gamma.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1)) == MoveResult.OK);
		gamma.makeMove(HantoPieceType.BUTTERFLY, new Coordinate(0, 0), new Coordinate(-1, 0));
	}
	
	@Test
	public void testWalkSurroundedValid1() throws HantoException{
		PieceLocationPair[] initialPieces = {
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,0))
		};
		gammaTest.initializeBoard(initialPieces);
		gammaTest.setTurnNumber(4);
		assertEquals(MoveResult.OK, gamma.makeMove(HantoPieceType.SPARROW, new Coordinate(0,0), new Coordinate(-1,1)));
	}
	
	@Test
	public void testWalkSurroundedValid2() throws HantoException{
		PieceLocationPair[] initialPieces = {
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,0))
		};
		gammaTest.initializeBoard(initialPieces);
		gammaTest.setTurnNumber(4);
		assertEquals(MoveResult.OK, gamma.makeMove(HantoPieceType.SPARROW, new Coordinate(0,0), new Coordinate(0,1)));
	}
	
	@Test (expected = HantoException.class)
	public void testWalkSurroundedInvalid1() throws HantoException{
		PieceLocationPair[] initialPieces = {
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(-1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(-1,1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,0))
		};
		gammaTest.initializeBoard(initialPieces);
		gammaTest.setTurnNumber(4);
		gamma.makeMove(HantoPieceType.SPARROW, new Coordinate(0,0), new Coordinate(0,1));
	}
	
	@Test (expected = HantoException.class)
	public void testWalkSurroundedInvalid2() throws HantoException{
		PieceLocationPair[] initialPieces = {
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new Coordinate(1,0)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(1,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,-1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(-1,1)),
			new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,0))
		};
		gammaTest.initializeBoard(initialPieces);
		gammaTest.setTurnNumber(4);
		gamma.makeMove(HantoPieceType.SPARROW, new Coordinate(0,0), new Coordinate(-1,1));
	}
	
	@Test (expected = HantoException.class)
	public void testPlaceInvalidPiece() throws HantoException{
		PieceLocationPair[] initialPieces = {
				new PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new Coordinate(0,0)),
		};
		gammaTest.initializeBoard(initialPieces);
		gammaTest.setTurnNumber(2);
		gamma.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0,1));
	}
	
	@Test (expected = HantoException.class)
	public void testWalkInvalid() throws HantoException{
		PieceLocationPair[] initialPieces = {
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(1,0)),
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(2,0)),
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,0)),
			};
		gammaTest.initializeBoard(initialPieces);
		gamma.makeMove(HantoPieceType.SPARROW, new Coordinate(0,0), new Coordinate(1,1));
	}
	
	@Test (expected = HantoException.class)
	public void testBreakChain() throws HantoException{
		PieceLocationPair[] initialPieces = {
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(1,0)),
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(2,0)),
				new PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new Coordinate(0,0)),
			};
		gammaTest.initializeBoard(initialPieces);
		gamma.makeMove(HantoPieceType.SPARROW, new Coordinate(1,0), new Coordinate(1,1));
	}
	
	@Test (expected = HantoException.class)
	public void addInvalidPiece() throws HantoException{
		gamma.makeMove(HantoPieceType.CRAB, null, new Coordinate(0,0));
	}
}