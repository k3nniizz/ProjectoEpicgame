package com.example.myapplication4.Ordenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication4.Login.Perfilusuario;
import com.example.myapplication4.R;

public class Gameover extends AppCompatActivity {

    MediaPlayer mpgameover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        mpgameover = MediaPlayer.create(this, R.raw.gameoverord);
        mpgameover.start();

        Button finalizar =(Button)findViewById(R.id.btfingame);

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gameover.this, Perfilusuario.class));
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        mpgameover.release();
    }


}