package com.sesample.tetris.render;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.sesample.tetris.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by phzhou on 1/21/16.
 */
public class Sprite {

    private final int vertShaderResourceId = R.raw.sprite_vert;
    private final int fragShaderResourceId = R.raw.sprite_frag;

    private final FloatBuffer vertexBuffer;
    private final FloatBuffer texCoordBuffer;

    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private int mTextureUniformHandle;
    private int mTextureCoordinateHandle;

    private float[] mVertex;
    private float[] mTexCoords;

    private int mTextureHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private float mColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    public Sprite(final Context context, int textureResourceId) {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                3 * 6 * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();

        // initialize the float buffer for tex coords
        ByteBuffer tb = ByteBuffer.allocateDirect(6 * 2 * 4);
        tb.order(ByteOrder.nativeOrder());
        texCoordBuffer = tb.asFloatBuffer();

        // prepare shaders and OpenGL program
        int vertexShader = ResourceHelper.loadShader(context, GLES20.GL_VERTEX_SHADER, vertShaderResourceId);
        int fragmentShader = ResourceHelper.loadShader(context, GLES20.GL_FRAGMENT_SHADER, fragShaderResourceId);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        Log.d("glLink", "Success create shader: " + mProgram);

        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables

        Log.d("glLink", "Success link shader: " + mProgram);


        mTextureHandle = ResourceHelper.loadTexture(context, textureResourceId);

        // Default: a square around the origin
        mVertex = new float[] {
                -0.5f,  0.5f, 0.0f,   // top left
                -0.5f, -0.5f, 0.0f,   // bottom left
                0.5f, -0.5f, 0.0f,   // bottom right
                -0.5f,  0.5f, 0.0f,   // top left
                0.5f, -0.5f, 0.0f,   // bottom right
                0.5f,  0.5f, 0.0f   // top right;
        };

        // Default: the whole texture
        mTexCoords = new float[] {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };
    }

    public void setVertex(float[] vertex) {
        mVertex = vertex;
    }

    public void setVertex(float top, float left, float bottom, float right) {
        mVertex = new float[] {
                left,  top, 0.0f,   // top left
                left, bottom, 0.0f,   // bottom left
                right, bottom, 0.0f,   // bottom right
                left,  top, 0.0f,   // top left
                right, bottom, 0.0f,   // bottom right
                right,  top, 0.0f   // top right;
        };
    }

    public void setTexCoords(float[] texCoords) {
        mTexCoords = texCoords;
    }

    public void draw(float[] mvpMatrix) {
        vertexBuffer.clear();
        vertexBuffer.put(mVertex);
        vertexBuffer.position(0);

        texCoordBuffer.clear();
        texCoordBuffer.put(mTexCoords);
        texCoordBuffer.position(0);

        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, mColor, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Get and set handles for texture
        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureHandle);

        GLES20.glUniform1i(mTextureUniformHandle, 0);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
                8, texCoordBuffer);

        // Draw the square
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}
