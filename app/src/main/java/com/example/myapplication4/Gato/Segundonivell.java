package com.example.myapplication4.Gato;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.example.myapplication4.R;

public class Segundonivell extends AppCompatActivity {


    TextView timerTextView;
    int minutes=0;
    int seconds = 60; //cambiar el tiempo del cronometro
    int hours = 0;
    Button iniciar;
    TextView textoVictoria;
    TextView mostrarT;
    int min = 0;
    int seg = 0;
    Integer[] botones;
    int[] tablero = new int[]{
            0, 0, 0,
            0, 0, 0,
            0, 0, 0


    };
    int estado = 0;
    int fichasPuestas = 0;
    int turno = 1;
    int[] posGanadora = new int[]{-1,-1,-1};
    TextView marcador0 , marcador1 , marcador2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundonivell);

        timerTextView = (TextView)findViewById(R.id.mostrarTiempo);

        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 1000);







        textoVictoria = (TextView) findViewById(R.id.textoVictoria);
        textoVictoria.setVisibility(View.INVISIBLE);

        botones = new Integer[]{
                R.id.b1, R.id.b2, R.id.b3,   // mi array de botones
                R.id.b4, R.id.b5, R.id.b6,
                R.id.b7, R.id.b8, R.id.b9,
        };

    }

    private void TimerMethod()
    {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            seconds--;
            if (seconds == 0)
                timerTextView.setVisibility(View.INVISIBLE);


            if(seconds ==0)
            {textoVictoria.setVisibility(View.VISIBLE);
                textoVictoria.setText("Has empatado");
                if (seconds==0){
                    estado = 2;


                }



            }
            if(minutes==60)
            {
                hours++;
                minutes=0;
            }
            timerTextView.setText(String.format("%d:%d", minutes, seconds));

        }

    };




    public void ponerFicha(View v) {
        int numBoton = Arrays.asList(botones).indexOf(v.getId()); // pulsar un boton , consulta la id y tambien la posicion con num boton
        System.out.println("numboton: "+numBoton);
        Button button = (Button)  findViewById(botones[numBoton]);

        if(estado == 0 && turno == 1) { //si nadie ha ganado y es turno del jugador1 (turno = 1)

            if(tablero[numBoton] == 0){ //si el boton del tablero está vacio
                button.setText("O");   //se coloca un circulo
                button.setTextColor(Color.BLACK);

                tablero[numBoton] = 1; //el valor del boton está tomado por jugador1
                fichasPuestas += 1;  //se le suma 1 al contador de fichas puestas
                estado = comprobarEstado(); //se comprueba el estado (si hay ganador o empate o se sigue jugando)
                turno = -1; //se cambia el turno al jugador2 (turno = -1)
                terminarPartida(); //si hubo ganador muestra mensaje


            }

        }
        if (estado == 0 && turno == -1) { //si nadie ha ganado y es turno del jugador2 (turno = -1)

            if(tablero[numBoton] == 0) {//si el boton del tablero está vacio
                button.setText("X"); //se coloca una X
                button.setTextColor(Color.WHITE);

                tablero[numBoton] = -1; //el valor del boton está tomado por jugador2
                fichasPuestas += 1; //se le suma 1 al contador de fichas puestas
                estado = comprobarEstado();//se comprueba el estado
                turno = 1; //se cambia el turno al jugador1 (turno = 1)
                terminarPartida();
            }
        }

    }
    public void ia() {
        Random ran = new Random();
        int pos = ran.nextInt(tablero.length);
        while(tablero[pos] !=0){
            pos = ran.nextInt(tablero.length);
        }
        Button b = (Button) findViewById(botones[pos]);
        b.setText("X");

        b.setTextColor(Color.WHITE);
        tablero[pos] = -1;

    }

    public  void terminarPartida() {
        if (estado == 1 || estado == -1) {
            if (estado == 1) {
                textoVictoria.setVisibility(View.VISIBLE);
                textoVictoria.setTextColor(Color.GREEN);
                timerTextView.setVisibility(View.INVISIBLE);


            } else {
                textoVictoria.setVisibility(View.VISIBLE);
                textoVictoria.setText("Ha ganado el Jugador_2");
                textoVictoria.setTextColor(Color.GREEN);
                timerTextView.setVisibility(View.INVISIBLE);
            }


        } else if (estado == 2) {
            textoVictoria.setVisibility(View.VISIBLE);
            textoVictoria.setText("Has empatado");
            timerTextView.setVisibility(View.INVISIBLE);
        }
    }


    public int comprobarEstado(){
        int nuevoEstado = 0;
        if(Math.abs(tablero[0]+tablero[1]+tablero[2])==3){

            posGanadora = new int[]{0,1,2};
            nuevoEstado = 1*turno;

            for (int i =0; i<3;i++){
                Button b = (Button) findViewById(botones[i]);
                b.setTextColor(Color.GREEN);

            }}
        else if (Math.abs(tablero[3]+tablero[4]+tablero[5])==3) {
            posGanadora = new int[]{3, 4, 5};
            nuevoEstado = 1 * turno;
            for (int i =3; i<6;i++){
                Button b = (Button) findViewById(botones[i]);
                b.setTextColor(Color.GREEN);

            }

        }
        else if (Math.abs(tablero[0]+tablero[3]+tablero[6])==3) {
            posGanadora = new int[]{0,3,6 };
            nuevoEstado = 1 * turno;
            for (int i =0; i<7;i=i+3){
                Button b = (Button) findViewById(botones[i]);
                b.setTextColor(Color.GREEN);

            }
        }
        else if (Math.abs(tablero[1]+tablero[4]+tablero[7])==3) {
            posGanadora = new int[]{1, 4, 7};
            nuevoEstado = 1 * turno;
            for (int i =1; i<8;i=i+3){
                Button b = (Button) findViewById(botones[i]);
                b.setTextColor(Color.GREEN);

            }
        }
        else if (Math.abs(tablero[2]+tablero[5]+tablero[8])==3) {
            posGanadora = new int[]{2, 5, 8};
            nuevoEstado = 1 * turno;
            for (int i =2; i<9;i=i+3){
                Button b = (Button) findViewById(botones[i]);
                b.setTextColor(Color.GREEN);

            }
        }
        else if (Math.abs(tablero[0]+tablero[4]+tablero[8])==3) {
            posGanadora = new int[]{0, 4, 8};
            nuevoEstado = 1 * turno;
            for (int i =0; i<9;i=i+4){
                Button b = (Button) findViewById(botones[i]);
                b.setTextColor(Color.GREEN);

            }
        }
        else if (Math.abs(tablero[2]+tablero[4]+tablero[6])==3) {
            posGanadora = new int[]{2, 4, 6};
            nuevoEstado = 1 * turno;
            for (int i =0; i<7;i=i+2){
                Button b = (Button) findViewById(botones[i]);
                b.setTextColor(Color.GREEN);

            }
        }
        else if(fichasPuestas == 9){
            nuevoEstado = 2;

        }


        return nuevoEstado;

    }


}




