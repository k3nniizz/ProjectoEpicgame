package com.example.myapplication4.Gato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.example.myapplication4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Gato extends AppCompatActivity {



    TextView puntajeg2;
    TextView puntajeg;
    TextView timerTextView;

    int minutes=0;
    int seconds = 0;
    int seconds2= 0;
    int seconds3= 0;
    int hours = 0;
    int puntitos = 500;
    int puntitos2 =500;
    boolean mp2 = false;

    Button play;
    Button iniciarT;
    TextView mostrarT;
    TextView textoVictoria;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gato);





        MediaPlayer mp = MediaPlayer.create(this, R.raw.doraemon);
        if(mp2==false){

            mp.start();

        }else{
            mp.release();
            mp.start();
        }


        timerTextView = (TextView)findViewById(R.id.mostrarTiempo2);
        Timer myTimer = new Timer();//inicia al colocar ficha
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 1000);




        puntajeg=(TextView) findViewById(R.id.puntajeboludin1);







        textoVictoria = (TextView) findViewById(R.id.textoVictoria);
        textoVictoria.setVisibility(View.INVISIBLE);







        botones = new Integer[]{
                R.id.b1, R.id.b2, R.id.b3,   // mi array de botones
                R.id.b4, R.id.b5, R.id.b6,
                R.id.b7, R.id.b8, R.id.b9,
        };



    }

    private void setPuntaje(){



        if (seconds2 >=0 && seconds2 <=10){

            puntitos = 500;

        }
        else if(seconds2 >=11 && seconds2 <=20) {

            puntitos = 400;



        }

        else if(seconds2 >=21 && seconds2 <=30) {

            puntitos = 300;



        }
        else if(seconds2 >=31 && seconds2 <=40) {

            puntitos = 200;


        }

        else if(seconds2 >=41 && seconds2 <=50) {

            puntitos = 100;


        }

        else if(seconds2 >=50 && seconds2 <60) {

            puntitos = 50;

        }

        else if(seconds2 >=60){

            puntitos = 0;
        }



    }



    private void TimerMethod()
    {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            seconds++;
            seconds2++;
            setPuntaje();


            if (seconds == 0)
                timerTextView.setVisibility(View.VISIBLE);


            if(seconds==60)
            {
                minutes++;
                seconds=0;
            }
            puntajeg.setText(""+puntitos);

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




            }

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

                Intent siguientenivel = new Intent(this , Segundonivell.class);
                Bundle b = new Bundle();
                b.putInt("pts", puntitos);
                siguientenivel.putExtras(b);
                startActivity(siguientenivel);


            } else {
                textoVictoria.setVisibility(View.VISIBLE);
                textoVictoria.setText("Ha ganado el Jugador_2");
                textoVictoria.setTextColor(Color.GREEN);
                Intent siguientenivel = new Intent(this , Segundonivell.class);
                Bundle b = new Bundle();
                b.putInt("pts", puntitos);
                siguientenivel.putExtras(b);
                startActivity(siguientenivel);
            }


        } else if (estado == 2) {
            textoVictoria.setVisibility(View.VISIBLE);
            textoVictoria.setText("Has empatado");
            Intent minimenu = new Intent(this , minimenu.class);
            startActivity(minimenu);
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
        else if (Math.abs(tablero[6]+tablero[7]+tablero[8])==3) {
            posGanadora = new int[]{6, 7, 8};
            nuevoEstado = 1 * turno;
            for (int i =6; i<9;i++){
                Button b = (Button) findViewById(botones[i]);
                b.setTextColor(Color.GREEN);

            }}
        else if(fichasPuestas == 9){
            nuevoEstado = 2;

        }


        return nuevoEstado;



    }


}


