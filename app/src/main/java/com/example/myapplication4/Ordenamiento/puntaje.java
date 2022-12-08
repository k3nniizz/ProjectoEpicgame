package com.example.myapplication4.Ordenamiento;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication4.Menu.Menu5;
import com.example.myapplication4.R;

public class puntaje extends AppCompatActivity {
TextView puntajef;
int rec;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajeordenamiento);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.musicabotones);
        mp.stop();
        puntajef = (TextView) findViewById(R.id.puntosob);


        Bundle recibido2 = this.getIntent().getExtras();
        rec = recibido2.getInt("ptjpasa");
        puntajef.setText("Tu puntaje final fue: " + rec);



        Button finalizar =(Button)findViewById(R.id.btfin);

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                startActivity(new Intent(puntaje.this, Menu5.class));
            }
        });



    }
}