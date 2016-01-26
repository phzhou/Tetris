package com.sesample.tetris.activities;

import com.sesample.tetris.game.Game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sesample.tetris.R;

public class MainActivity extends Activity implements OnClickListener{

	// Label for intent data specifying mode
	public static final String EXTRA_LEVEL = "com.sesample.tetris.EXTRA_LEVEL";

	public static final int GAME_OVER_TIME_OUT = 2000;

	private GameSurfaceView mGameSurfaceView;
	private Button mLeft;
	private Button mRight;
	private Button mDown;
	private Button mTurn;

	private int mScore;
	private TextView mScoreView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mGameSurfaceView = (GameSurfaceView) findViewById(R.id.game);

		mLeft = (Button) findViewById(R.id.left);
		mLeft.setOnClickListener(this);

		mRight = (Button) findViewById(R.id.right);
		mRight.setOnClickListener(this);

		mDown = (Button) findViewById(R.id.down);
		mDown.setOnClickListener(this);

		mTurn = (Button) findViewById(R.id.turn);
		mTurn.setOnClickListener(this);

		int level = getIntent().getIntExtra(EXTRA_LEVEL, Game.LEVEL_NORMAL);
		TextView levelTextView = (TextView) findViewById(R.id.level);

		final String[] levelNames = {
				"EASY",
				"NORMAL",
				"HARD",
				"DEBUG"
		};
		levelTextView.setText(levelNames[level]);
		mGameSurfaceView.getRenderer().getGame().setLevel(level);

		mGameSurfaceView.setOnClickListener(this);

		mScore = 0;
		mScoreView = (TextView) findViewById(R.id.score);
		Game.OnScoreChangedListener listener = new Game.OnScoreChangedListener() {
			@Override
			public void onScoreChanged(final int score) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mScore = score;
						mScoreView.setText(Integer.toString(mScore));
					}
				});
			}
		};
		mGameSurfaceView.getRenderer().getGame().setOnScoreChangedListener(listener);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mGameSurfaceView.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mGameSurfaceView.onPause();
	}

	@Override
	public void onClick(View v) {
		int command = 0;
		switch (v.getId()) {
			case R.id.left:
				command = Game.COMMAND_LEFT;
				break;
			case R.id.right:
				command = Game.COMMAND_RIGHT;
				break;
			case R.id.turn:
				command = Game.COMMAND_TURN;
				break;
			case R.id.down:
				command = Game.COMMAND_DOWN;
				break;
			case R.id.game:
				if (mGameSurfaceView.getRenderer().getGame().isGameOver()) {
					Intent i = new Intent(MainActivity.this, ResultActivity.class);
					i.putExtra(ResultActivity.EXTRA_SCORE, mScore);
					startActivity(i);
					finish();
				}
				break;
			default:
				break;
		}
		mGameSurfaceView.sendCommand(command);
	}
}
