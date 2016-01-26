package com.sesample.tetris.activities;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.sesample.tetris.render.MyGLRenderer;

public class GameSurfaceView extends GLSurfaceView {

	private MyGLRenderer mRenderer;

	private void init(Context context) {
		setEGLContextClientVersion(2);
		mRenderer = new MyGLRenderer(context);
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	public GameSurfaceView(Context context) {
		super(context);
		init(context);
	}

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyGLRenderer getRenderer() {
		return mRenderer;
	}

	public void sendCommand(int command) {
		mRenderer.sendCommand(command);
		requestRender();
	}
}
