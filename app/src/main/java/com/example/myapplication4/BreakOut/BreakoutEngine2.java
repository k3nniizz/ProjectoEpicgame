package com.example.myapplication4.BreakOut;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.myapplication4.R;

import java.io.IOException;

class BreakoutEngine2 extends SurfaceView implements Runnable{


    private Thread gameThread = null;
    private SurfaceHolder ourHolder;
    private boolean isGameover = false;
    private boolean isWin = false;
    private volatile boolean playing;
    private boolean paused = true;

    // A Canvas and a Paint object
    private Canvas canvas;
    private Paint paint;
    private Bitmap base;
    private Bitmap pelota;
    private Bitmap fondo;
    private Bitmap bloke;
    private Bitmap textgameover;
    private Bitmap textwin;

    // How wide and high is the screen?
    private int screenX;
    private int screenY;

    // This variable tracks the game frame rate
    private long fps;

    // This is used to help calculate the fps
    private long timeThisFrame;

    // The player's bat
    Bat bat;

    // A ball
    Ball ball;

    // Up to 200 bricks
    Brick[] bricks = new Brick[200];
    int numBricks = 0;

    // For sound FX
    SoundPool soundPool;
    int beep1ID = -1;
    int beep2ID = -1;
    int beep3ID = -1;
    int loseLifeID = -1;
    int explodeID = -1;
    int sound1;

    // The score
    int score;

    // Lives
    int lives = 3;

    int cound = 0;

    // The constructor is called when the object is first created
    public BreakoutEngine2(Context context, int x, int y, int z) {
        // This calls the default constructor to setup the rest of the object
        super(context);

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        score = z;

        base = BitmapFactory.decodeResource(getResources(), R.drawable.paddle2);
        bloke = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
        pelota = BitmapFactory.decodeResource(getResources(),R.drawable.meteoro5);
        fondo = BitmapFactory.decodeResource(getResources(),R.drawable.blackhole);
        textgameover = BitmapFactory.decodeResource(getResources(),R.drawable.gameover);
        textwin = BitmapFactory.decodeResource(getResources(),R.drawable.win);


        // Initialize screenX and screenY because x and y are local
        screenX = x;
        screenY = y;

        // Initialize the player's bat
        bat = new Bat(screenX, screenY);

        // Create a ball
        ball = new Ball();

        // Load the sounds
        // This SoundPool is deprecated but don't worry

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){

            soundPool = new SoundPool.Builder().setMaxStreams(10).build();

        }else{

            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC,1);

        }

        beep1ID=soundPool.load(getContext(), R.raw.beep1,1);
        beep2ID=soundPool.load(getContext(), R.raw.beep2,1);
        beep3ID=soundPool.load(getContext(), R.raw.beep3,1);
        loseLifeID=soundPool.load(getContext(), R.raw.loselife,1);
        explodeID=soundPool.load(getContext(), R.raw.explode,1);

        MediaPlayer bk = MediaPlayer.create(getContext(), R.raw.game1);
        bk.start();





        restart();
    }

    // Runs when the OS calls onPause on BreakoutActivity method
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    // Runs when the OS calls onResume on BreakoutActivity method
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();


    }

    @Override
    public void run() {
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Update the frame
            // Update the frame
            if(!paused){
                update();
            }

            // Draw the frame
            draw();

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 400 / timeThisFrame;
            }

        }
    }

    private void update(){
        // Move the bat if required
        bat.update(fps);

        // Update the ball
        ball.update(fps);

        // Check for ball colliding with a brick
        for(int i = 0; i < numBricks; i++){

            if (bricks[i].getVisibility()){

                if(RectF.intersects(bricks[i].getRect(),ball.getRect())) {
                    bricks[i].setInvisible();
                    ball.reverseYVelocity();
                    cound++;
                    //score = score + 10;

                    switch (lives) {

                        case 1: score = score+4;
                            break;
                        case 2: score = score+6;
                            break;
                        case 3: score = score+10;
                            break;

                        default: score = score+10;

                    }

                    soundPool.play(explodeID,1,1,1,0,1);
                }
            }
        }

        // Check for ball colliding with bat
        if(RectF.intersects(bat.getRect(),ball.getRect())) {
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(bat.getRect().top - 2);
            soundPool.play(beep1ID, 1, 1, 0, 0, 1);
        }

        // Bounce the ball back when it hits the bottom of screen
        // And deduct a life
        if(ball.getRect().bottom > screenY){
            ball.reverseYVelocity();
            ball.clearObstacleY(screenY - 2);

            // Lose a life
            lives --;
            soundPool.play(loseLifeID, 1, 1, 0, 0, 1);

            if(lives == 0){
                isGameover= true;
                paused = true;
                //restart();
            }



        }

        // Bounce the ball back when it hits the top of screen
        if(ball.getRect().top < 0){
            ball.reverseYVelocity();
            ball.clearObstacleY(12);
            soundPool.play(beep2ID, 1, 1, 0, 0, 1);
        }

        // If the ball hits left wall bounce
        if(ball.getRect().left < 0){
            ball.reverseXVelocity();
            ball.clearObstacleX(2);
            soundPool.play(beep3ID, 1, 1, 0, 0, 1);
        }

        // If the ball hits right wall bounce
        if(ball.getRect().right > screenX - 10){
            ball.reverseXVelocity();
            ball.clearObstacleX(screenX - 22);
            soundPool.play(beep3ID, 1, 1, 0, 0, 1);
        }

        // Pause if cleared screen
        if(cound==24){
            paused = true;
            isWin=true;
        }
    }

    void restart(){
        // Put the ball back to the start
        ball.reset(screenX, screenY);

        int brickWidth = screenX / 6;
        int brickHeight = screenY / 20;

        // Build a wall of bricks
        numBricks = 0;

        for(int column = 0; column < 8; column ++ ){
            for(int row = 1; row < 5; row ++ ){
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks ++;
            }
        }

        // Reset scores and lives

        lives = 3;

    }

    private void draw(){
        // Make sure our drawing surface is valid or game will crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();


            canvas.drawBitmap(fondo,0,0,null);


            // Draw everything to the screen

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  255, 255, 255));

            // Draw the bat
            canvas.drawBitmap(base,bat.getRect().left, bat.getRect().top, null);

            // Draw the ball
            canvas.drawBitmap(pelota,ball.getRect().left-50,ball.getRect().top-70,null);



            // Draw the bricks if visible
            for(int i = 0; i < numBricks; i++){
                if(bricks[i].getVisibility()) {
                    canvas.drawBitmap(bloke,bricks[i].getRect().left,bricks[i].getRect().top, null);
                }
            }

            // Draw the HUD
            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  255, 255, 255));

            // Draw the score
            paint.setTextSize(70);
            canvas.drawText("Score: " + score + "   Lives: " + lives, 10,80, paint);



            if (isGameover) {
                //paint.setStyle(Paint.Style.FILL_AND_STROKE);
                //paint.setColor(Color.RED);
                canvas.drawBitmap(textgameover,0, screenY /2, null);
                //canvas.drawText("Game Over!", screenX / 3, screenY / 2, paint);

            }
            if (isWin) {
                //paint.setStyle(Paint.Style.FILL_AND_STROKE);
                //paint.setColor(Color.RED);
                canvas.drawBitmap(textwin,30, screenY /2, null);
                //canvas.drawText("Has Ganado!", screenX / 3, screenY / 2, paint);

            }


            // Show everything we have drawn
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // Our code here
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                paused = false;

                if (isWin || isGameover) {
                    paused =true;
                    Intent intent = new Intent(getContext(), Puntuacionbreakout.class);
                    Bundle b = new Bundle();
                    b.putInt("Score", score);
                    intent.putExtras(b);

                    getContext().startActivity(intent);

                }



                if(motionEvent.getX() > screenX / 2){
                    bat.setMovementState(bat.RIGHT);
                }
                else{
                    bat.setMovementState(bat.LEFT);
                }

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                bat.setMovementState(bat.STOPPED);
                break;
        }

        return true;
    }
}

