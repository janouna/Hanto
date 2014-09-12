package hanto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import hanto.common.*;

public class HantoGameFactoryTest {
	HantoGameFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = HantoGameFactory.getInstance();
	}

	@Test
	public void createAlphaGame() {
		assertTrue(factory.makeHantoGame(HantoGameID.ALPHA_HANTO) instanceof hanto.studentjgasfm.alpha.AlphaGame);
	}

}
