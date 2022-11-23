package com.example.myapplication4.BreakOut;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class BreakoutActivity extends Activity {

    BreakoutEngine breakoutEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtener un objeto Display para acceder a los detalles de la pantalla
        Display display = getWindowManager().getDefaultDisplay();
        // carga la resolucion en un objeto point
        Point size = new Point();
        display.getSize(size);



//         Inicializar gameView y establecerlo como la vista
        breakoutEngine = new BreakoutEngine(this, size.x, size.y);
        setContentView(breakoutEngine);
//        setContentView(R.layout.activity_breakout);

    }

    // Este método se ejecuta cuando el jugador inicia el juego
    @Override
    protected void onResume() {
        super.onResume();

        // Indicar al método gameView resume que se ejecute
        breakoutEngine.resume();
    }

    // Este método se ejecuta cuando el jugador sale del juego
//    @Override
//    protected void onPause() {
//        super.onPause();
//        // Indicar al método gameView pause que ejecute
//
//        breakoutEngine.pause();
//    }
}