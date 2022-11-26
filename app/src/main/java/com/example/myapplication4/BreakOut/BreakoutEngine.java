package com.example.myapplication4.BreakOut;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.example.myapplication4.R;


class BreakoutEngine extends SurfaceView implements Runnable{



    // hilo conductor
    private Thread gameThread = null;

    private ImageView imageView;

    Bitmap base;
    Bitmap pelota;
    Bitmap fondo;


    private SurfaceHolder ourHolder;

    // e juego se esta ejecutando true
    private volatile boolean playing;

    // juego en pausa
    private boolean paused = true;

    // objeto canvas y paint
    private Canvas canvas;
    private Paint paint;
    private Paint paint2;
    private int cound = 0;
    private int numblokes=0;


    // dimensiones del movil
    private int screenX;
    private int screenY;

    // rastrea la velocidad de fotogramas
    private long fps;

    // calcula los fps
    private long timeThisFrame;


    // objeto barra
    Bat bat;

    // objeto bola
    Ball ball;

    // Up to 200 bricks
    Brick[] bricks = new Brick[200];
    int numBricks = 0;


    // Puntuacion
    int score = 0;
    int score2 = 0;

    // Lives
    int lives = 3;



    // Se llama al constructor cuando se crea el objeto por primera vez
    public BreakoutEngine(Context context, int x, int y) {
        // Esto llama al constructor predeterminado para configurar el resto del objeto
        super(context);
        base = BitmapFactory.decodeResource(getResources(), R.drawable.bat4);
        pelota = BitmapFactory.decodeResource(getResources(),R.drawable.meteoro5);
        fondo = BitmapFactory.decodeResource(getResources(),R.drawable.espacio4);
        // inicializa ourHolder y paint para los objetos
        ourHolder = getHolder();
        paint = new Paint();
        paint2 = new Paint();
        // Inicialice screenX y screenY porque x e y
        screenX = x;
        screenY = y;

        // inicializa la barra
        bat = new Bat(screenX, screenY);

        // crea la bola
        ball = new Ball();





        restart();

    }



    // Se ejecuta cuando el sistema operativo llama a onPause en el método BreakoutActivity
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    // Se ejecuta cuando el sistema operativo llama onResume en el método BreakoutActivity
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (playing) {



            // Capturar la hora actual en milisegundos en startFrameTime
            long startFrameTime = System.currentTimeMillis();



            if(!paused){
                update();
            }

            //dibuja el maco
            draw();

            // calcula los fps
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
                //tiempo= (timeThisFrame*1000)/60;
            }

        }
    }

    int cont1=0;

    private void update(){
        // Mover la barra
        bat.update(fps);

        // actualiza a bola

        if(cont1==1){


            ball.update(fps);

        }

        ball.update(fps);

        // Compruebe si la bola choca con un ladrillo
        for(int i = 0; i < numBricks; i++){

            if (bricks[i].getVisibility()){

                if(RectF.intersects(bricks[i].getRect(),ball.getRect())) {
                    bricks[i].setInvisible();
                    cound ++;
                    ball.reverseYVelocity();

                    switch (lives) {

                        case 1: score = score+4;
                                 break;
                        case 2: score = score+6;
                                 break;
                        case 3: score = score+10;
                                 break;

                        default: score = score+10;

                    }
                        //score = score + 10;

                }
            }
        }

        // Compruebe si la pelota choca con la barra
        if(RectF.intersects(bat.getRect(),ball.getRect())) {
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(bat.getRect().top - 2);

        }

        // devuelve la pelota cuando golpea en el fondo
        // reduce la vida
        if(ball.getRect().bottom > screenY){
            ball.reverseYVelocity();
            ball.clearObstacleY(screenY - 2);

            // pierde vidas
            lives --;


            if(lives == 0){
                paused = true;
                restart();
            }if(lives==2){
                restartjugada();
            }

        }

        // Rebota la pelota cuando golpea la parte superior de la pantalla
        if(ball.getRect().top < 0){
            ball.reverseYVelocity();
            ball.clearObstacleY(12);

        }

        // Si la pelota golpea la pared izquierda, rebota
        if(ball.getRect().left < 0){
            ball.reverseXVelocity();
            ball.clearObstacleX(2);

        }

        // Si la pelota golpea la pared derecha, rebota
        if(ball.getRect().right > screenX - 10){
            ball.reverseXVelocity();
            ball.clearObstacleX(screenX - 22);

        }

        // Pausar si la pantalla está borrada
//       if(score == numBricks * 10){
//            paused = true;
//            restart();
//        }
        switch (numblokes){

            case 0:
                if(cound == 8){
                    numblokes++;
                    paused = true;
                     cound=0;

                    restart2();;
                }

                break;
            case 1:
                if(cound == 16){

                    paused = true;
                    cound=0;
                    numblokes=0;
                    restart();

                }
                break;


        }






    }
    void restartjugada(){
        paused = true;
        // pelota en punto de arranque
        ball.reset(screenX, screenY);
        bat.reset(screenX);






    }
    void restart(){
        // pelota en punto de arranque
        ball.reset(screenX, screenY);
        //tamaño blokes
        int brickWidth = screenX / 6;
        int brickHeight = (screenY / 20)-20;

        // contructor de muro de ladrillos
        numBricks = 0;
        //corridas
        for(int column = 1; column < 5; column ++ ){
            for(int row = 3; row < 5; row ++ ){
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks ++;

            }
        }

        // resetea puntuacion y vidas
        //score2 = score;
        score = 0;
        lives = 3;

    }

    void restart2(){
        //score = score2;
        // pelota en punto de arranque
        ball.reset(screenX, screenY);
        //tamaño blokes
        int brickWidth = screenX / 6;
        int brickHeight = (screenY / 20)-20;

        // contructor de muro de ladrillos
        numBricks = 0;
        //corridas
        for(int column = 1; column < 5; column ++ ){
            for(int row = 3; row < 7; row ++ ){
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks ++;

            }
        }

        // resetea puntuacion y vidas
        //score = 0;
        lives = 3;

    }

    private void draw(){

        if (ourHolder.getSurface().isValid()) {

            canvas = ourHolder.lockCanvas();

            // color fondo
            canvas.drawColor(Color.argb(255,  26, 128, 182));
            canvas.drawBitmap(fondo,0,0,null);



            // colo barra y pelota
            paint.setColor(Color.argb(255,  255, 0, 0));
            paint2.setColor(Color.argb(255,  255, 255, 255));

            canvas.drawRect(bat.getRect(), paint);
            canvas.drawRect(ball.getRect(),paint2);


            canvas.drawBitmap(base,bat.getRect().left, bat.getRect().top, null);
            canvas.drawBitmap(pelota,ball.getRect().left-50,ball.getRect().top-70,null);






            // color blokes
            paint.setColor(Color.argb(255,  249, 129, 0));

            // ladrillos si son visibles
            for(int i = 0; i < numBricks; i++){
                if(bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].getRect(), paint);
                }
            }


            // color puntaje
            paint.setColor(Color.argb(255,  255, 255, 255));

            // tamaño puntaje
            paint.setTextSize(70);
            canvas.drawText("Puntuacion: " + score + "   Vidas: " + lives, 10,80, paint);
            //canvas.drawText("tiempo: " + tiempo, 10,150, paint);
            // mostrar
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    protected void imagenes (Canvas canvas){



    }

    // movimiento por touch
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // El jugador ha tocado la pantalla
            case MotionEvent.ACTION_DOWN:

                paused = false;

                if(motionEvent.getX() > screenX / 2){


                    bat.setMovementState(bat.RIGHT);
                }
                else{
                    if(bat.getRect().left>=20){
                        bat.setMovementState(bat.LEFT);
                    }else{


                    }

                }

                break;

            // El jugador ha eliminado el dedo de la pantalla
            case MotionEvent.ACTION_UP:
                bat.setMovementState(bat.STOPPED);
                break;
        }


        return true;
    }

}
