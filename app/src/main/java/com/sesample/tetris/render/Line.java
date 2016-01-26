package com.sesample.tetris.render;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.sesample.tetris.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by phzhou on 1/21/16.
 */
public class Line {
    private FloatBuffer VertexBuffer;

    private final int vertShaderResourceId = R.raw.line_vert;
    private final int fragShaderResourceId = R.raw.line_frag;

    protected int GlProgram;
    protected int PositionHandle;
    protected int ColorHandle;
    protected int MVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float LineCoords[] = {
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f
    };

    private final int VertexCount = LineCoords.length / COORDS_PER_VERTEX;
    private final int VertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    public Line(final Context context) {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                LineCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        VertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        VertexBuffer.put(LineCoords);
        // set the buffer to read the first coordinate
        VertexBuffer.position(0);

        // int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, VertexShaderCode);
        // int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FragmentShaderCode);

        int vertexShader = ResourceHelper.loadShader(context, GLES20.GL_VERTEX_SHADER, vertShaderResourceId);
        int fragmentShader = ResourceHelper.loadShader(context, GLES20.GL_FRAGMENT_SHADER, fragShaderResourceId);

        GlProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(GlProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(GlProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(GlProgram);                  // creates OpenGL ES program executables
    }

    public static Line[] createLineStrip(final Context context, float[] coords) {
        int length = coords.length;
        int count = length / 3; // 3 floats per point
        if (count < 2) {
            return null;
        }

        Line[] result = new Line[count];
        float start[] = {
            coords[0], coords[1], coords[2]
        };

        for(int i=1; i<count; i++) {
            float point[] = {
                coords[i*3],
                coords[i*3 + 1],
                coords[i*3 + 2]
            };
            Line segment = new Line(context);
            segment.SetVerts(
                    start[0], start[1], start[2],
                    point[0], point[1], point[2]
            );
            result[i] = segment;

            start = point;
        }

        Log.d("Create Line Strip", "Created with segments: " + count);

        return result;
    }

    public void SetVerts(float v0, float v1, float v2, float v3, float v4, float v5) {
        LineCoords[0] = v0;
        LineCoords[1] = v1;
        LineCoords[2] = v2;
        LineCoords[3] = v3;
        LineCoords[4] = v4;
        LineCoords[5] = v5;

        VertexBuffer.put(LineCoords);
        // set the buffer to read the first coordinate
        VertexBuffer.position(0);
    }

    public void SetColor(float red, float green, float blue, float alpha) {
        color[0] = red;
        color[1] = green;
        color[2] = blue;
        color[3] = alpha;
    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(GlProgram);

        // get handle to vertex shader's vPosition member
        PositionHandle = GLES20.glGetAttribLocation(GlProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(PositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(PositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                VertexStride, VertexBuffer);

        // get handle to fragment shader's vColor member
        ColorHandle = GLES20.glGetUniformLocation(GlProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(ColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        MVPMatrixHandle = GLES20.glGetUniformLocation(GlProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, VertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(PositionHandle);
        MyGLRenderer.checkGlError("after line");
    }
}
