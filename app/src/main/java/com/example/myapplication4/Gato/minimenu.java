package com.example.myapplication4.Gato;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.myapplication4.databinding.ActivityMinimenuBinding;
import com.example.myapplication4.R;

public class minimenu extends AppCompatActivity {


    VideoView fondoVideo;
    MediaPlayer mMediaplayer;
    int mCurrentVideoPosition;
    Button btn1 , btn2 , btn3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minimenu);


        btn1 = (Button)findViewById(R.id.reintentarG);
        btn2 = (Button)findViewById(R.id.perfilG);
        btn3 = (Button)findViewById(R.id.salirG1);



        fondoVideo = findViewById(R.id.fondovideo);
        Uri uri = Uri.parse("android.resource://"+ getPackageName()+"/"+ R.raw.olaola);

        fondoVideo.setVideoURI(uri);
        fondoVideo.start();
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



}






