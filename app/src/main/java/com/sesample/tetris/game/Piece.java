package com.sesample.tetris.game;

public class Piece {

	public static final int PIECES_TYPES = 7;

	// Pieces definition
	// Type * Rotation * size ^ 2
	public static final int[][][][] PIECES =
	{
// Square
			{
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 2, 1, 0},
							{0, 0, 1, 1, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 2, 1, 0},
							{0, 0, 1, 1, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 2, 1, 0},
							{0, 0, 1, 1, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 2, 1, 0},
							{0, 0, 1, 1, 0},
							{0, 0, 0, 0, 0}
					}
			},

// I
			{
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 1, 2, 1, 1},
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 2, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 1, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{1, 1, 2, 1, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 1, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 2, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 0, 0, 0}
					}
			}
			,
// L
			{
					{
							{0, 0, 0, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 2, 0, 0},
							{0, 0, 1, 1, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 1, 2, 1, 0},
							{0, 1, 0, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 1, 1, 0, 0},
							{0, 0, 2, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 1, 0},
							{0, 1, 2, 1, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0}
					}
			},
// L mirrored
			{
					{
							{0, 0, 0, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 2, 0, 0},
							{0, 1, 1, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 1, 0, 0, 0},
							{0, 1, 2, 1, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 1, 1, 0},
							{0, 0, 2, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 1, 2, 1, 0},
							{0, 0, 0, 1, 0},
							{0, 0, 0, 0, 0}
					}
			},
// N
			{
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 1, 0},
							{0, 0, 2, 1, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 1, 2, 0, 0},
							{0, 0, 1, 1, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 1, 2, 0, 0},
							{0, 1, 0, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 1, 1, 0, 0},
							{0, 0, 2, 1, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0}
					}
			},
// N mirrored
			{
					{
							{0, 0, 0, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 2, 1, 0},
							{0, 0, 0, 1, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 2, 1, 0},
							{0, 1, 1, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 1, 0, 0, 0},
							{0, 1, 2, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 1, 1, 0},
							{0, 1, 2, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0}
					}
			},
// T
			{
					{
							{0, 0, 0, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 2, 1, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0},
							{0, 1, 2, 1, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 1, 2, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 0, 0, 0, 0}
					},
					{
							{0, 0, 0, 0, 0},
							{0, 0, 1, 0, 0},
							{0, 1, 2, 1, 0},
							{0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0}
					}
			}
	};


	// Displacement of the piece to the position where it is first drawn in the board when it is created
	public static final int[][][] PIECES_INIT_POSITIONS =
	{
/* Square */
		{
			{-2, -3},
			{-2, -3},
			{-2, -3},
			{-2, -3}
		},
/* I */
		{
			{-2, -2},
			{-2, -3},
			{-2, -2},
			{-2, -3}
		},
/* L */
		{
			{-2, -3},
			{-2, -3},
			{-2, -3},
			{-2, -2}
		},
/* L mirrored */
		{
			{-2, -3},
			{-2, -2},
			{-2, -3},
			{-2, -3}
		},
/* N */
		{
			{-2, -3},
			{-2, -3},
			{-2, -3},
			{-2, -2}
		},
/* N mirrored */
		{
			{-2, -3},
			{-2, -3},
			{-2, -3},
			{-2, -2}
		},
/* T */
		{
			{-2, -3},
			{-2, -3},
			{-2, -3},
			{-2, -2}
		},
	};

	/*
	======================================
	Return the type of a block (0 = no-block, 1 = normal block, 2 = pivot block)

	Parameters:

	>> pPiece:		Piece to draw
	>> pRotation:	1 of the 4 possible rotations
	>> pX:			Horizontal position in blocks
	>> pY:			Vertical position in blocks
	======================================
	*/
	public static int getBlockType(int pPiece, int pRotation, int pX, int pY)
	{
		return PIECES[pPiece][pRotation][pX][pY];
	}


	/*
    ======================================
    Returns the horizontal displacement of the piece that has to be applied in order to create it in the
    correct position.

    Parameters:

    >> pPiece:	Piece to draw
    >> pRotation:	1 of the 4 possible rotations
    ======================================
    */
	public static int getXInitialPosition(int pPiece, int pRotation)
	{
		return PIECES_INIT_POSITIONS[pPiece][pRotation][0];
	}


	/*
    ======================================
    Returns the vertical displacement of the piece that has to be applied in order to create it in the
    correct position.

    Parameters:

    >> pPiece:	Piece to draw
    >> pRotation:	1 of the 4 possible rotations
    ======================================
    */
	public static int getYInitialPosition(int pPiece, int pRotation)
	{
		return PIECES_INIT_POSITIONS[pPiece][pRotation][1];
	}

}
