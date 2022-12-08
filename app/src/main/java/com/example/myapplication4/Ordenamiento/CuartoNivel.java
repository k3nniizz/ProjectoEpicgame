package com.example.myapplication4.Ordenamiento;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication4.Login.Perfilusuario;
import com.example.myapplication4.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class CuartoNivel extends AppCompatActivity {

    TextView timerTextView;
    int minutes=0;
    int seconds = 0;
    MediaPlayer mp;
    int puntaje = 100;
    int acumuladorptj;
    int ptjpasa;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuarto_nivel);
        mp = MediaPlayer.create(this, R.raw.musicabotones);
        mp.start();


        timerTextView = (TextView)findViewById(R.id.crono4);
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 1000);

        try{
            Bundle recibido = this.getIntent().getExtras();
            final String mensaje = recibido.getString("mensaje");
            ptjpasa = recibido.getInt("ptjpasa");
        }
        catch (Exception e)
        {}

        ArrayList<Button> listado = new ArrayList<Button>();

        listado.add((Button) findViewById(R.id.but1));
        listado.add((Button) findViewById(R.id.but2));
        listado.add((Button) findViewById(R.id.but3));
        listado.add((Button) findViewById(R.id.but4));
        listado.add((Button) findViewById(R.id.but5));
        listado.add((Button) findViewById(R.id.but6));
        listado.add((Button) findViewById(R.id.but7));
        listado.add((Button) findViewById(R.id.but8));
        listado.add((Button) findViewById(R.id.but9));
        listado.add((Button) findViewById(R.id.but10));
        listado.add((Button) findViewById(R.id.but11));
        listado.add((Button) findViewById(R.id.but12));
        listado.add((Button) findViewById(R.id.but13));
        listado.add((Button) findViewById(R.id.but14));
        listado.add((Button) findViewById(R.id.but15));
        listado.add((Button) findViewById(R.id.but16));
        listado.add((Button) findViewById(R.id.but17));
        listado.add((Button) findViewById(R.id.but18));
        listado.add((Button) findViewById(R.id.but19));
        listado.add((Button) findViewById(R.id.but20));
        listado.add((Button) findViewById(R.id.but21));
        listado.add((Button) findViewById(R.id.but22));
        listado.add((Button) findViewById(R.id.but23));
        listado.add((Button) findViewById(R.id.but24));

        final TextView texto = (TextView)findViewById(R.id.texto);
        final TextView ptjrecibido = (TextView)findViewById(R.id.ptjrecibido);
        ptjrecibido.setText("" + ptjpasa);

        final ArrayList numeros = new ArrayList();

        for (final Button bt:listado) {
            int num = (int) (Math.random() * 24) + 1;
            numeros.add(num);
            bt.setText(num + "");
            bt.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    texto.setText(texto.getText() + " " + bt.getText());
                    bt.setVisibility(View.INVISIBLE);
                }
            });
        }
        Button validar2 =(Button)findViewById(R.id.btValidar2);
        Button automatico =(Button)findViewById(R.id.btautomatico);
        Button perfil =(Button)findViewById(R.id.btperfil);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CuartoNivel.this, Perfilusuario.class));
            }
        });

        validar2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validarContenido2(texto, numeros);}
        });
        automatico.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                automatizar(texto, numeros);}
        });

    }

    private void TimerMethod()
    {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {

        public void run() {


            seconds++;


            final TextView ptj = (TextView)findViewById(R.id.puntajemuestra);
            ptj.setText(Integer.toString(puntaje));


            if (seconds == 0)
                timerTextView.setVisibility(View.VISIBLE);

            if(seconds==9 && minutes==0)
            {
                puntaje=puntaje-10;
            }
            if(seconds==19 && minutes==0)
            {
                puntaje=puntaje-10;
            }
            if(seconds==29 && minutes==0)
            {
                puntaje=puntaje-10;
            }
            if(seconds==39 && minutes==0)
            {
                puntaje=puntaje-10;
            }
            if(seconds==49 && minutes==0)
            {
                puntaje=puntaje-10;
            }
            if(seconds==59 && minutes==0)
            {
                puntaje=10;
            }


            if(seconds==60)
            {
                minutes++;
                seconds=0;
            }
            timerTextView.setText(String.format("%d:%d", minutes, seconds));

        }

    };
    public void validarContenido2(TextView texto, ArrayList numeros){
        Collections.sort(numeros, Collections.reverseOrder());
        String cadena="";
        for (Object num: numeros){
            cadena+=(int)num+"";
        }
        String cadena2 = texto.getText().toString().replaceAll(" ","");

        mp.release();

        acumuladorptj = puntaje+ptjpasa;

        if(cadena.equals(cadena2)){
            Intent in2 = new Intent(this, com.example.myapplication4.Ordenamiento.puntaje.class);

            Bundle b = new Bundle();
            b.putInt("ptjpasa",acumuladorptj);

            in2.putExtras(b);
            startActivity(in2);
        } else {


            finish();
            startActivity(getIntent());
        }
    }

    public void automatizar(TextView texto, ArrayList numeros){
        String mensaje;


        Collections.sort(numeros, Collections.reverseOrder());
        for (int i =0; i<numeros.size();i++){
            texto.setText(texto.getText() + " " + numeros.get(i));;
        }



    }
    @Override
    protected void onPause() {
        super.onPause();
        mp.release();
    }
}