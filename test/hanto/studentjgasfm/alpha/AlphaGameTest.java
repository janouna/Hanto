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
	public void addBlueButterfly() throws HantoException{
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
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
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 1)) == MoveResult.DRAW);
	}
	
	@Test
	public void addBlueThenRed2() throws HantoException{
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0)) == MoveResult.OK);
		assertTrue(alpha.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1, -1)) == MoveResult.DRAW);
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
}
