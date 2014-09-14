/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import hanto.common.*;

public class HantoGameFactoryTest {
	HantoGameFactory factory;

	@Before
	public void setUp() {
		factory = HantoGameFactory.getInstance();
	}

	@Test
	public void createAlphaGame() {
		assertTrue(factory.makeHantoGame(HantoGameID.ALPHA_HANTO) instanceof hanto.studentjgasfm.alpha.AlphaGame);
	}
	
	@Test
	public void createBetaGame(){
		assertTrue(factory.makeHantoGame(HantoGameID.BETA_HANTO) instanceof hanto.studentjgasfm.beta.BetaGame);
	}

}
