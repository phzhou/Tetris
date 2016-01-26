package com.sesample.tetris.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.sesample.tetris.R;

/**
 * Created by phzhou on 1/26/16.
 */
public class DeepLinkActivity extends Activity {
    public static final String EXTRA_DEEPLINK_MESSAGE = "com.sesample.tetris.EXTRA_DEEPLINK_MESSAGE";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deeplink);

        final String message = getIntent().getStringExtra(EXTRA_DEEPLINK_MESSAGE);
        TextView deepLinkMessage = (TextView) findViewById(R.id.deep_link);
        deepLinkMessage.setText(message);
    }
}
