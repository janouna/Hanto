/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentjgasfm.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;

public class Move {
	public final HantoCoordinate to, from;
	public final HantoPieceType piece;
	public final MoveResult result;
	
	public Move(HantoPieceType piece, HantoCoordinate from, HantoCoordinate to, MoveResult result){
		this.to = to;
		this.from = from;
		this.piece = piece;
		this.result = result;
	}
}
