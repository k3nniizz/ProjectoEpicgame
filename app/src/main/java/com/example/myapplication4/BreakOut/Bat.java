package com.example.myapplication4.BreakOut;

import android.graphics.RectF;

public class Bat {

    // RectF es un objeto que contiene cuatro coordenadas
    private RectF rect;

    // largo de la barra
    private float length;

    // X es el extremo izquierdo del rectángulo que forma nuestra barra
    private float x;

    //  Esto mantendrá la velocidad de píxeles por segundo que la barra se movera
    private float paddleSpeed;

    // direcciones de movimiento
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;

    // direccion de movimiento de la barra
    private int paddleMoving = STOPPED;

    // contrucctor
    // parametros ancho i alto pantalla
    Bat(int screenX, int screenY){
        // 140 pixels wide and 20 pixels high
        length = 200;
        float height = 20;

        // punto de inico barra
        x = (screenX / 2)-100;

        // Y cordenada superior barra
        float y = screenY - 20;

        rect = new RectF(x, y, x + length, y + height);

        // velocidad de la barra
        paddleSpeed = 1000;
    }

    // metodo devuelve la posicion de la barra
    RectF getRect(){
        return rect;
    }

    // Este método se utilizará para cambiar si la paleta va a la izquierda, a la derecha o a ninguna parte.
    void setMovementState(int state){
        paddleMoving = state;
    }

    // actualiza hacia donde se mueve la barra
    void update(long fps){
        if(paddleMoving == LEFT){
            x = x - paddleSpeed / fps;
        }

        if(paddleMoving == RIGHT){
            x = x + paddleSpeed / fps;
        }

        rect.left = x;
        rect.right = x + length;
    }
    void reset(int x){
        rect.left = (x / 2)-100;
        rect.right = (x / 2)+100;

    }
}
