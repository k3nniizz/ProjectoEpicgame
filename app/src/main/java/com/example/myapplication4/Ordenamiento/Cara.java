package com.example.myapplication4.Ordenamiento;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication4.Login.Perfilusuario;
import com.example.myapplication4.Menu.Menu5;
import com.example.myapplication4.R;

public class Cara extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cara);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.musicabotones);
        mp.stop();


        Bundle recibido2 = this.getIntent().getExtras();
        final String mensaje2 = recibido2.getString("mensaje");

        ImageButton inimg = (ImageButton) findViewById(R.id.btnImagen);

        Button finalizar =(Button)findViewById(R.id.btfinal);

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                startActivity(new Intent(Cara.this, Menu5.class));
            }
        });


        if(mensaje2.equals("Ok")){
            inimg.setImageResource(R.drawable.feliz22);

        } else {

            inimg.setImageResource(R.drawable.triste22);

        }
    }
}