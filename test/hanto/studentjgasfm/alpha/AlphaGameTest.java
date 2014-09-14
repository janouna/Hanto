package hanto.studentjgasfm.alpha;

import static org.junit.Assert.*;
import hanto.HantoGameFactory;
import hanto.common.*;
import hanto.studentjgasfm.common.*;

import org.junit.Before;
import org.junit.Test;

public class AlphaGameTest {
	HantoGame alpha;
	
	@Before
	public void setUp() throws Exception {
		HantoGameFactory factory = HantoGameFactory.getInstance();
		alpha = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
	}

	@Test
	public void addBlueButterfly1() throws HantoException{
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
	}
	
	@Test
	public void addBlueButterfly2() throws HantoException{
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		HantoPiece p = alpha.getPieceAt(new Coordinate(0, 0));
		assertTrue(p.getColor() == HantoPlayerColor.BLUE);
		assertTrue(p.getType() == HantoPieceType.BUTTERFLY);
	}
	
	@Test
	public void addBlueButterflyInvalid() throws HantoException{
		try{
			alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1));
			fail();
		}catch(HantoException e){	}
	}
	
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
	
	@Test
	public void addBlueThenRed2() throws HantoException{
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1, -1)) == MoveResult.DRAW);
		HantoPiece p = alpha.getPieceAt(new Coordinate(1, -1));
		assertTrue(p.getColor() == HantoPlayerColor.RED);
		assertTrue(p.getType() == HantoPieceType.BUTTERFLY);
	}
	
	@Test
	public void addRedInvalid1() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		try{
			alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
			fail();
		}catch(HantoException e){	}
	}
	
	@Test
	public void addRedInvalid2() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		try{
			alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(-1, -1));
			fail();
		}catch(HantoException e){	}
	}
	
	@Test
	public void addRedInvalid3() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		try{
			alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(2, -3));
			fail();
		}catch(HantoException e){	}
	}
	
	@Test
	public void addSparrow1() throws HantoException{
		try{
			alpha.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 0));
			fail();
		}catch(HantoException e){	}
	}
	
	@Test
	public void addSparrow2() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		try{
			alpha.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, -1));
			fail();
		}catch(HantoException e){	}
	}
	
	@Test
	public void testBoard1() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1));
		assertEquals("0,1 RED Butterfly\n0,0 BLUE Butterfly\n", alpha.getPrintableBoard());
	}
	
	@Test
	public void testBoard2() throws HantoException{
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1, -1));
		assertEquals("1,-1 RED Butterfly\n0,0 BLUE Butterfly\n", alpha.getPrintableBoard());
	}
}
