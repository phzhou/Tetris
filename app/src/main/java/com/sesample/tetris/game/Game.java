package com.sesample.tetris.game;

import android.util.Log;

import com.sesample.tetris.render.MyGLRenderer;

import java.util.Random;

/**
 * Created by phzhou on 1/20/16.
 */
public class Game {

    public static final int LEVEL_MIN = 0;
    public static final int LEVEL_EASY = 0;
    public static final int LEVEL_NORMAL = 1;
    public static final int LEVEL_HARD = 2;
    public static final int LEVEL_DEBUG = 3;
    public static final int LEVEL_MAX = 3;

    // Number of milliseconds that the piece remains before going 1 block down
    public static final int WAIT_TIME_EASY = 700;
    public static final int WAIT_TIME_NORMAL = 400;
    public static final int WAIT_TIME_HARD = 100;
    public static final int WAIT_TIME_DEBUG = 30;

    int mPosX, mPosY;				// Position of the piece that is falling down
    int mPiece, mRotation;			// Type and rotation the piece that is falling down

    int mNextPosX, mNextPosY;		// Position of the next piece
    int mNextPiece, mNextRotation;	// Kind and rotation of the next piece

    Board mBoard;
    MyGLRenderer mRenderer;

    float mDeltaTime;

    public static final int MAIN_STATE = 0;
    public static final int GAME_OVER = 1;
    int mGameState;

    public static final int COMMAND_LEFT = 1;
    public static final int COMMAND_RIGHT = 2;
    public static final int COMMAND_TURN = 3;
    public static final int COMMAND_DOWN = 4;

    private int mNextCommand;

    private int mScore;
    private int mLevel;

    public interface OnScoreChangedListener {
        void onScoreChanged(int score);
    }
    private OnScoreChangedListener mOnScoreChangedListener;

    public Game(MyGLRenderer renderer) {
        mBoard = new Board();
        mRenderer = renderer;
        mDeltaTime = 0;
        mGameState = MAIN_STATE;
        mNextCommand = 0;
        mScore = 0;
    }

    public boolean isGameOver() {
        return mGameState == GAME_OVER;
    }

    public void createNewPiece() {
        // The new piece
        mPiece			= mNextPiece;
        mRotation		= mNextRotation;
        mPosX 			= (Board.BOARD_WIDTH / 2) + Piece.getXInitialPosition(mPiece, mRotation);
        mPosY 			= Piece.getYInitialPosition(mPiece, mRotation);

        // Random next piece
        mNextPiece 		= getRand(0, 6);
        mNextRotation 	= getRand(0, 3);
    }

    public int getRand (int pA, int pB) {
        Random r = new Random();
        return r.nextInt(pB - pA) + pA;
    }

    public void initGame() {
        // First piece
        mPiece			= getRand(0, 6);
        mRotation		= getRand(0, 3);
        mPosX 			= (Board.BOARD_WIDTH / 2) + Piece.getXInitialPosition (mPiece, mRotation);
        mPosY 			= Piece.getYInitialPosition (mPiece, mRotation);

        //  Next piece
        mNextPiece 		= getRand(0, 6);
        mNextRotation 	= getRand (0, 3);
        mNextPosX 		= Board.BOARD_WIDTH + 5;
        mNextPosY 		= 5;
    }

    private void drawPiece(int pX, int pY, int pPiece, int pRotation) {
        // color mColor;				// Color of the block

        // Obtain the position in pixel in the screen of the block we want to draw
        // int mPixelsX = mBoard -> GetXPosInPixels (pX);
        // int mPixelsY = mBoard->GetYPosInPixels(pY);

        // Travel the matrix of blocks of the piece and draw the blocks that are filled
        for (int i = 0; i < Board.PIECE_BLOCKS; i++)
        {
            for (int j = 0; j < Board.PIECE_BLOCKS; j++)
            {
                if (Piece.getBlockType(pPiece, pRotation, j, i) != 0) {
                    if (j + pY < 0)
                        continue;

                    // Register this block for renderer
                    mRenderer.drawBlock(pX + i, pY + j, pPiece);
                }
            }
        }
    }

    private void drawNextPiece(int pPiece, int pRotation) {
        int nextX = 11;
        int nextY = 8;
        // Travel the matrix of blocks of the piece and draw the blocks that are filled
        for (int i = 0; i < Board.PIECE_BLOCKS; i++)
        {
            for (int j = 0; j < Board.PIECE_BLOCKS; j++)
            {
                if (Piece.getBlockType(pPiece, pRotation, j, i) != 0) {
                    // Register this block for renderer
                    mRenderer.drawBlock(nextX + i, nextY + j, pPiece);
                }
            }
        }
    }

    private void drawBoard() {
        int[][] board = mBoard.getBoard();
        for (int y=0; y<Board.BOARD_HEIGHT; y++) {
            for (int x=0; x<Board.BOARD_WIDTH; x++) {
                if (Board.POS_FREE != board[x][y]) {
                    mRenderer.drawBlock(x, y, board[x][y] - 1);
                }
            }
        }
    }

    private void deleteLines() {
        mScore += mBoard.deletePossibleLines();
        if (mOnScoreChangedListener != null) {
            mOnScoreChangedListener.onScoreChanged(mScore);
        }
    }

    public void render() {
        drawBoard();
        drawPiece(mPosX, mPosY, mPiece, mRotation);					// Draw the playing piece
        drawNextPiece(mNextPiece, mNextRotation);	// Draw the next piece
    }

    public void update(float elapsedTime) {

        if (mGameState == GAME_OVER) {
            return;
        }

        mDeltaTime += elapsedTime;
        boolean isEvent = false;

        int wait_times[] = {
                WAIT_TIME_EASY,
                WAIT_TIME_NORMAL,
                WAIT_TIME_HARD,
                WAIT_TIME_DEBUG
        };
        int wait_time = wait_times[mLevel];

        if (mDeltaTime >= wait_time) {
            // Frame!
            isEvent = true;
            mDeltaTime = 0;
        }

        if (isEvent) {

            // FALL
            if (mBoard.isPossibleMovement(mPosX, mPosY + 1, mPiece, mRotation)) {
                mPosY++;
            } else {
                if (mBoard.isGameOver()) {
                    // Game Over!
                    mGameState = GAME_OVER;
                    Log.d("Game Update", "Game Over...");
                } else {
                    // Store piece
                    mBoard.storePiece(mPosX, mPosY, mPiece, mRotation);
                    createNewPiece();
                }
            }

            deleteLines();
        }

        // Process command
        switch (mNextCommand) {
            case COMMAND_DOWN:
                if (mBoard.isPossibleMovement(mPosX, mPosY + 1, mPiece, mRotation)) {
                    mPosY++;
                }
                break;
            case COMMAND_TURN:
                int nextRotation = mRotation - 1;
                if (nextRotation < 0) nextRotation = 3;
                if (mBoard.isPossibleMovement(mPosX, mPosY, mPiece, nextRotation)) {
                    mRotation = nextRotation;
                }
                break;
            case COMMAND_LEFT:
                if (mBoard.isPossibleMovement(mPosX - 1, mPosY, mPiece, mRotation)) {
                    mPosX -= 1;
                }
                break;
            case COMMAND_RIGHT:
                if (mBoard.isPossibleMovement(mPosX + 1, mPosY, mPiece, mRotation)) {
                    mPosX += 1;
                }
                break;
            case 0:
                break;
            default:
                Log.d("Command", "Invalid command: " + mNextCommand);
                break;
        }
        mNextCommand = 0;
    }

    public void sendCommand(int command) {
        mNextCommand = command;
    }

    public void setOnScoreChangedListener(OnScoreChangedListener listener) {
        mOnScoreChangedListener = listener;
    }

    public void setLevel(int level) {
        mLevel = getValidLevel(level);
    }

    public int getValidLevel(int level) {
        return level > LEVEL_MAX ? LEVEL_MAX : (level < LEVEL_MIN ? LEVEL_MIN : level);
    }
}
