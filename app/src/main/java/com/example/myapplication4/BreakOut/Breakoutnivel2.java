package com.example.myapplication4.BreakOut;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class Breakoutnivel2 extends Activity {

    BreakoutEngine2 breakoutEngine2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        Bundle b = getIntent().getExtras();
        final Long score = b.getLong("Score");

        String z = score+"";

        // Initialize gameView and set it as the view
        breakoutEngine2 = new BreakoutEngine2(this, size.x, size.y, z);
        setContentView(breakoutEngine2);


    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        breakoutEngine2.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();



        // Tell the gameView pause method to execute
        breakoutEngine2.pause();
    }
}
