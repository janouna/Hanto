/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentjgasfm.alpha;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import hanto.HantoGameFactory;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentjgasfm.common.Coordinate;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing AlphaGame
 */
public class AlphaGameTest {
	HantoGame alpha;
	
	/**
	 * Method setUp.
	 */
	@Before
	public void setUp() {
		HantoGameFactory factory = HantoGameFactory.getInstance();
		alpha = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
	}

	/**
	 * Method addBlueButterfly1.
	 * @throws HantoException
	 */
	@Test
	public void addBlueButterfly1() throws HantoException{
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
	}
	
	/**
	 * Method addBlueButterfly2.
	 * @throws HantoException
	 */
	@Test
	public void addBlueButterfly2() throws HantoException{
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		HantoPiece p = alpha.getPieceAt(new Coordinate(0, 0));
		assertTrue(p.getColor() == HantoPlayerColor.BLUE);
		assertTrue(p.getType() == HantoPieceType.BUTTERFLY);
	}
	
	/**
	 * Method addBlueButterflyInvalid.
	 * @throws HantoException
	 */
	@Test
	public void addBlueButterflyInvalid() throws HantoException{
		try{
			alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1));
			fail();
		}catch(HantoException e){	
			assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		}
	}
	
	/**
	 * Method addBlueThenRed1.
	 * @throws HantoException
	 */
	@Test
	public void addBlueThenRed1() throws HantoException{
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		HantoPiece p1 = alpha.getPieceAt(new Coordinate(0, 0));
		assertTrue(p1.getColor() == HantoPlayerColor.BLUE);
		assertTrue(p1.getType() == HantoPieceType.BUTTERFLY);
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1)) == MoveResult.DRAW);
		HantoPiece p2 = alpha.getPieceAt(new Coordinate(0, 1));
		assertTrue(p2.getColor() == HantoPlayerColor.RED);
		assertTrue(p2.getType() == HantoPieceType.BUTTERFLY);
	}
	
	/**
	 * Method addBlueThenRed2.
	 * @throws HantoException
	 */
	@Test
	public void addBlueThenRed2() throws HantoException{
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1, -1)) == MoveResult.DRAW);
		HantoPiece p = alpha.getPieceAt(new Coordinate(1, -1));
		assertTrue(p.getColor() == HantoPlayerColor.RED);
		assertTrue(p.getType() == HantoPieceType.BUTTERFLY);
	}
	
	/**
	 * Method addRedInvalid1.
	 * @throws HantoException
	 */
	@Test
	public void addRedInvalid1() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		try{
			alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
			fail();
		}catch(HantoException e){	
			assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1)) == MoveResult.DRAW);
		}
	}
	
	/**
	 * Method addRedInvalid2.
	 * @throws HantoException
	 */
	@Test
	public void addRedInvalid2() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		try{
			alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(-1, -1));
			fail();
		}catch(HantoException e){	
			assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1, -1)) == MoveResult.DRAW);
		}
	}
	
	/**
	 * Method addRedInvalid3.
	 * @throws HantoException
	 */
	@Test
	public void addRedInvalid3() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		try{
			alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(2, -3));
			fail();
		}catch(HantoException e){	
			assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1)) == MoveResult.DRAW);
		}
	}
	
	/**
	 * Method addSparrow1.
	 * @throws HantoException
	 */
	@Test
	public void addSparrow1() throws HantoException{
		try{
			alpha.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0));
			fail();
		}catch(HantoException e){	
			assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		}
	}
	
	/**
	 * Method addSparrow2.
	 * @throws HantoException
	 */
	@Test
	public void addSparrow2() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		try{
			alpha.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, -1));
			fail();
		}catch(HantoException e){	
			assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1, -1)) == MoveResult.DRAW);
		}
	}
	
	/**
	 * Method testBoard1.
	 * @throws HantoException
	 */
	@Test
	public void testBoard1() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1));
		assertTrue(alpha.getPrintableBoard().contains("0,0 BLUE Butterfly\n"));
		assertTrue(alpha.getPrintableBoard().contains("0,1 RED Butterfly\n"));
	}
	
	/**
	 * Method testBoard2.
	 * @throws HantoException
	 */
	@Test
	public void testBoard2() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1, -1));
		assertTrue(alpha.getPrintableBoard().contains("0,0 BLUE Butterfly\n"));
		assertTrue(alpha.getPrintableBoard().contains("1,-1 RED Butterfly\n"));
	}
}
