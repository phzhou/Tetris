package com.sesample.tetris.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.sesample.tetris.R;
import com.sesample.tetris.game.Game;

/**
 * Created by phzhou on 1/22/16.
 */
public class MenuActivity extends Activity {

    private Spinner mLevelSpinner;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        mLevelSpinner = (Spinner) findViewById(R.id.spinner_level);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.level_array,
                        android.R.layout.simple_spinner_dropdown_item);

        mLevelSpinner.setAdapter(adapter);
        mLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                mMode = pos;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        Button button = (Button) findViewById(R.id.button_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, MainActivity.class);
                i.putExtra(MainActivity.EXTRA_LEVEL, mMode);
                Log.d("LEVEL", "Sending level: " + mMode);
                startActivity(i);
            }
        });
    }
}
