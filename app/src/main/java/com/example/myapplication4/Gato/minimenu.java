package com.example.myapplication4.Gato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.myapplication4.Login.Perfilusuario;
import com.example.myapplication4.Menu.Menu5;
import com.example.myapplication4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class minimenu extends AppCompatActivity {

    //base de datos
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;

    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 3;



    //colocar conexión a firebase
    VideoView fondoVideo;
    MediaPlayer mMediaplayer;
    int mCurrentVideoPosition;
    Button btn1 , btn2 , btn3;
    int acumulador3;
    ImageView gameover1;

    TextView pts3;

//acumulador 3 enviarlo al firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minimenu);

        //base de datos

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();


        //se inicializa la base de datos con su nombre raiz
        firebaseDatabase=FirebaseDatabase.getInstance();
        /*JUGADORES*/
        mDatabase=firebaseDatabase.getReference("Usuarios");


        btn1 = (Button)findViewById(R.id.reintentarG);
        btn2 = (Button)findViewById(R.id.perfilG);
        btn3 = (Button)findViewById(R.id.salirG1);

        //imagen

        pts3 = (TextView) findViewById(R.id.puntusminimeno);
        gameover1 = (ImageView)findViewById(R.id.gameover1);
        gameover1.setVisibility(View.VISIBLE);


        Bundle c = getIntent().getExtras();//
        acumulador3 = c.getInt("pts");

        //codigo del score
        String Uid = mAuth.getCurrentUser().getUid();
        mDatabase.child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    int sc = Integer.parseInt(snapshot.child("Score").getValue().toString());



                    if(acumulador3 > sc){

                        GuardarResultados("Score2",acumulador3);
                        notificacion1();
                        createNotificationChannel();
                    }
                }
                // textpuntuacion2.setText(sc+"  Pts");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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

    private void GuardarResultados(String key, long Score){

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(key, Score);



        mDatabase.child(user.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(minimenu.this, "puntuacion actualizada", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @SuppressLint("")
    public void salir (View view){
        finishAffinity();

    }

    private void notificacion1(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.eg2);
        builder.setContentTitle("Epicgame");
        builder.setContentText(" Has superado tu puntaje");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());

    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NOtificacion";
            NotificationChannel notificacionChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificacionChannel);
        }}



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






