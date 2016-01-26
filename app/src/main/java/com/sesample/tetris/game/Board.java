package com.sesample.tetris.game;

/**
 * Created by phzhou on 1/20/16.
 */
public class Board {

    public static final int PIECE_BLOCKS = 5;

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 22;

    public static final int POS_FREE = 0;
    public static final int POS_FILLED = 1;

    private int[][] board;

    public Board() {
        initBoard();
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean isFreeBlock(int pX, int pY) {
        return board[pX][pY] == POS_FREE;
    }

    /**
     * Check if the piece can be stored at this position without any collision
     * Returns true if the movement is  possible, false if it not possible
     *
     * @param pX Horizontal position in blocks
     * @param pY Vertical position in blocks
     * @param pPiece Piece to draw
     * @param pRotation 1 of the 4 possible rotations
     * @return the movement is possible
     */
    public boolean isPossibleMovement(int pX, int pY, int pPiece, int pRotation) {

        // Checks collision with pieces already stored in the board or the board limits
        // This is just to check the 5x5 blocks of a piece with the appropriate area in the board
        for (int i1 = pX, i2 = 0; i1 < pX + PIECE_BLOCKS; i1++, i2++)
        {
            for (int j1 = pY, j2 = 0; j1 < pY + PIECE_BLOCKS; j1++, j2++)
            {
                // Check if the piece is outside the limits of the board
                if (	i1 < 0 			||
                        i1 > BOARD_WIDTH  - 1	||
                        j1 > BOARD_HEIGHT - 1)
                {
                    if (Piece.getBlockType(pPiece, pRotation, j2, i2) != 0)
                        return false;
                }

                // Check if the piece have collisioned with a block already stored in the map
                if (j1 >= 0)
                {
                    if ((Piece.getBlockType(pPiece, pRotation, j2, i2) != 0) &&
                            (!isFreeBlock(i1, j1))	)
                        return false;
                }
            }
        }
        // No collision
        return true;
    }

    private boolean inBoardRange(int x, int y) {
        return x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT;
    }

    public void storePiece(int pX, int pY, int pPiece, int pRotation) {
        // Store each block of the piece into the board
        for (int i1 = pX, i2 = 0; i1 < pX + PIECE_BLOCKS; i1++, i2++)
        {
            for (int j1 = pY, j2 = 0; j1 < pY + PIECE_BLOCKS; j1++, j2++)
            {
                // Store only the blocks of the piece that are not holes
                if (Piece.getBlockType(pPiece, pRotation, j2, i2) != 0) {
                    if (inBoardRange(i1, j1)) {
                        board[i1][j1] = pPiece + 1; // 1 ~ 7
                    }
                }
            }
        }
    }

    public int deletePossibleLines() {
        int deleted = 0;
        for (int j = 0; j < BOARD_HEIGHT; j++)
        {
            int i = 0;
            while (i < BOARD_WIDTH)
            {
                if (board[i][j] == POS_FREE) break;
                i++;
            }

            if (i == BOARD_WIDTH) {
                deleteLine(j);
                deleted++;
            }
        }
        return deleted;
    }

    public boolean isGameOver() {
        //If the first line has blocks, then, game over
        for (int i = 0; i < BOARD_WIDTH; i++) {
            if (board[i][0] != POS_FREE)
                return true;
        }
        return false;
    }

    public void initBoard() {
        board = new int[BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = 0; i < BOARD_WIDTH; i++)
            for (int j = 0; j < BOARD_HEIGHT; j++)
                board[i][j] = POS_FREE;
    }

    public void deleteLine(int pY) {
        // Moves all the upper lines one row down
        for (int j = pY; j > 0; j--)
        {
            for (int i = 0; i < BOARD_WIDTH; i++)
            {
                board[i][j] = board[i][j-1];
            }
        }
    }
}
