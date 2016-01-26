package com.sesample.tetris.render;

import com.sesample.tetris.activities.MenuActivity;
import com.sesample.tetris.activities.ResultActivity;
import com.sesample.tetris.game.Piece;
import com.sesample.tetris.game.Timer;
import com.sesample.tetris.game.Game;
import com.sesample.tetris.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Context mContext;

    private static final String TAG = "MyGLRenderer";

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private Timer mTimer;
    private Game mGame;

    private Line[] mBoardFrame;
    private Line[] mNextFrame;
    private Sprite mSprite;
    private Sprite mGameOverSprite;

    public MyGLRenderer(Context context) {
        super();
        mContext = context;
        mTimer = new Timer();
        mGame = new Game(this);
        mGame.initGame();
    }

    public Game getGame() {
        return mGame;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Board frame lines
        mBoardFrame = new Line[4];
        Line west = new Line(mContext);
        west.SetVerts(0, 0, 0, 0, -22, 0);
        mBoardFrame[0] = west;

        Line east = new Line(mContext);
        east.SetVerts(10, 0, 0, 10, -22, 0);
        mBoardFrame[1] = east;

        Line north = new Line(mContext);
        north.SetVerts(0, 0, 0, 10, 0, 0);
        mBoardFrame[2] = north;

        Line south = new Line(mContext);
        south.SetVerts(0, -22, 0, 10, -22, 0);
        mBoardFrame[3] = south;

        // Frame for next piece
        float left = 11f;
        float right = 16f;
        float top = -8f;
        float bottom = -13f;
        mNextFrame = new Line[4];
        west = new Line(mContext);
        west.SetVerts(left, top, 0, left, bottom, 0);
        mNextFrame[0] = west;

        east = new Line(mContext);
        east.SetVerts(right, top, 0, right, bottom, 0);
        mNextFrame[1] = east;

        north = new Line(mContext);
        north.SetVerts(left, top, 0, right, top, 0);
        mNextFrame[2] = north;

        south = new Line(mContext);
        south.SetVerts(left, bottom, 0, right, bottom, 0);
        mNextFrame[3] = south;

        mSprite = new Sprite(mContext, R.drawable.blocks);

        mGameOverSprite = new Sprite(mContext, R.drawable.game_over);
        left = 2;
        right = 8;
        top = -10;
        bottom = -11;
        mGameOverSprite.setVertex(top, left, bottom, right);
    }

    private void prepRender() {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        /*
        Matrix.setLookAtM(mViewMatrix, 0,
            0, 0, 3,
            0f, 0f, 0f,
            0f, 1.0f, 0.0f);
         */
        Matrix.setLookAtM(mViewMatrix, 0,
                8f, -11f, 10f,
                8f, -11f, 0f,
                0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        float deltaTime = (float) mTimer.update();

        // Game update
        mGame.update(deltaTime);

        // Rendering
        prepRender();
        mGame.render();

        drawBoardFrame();
        drawNextFrame();

        if (mGame.isGameOver()) {
            mGameOverSprite.draw(mMVPMatrix);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        /*Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);*/

        Matrix.orthoM(mProjectionMatrix, 0,
                -13 * ratio, 13 * ratio,
                -13, 13,
                3, 50);

    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    private ArrayList<Float> getCoordsFromIndex(int x, int y) {
        float baseX = x * 1 + 0.5f;
        float baseY = - y * 1 - 0.5f;

        Float[] coords = {
                baseX-0.45f, baseY+0.45f, 0.0f,   // top left
                baseX-0.45f, baseY-0.45f, 0.0f,   // bottom left
                baseX+0.45f, baseY-0.45f, 0.0f,   // bottom right
                baseX+0.45f, baseY+0.45f, 0.0f    // top right
        };

        return new ArrayList<>(Arrays.asList(coords));
    }

    private float[] getTexCoordsByType(int pieceType) {

        if (pieceType >= Piece.PIECES_TYPES && pieceType < 0) {
            pieceType = 0;
        }

        float yInterval = 1.0f / 11;
        float yStart = yInterval * pieceType;
        float yEnd = yStart + yInterval;

        float textureCoords[] = {
                0.0f, yStart,
                0.0f, yEnd,
                1.0f, yEnd,
                0.0f, yStart,
                1.0f, yEnd,
                1.0f, yStart
        };
        return textureCoords;
    }

    private float[] getVertexCoorsByPosition(int x, int y) {
        float baseX = 0.5f + x;
        float baseY = -0.5f - y;
        float coords[] = {
            baseX-0.5f, baseY+0.5f, 0.0f,   // top left
            baseX-0.5f, baseY-0.5f, 0.0f,   // bottom left
            baseX+0.5f, baseY-0.5f, 0.0f,   // bottom right
            baseX-0.5f, baseY+0.5f, 0.0f,   // top left
            baseX+0.5f, baseY-0.5f, 0.0f,   // bottom right
            baseX+0.5f, baseY+0.5f, 0.0f }; // top right

        return coords;
    }

    public void drawBlock(int x, int y, int pieceType) {
        mSprite.setTexCoords(getTexCoordsByType(pieceType));
        mSprite.setVertex(getVertexCoorsByPosition(x, y));
        mSprite.draw(mMVPMatrix);
    }

    public void drawBoardFrame() {
        for (int i=0; i<mBoardFrame.length; i++) {
            mBoardFrame[i].draw(mMVPMatrix);
        }
    }

    public void drawNextFrame() {
        for (int i=0; i<mNextFrame.length; i++) {
            mNextFrame[i].draw(mMVPMatrix);
        }
    }

    public void sendCommand(int command) {
        mGame.sendCommand(command);
    }
}