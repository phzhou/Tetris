package com.sesample.tetris.game;

import android.os.SystemClock;

/**
 * Created by phzhou on 1/21/16.
 */
public class Timer {

    private long curTime;
    private long lastTime;
    private long deltaTime;

    public Timer() {
        lastTime = getTime();
        curTime = lastTime;
        deltaTime = 0;
    }

    public long getTime() {
        return SystemClock.uptimeMillis();
    }

    public long update() {
        // Elapsed time since last call in ms
        curTime = getTime();
        deltaTime = curTime - lastTime;
        lastTime = getTime();

        return deltaTime;
    }

    public float getFPS() {
        if (deltaTime != 0) {
            return 1000.0f / deltaTime;
        } else {
            return 0;
        }
    }
}
