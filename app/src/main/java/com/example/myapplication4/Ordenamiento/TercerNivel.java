package com.example.myapplication4.Ordenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication4.Login.Perfilusuario;
import com.example.myapplication4.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TercerNivel extends AppCompatActivity {

    TextView timerTextView;
    int minutes=0;
    int seconds = 0;
    MediaPlayer mp;
    int puntaje = 100;
    int acumuladorptj;
    int ptjpasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercer_nivel);
        mp = MediaPlayer.create(this, R.raw.musicabotones);
        mp.start();

        timerTextView = (TextView)findViewById(R.id.crono3);
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 1000);

        try{
            Bundle recibido = this.getIntent().getExtras();

            ptjpasa = recibido.getInt("ptjpasa");

        }
        catch (Exception e)
        {}


        ArrayList<Button> listado = new ArrayList<Button>();

        listado.add((Button) findViewById(R.id.bt1));
        listado.add((Button) findViewById(R.id.bt2));
        listado.add((Button) findViewById(R.id.bt3));
        listado.add((Button) findViewById(R.id.bt4));
        listado.add((Button) findViewById(R.id.bt5));
        listado.add((Button) findViewById(R.id.bt6));
        listado.add((Button) findViewById(R.id.bt7));
        listado.add((Button) findViewById(R.id.bt8));
        listado.add((Button) findViewById(R.id.bt9));
        listado.add((Button) findViewById(R.id.bt10));
        listado.add((Button) findViewById(R.id.bt11));
        listado.add((Button) findViewById(R.id.bt12));

        final TextView texto = (TextView)findViewById(R.id.texto);
        final TextView ptjrecibido = (TextView)findViewById(R.id.ptjrecibido);
        ptjrecibido.setText("" + ptjpasa);

        final ArrayList numeros = new ArrayList();

        for (final Button bt:listado) {
            int num = (int) (Math.random() * 12) + 1;
            numeros.add(num);
            bt.setText(num + "");
            bt.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    texto.setText(texto.getText() + " " + bt.getText());
                    bt.setVisibility(View.INVISIBLE);
                }
            });
        }
        Button validar =(Button)findViewById(R.id.btValidar);
        Button automatico =(Button)findViewById(R.id.btautomatico);
        Button perfil =(Button)findViewById(R.id.btperfil);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TercerNivel.this, Perfilusuario.class));
            }
        });

        validar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validarContenido(texto, numeros);}
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

            final TextView ptj = (TextView)findViewById(R.id.puntajemuestra);
            ptj.setText(Integer.toString(puntaje));

            seconds++;

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

    public void validarContenido(TextView texto, ArrayList numeros){
        Collections.sort(numeros, Collections.reverseOrder());
        String cadena="";
        for (Object num: numeros){
            cadena+=(int)num+"";
        }
        String cadena2 = texto.getText().toString().replaceAll(" ","");
        String mensaje;
        mp.release();
        acumuladorptj = puntaje+ptjpasa;


        if(cadena.equals(cadena2)){
            mensaje= "Ok";
            Intent in = new Intent(this,CuartoNivel.class);
            Bundle b = new Bundle();
            b.putInt("ptjpasa",acumuladorptj);
            b.putString("mensaje",mensaje);
            in.putExtras(b);
            startActivity(in);
        } else {

            mensaje = "fail";

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