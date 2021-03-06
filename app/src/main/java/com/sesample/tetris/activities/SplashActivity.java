package com.sesample.tetris.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;

import com.sesample.tetris.R;

/**
 * Created by phzhou on 1/22/16.
 */
public class SplashActivity extends Activity {

    //Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // Check if there is incoming deep link and redirect to the deep link activity
                if (false) {
                    redirectToDeepLink("");
                }

                Intent i = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void redirectToDeepLink(String message) {
        Intent i = new Intent(SplashActivity.this, DeepLinkActivity.class);
        i.putExtra(DeepLinkActivity.EXTRA_DEEPLINK_MESSAGE, message);
        startActivity(i);
        finish();
    }
}
