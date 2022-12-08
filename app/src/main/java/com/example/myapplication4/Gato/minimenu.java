package com.example.myapplication4.Gato;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.myapplication4.Login.Perfilusuario;
import com.example.myapplication4.Menu.Menu5;
import com.example.myapplication4.R;

public class minimenu extends AppCompatActivity {


    VideoView fondoVideo;
    MediaPlayer mMediaplayer;
    int mCurrentVideoPosition;
    Button btn1 , btn2 , btn3;
    int acumulador3;

    TextView pts3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minimenu);


        btn1 = (Button)findViewById(R.id.reintentarG);
        btn2 = (Button)findViewById(R.id.perfilG);
        btn3 = (Button)findViewById(R.id.salirG1);

        pts3 = (TextView) findViewById(R.id.puntusminimeno);

        Bundle c = getIntent().getExtras();
        acumulador3 = c.getInt("pts");

        fondoVideo = findViewById(R.id.fondovideo);
        Uri uri = Uri.parse("android.resource://"+ getPackageName()+"/"+ R.raw.olaola);

        fondoVideo.setVideoURI(uri);
        fondoVideo.start();

        pts3.setText(""+acumulador3);

        fondoVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer)
            {
                mMediaplayer = mediaPlayer;
                mMediaplayer.setLooping(true);

                if (mCurrentVideoPosition != 0){

                    mMediaplayer.seekTo(mCurrentVideoPosition);
                    mMediaplayer.start();
                }
            }
        });


    }


    @Override
    protected void onPause(){


        super.onPause();

        mCurrentVideoPosition = mMediaplayer.getCurrentPosition();
        fondoVideo.pause();


    }

    @Override
    protected void onResume(){

        super.onResume();
        fondoVideo.start();
    }
    @Override
    protected void onDestroy(){

        super.onDestroy();
        mMediaplayer.release();
        mMediaplayer = null;





    }


    public void Reintentar(View view) {
        Intent reintentarnivel = new Intent(this , Gato.class);
        startActivity(reintentarnivel);
    }
    public void Perfilgato(View view) {
        Intent reintentarnivel = new Intent(this , Perfilusuario.class);
        startActivity(reintentarnivel);
    }
    public void Salirgato(View view) {
        Intent reintentarnivel = new Intent(this , Menu5.class);
        startActivity(reintentarnivel);
    }



}






