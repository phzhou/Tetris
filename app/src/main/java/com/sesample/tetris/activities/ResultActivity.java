package com.sesample.tetris.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sesample.tetris.R;
import com.sesample.tetris.game.Game;

/**
 * Created by phzhou on 1/22/16.
 */
public class ResultActivity extends Activity {
    // Label for intent data specifying mode
    public static final String EXTRA_SCORE = "com.sesample.tetris.EXTRA_SCORE";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra(EXTRA_SCORE, 0);
        TextView scoreTextView= (TextView) findViewById(R.id.result_score);
        scoreTextView.setText(Integer.toString(score));

        Button backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}
