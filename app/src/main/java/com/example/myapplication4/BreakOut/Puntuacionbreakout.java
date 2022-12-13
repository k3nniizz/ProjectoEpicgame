package com.example.myapplication4.BreakOut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication4.Menu.Menu5;
import com.example.myapplication4.R;
import com.example.myapplication4.Ranking.Ranking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.security.PrivateKey;
import java.util.HashMap;

public class Puntuacionbreakout extends AppCompatActivity {

    private TextView textpuntuacion2;
    private TextView textpuntuacion;
    private Button btn_menubk;
    private Button btn_rank;
    private Button btn_salirP;

    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 1;




    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference JUGADORES;
    DatabaseReference Comparador;
    int sc= 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacionbreakout);

        btn_menubk = (Button) findViewById(R.id.btn_menubk);
        btn_rank = (Button) findViewById(R.id.btn_rank);
        btn_salirP = (Button) findViewById(R.id.btn_salirbk2);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        JUGADORES = firebaseDatabase.getReference("Usuarios");
        textpuntuacion2 = (TextView) findViewById(R.id.textView2);
        //textpuntuacion = (TextView) findViewById(R.id.textPuntuacion);
        Bundle b = getIntent().getExtras();
         int score = b.getInt("Score");

        textpuntuacion2.setText(score+"  Pts");
       String Uid = firebaseAuth.getCurrentUser().getUid();
       JUGADORES.child(Uid).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot.exists()){

                sc = Integer.parseInt(snapshot.child("Score").getValue().toString());



               if(score > sc){

                   GuardarResultados("Score",score);
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



        btn_menubk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xi = new Intent(Puntuacionbreakout.this, Menu5.class);
                startActivity(xi);
                finish();
            }
        });



        btn_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xi = new Intent(Puntuacionbreakout.this, Ranking.class);
                startActivity(xi);

            }
        });

    }

    private void GuardarResultados(String key, long Score){

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(key, Score);



        JUGADORES.child(user.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Puntuacionbreakout.this, "puntuacion actualizada", Toast.LENGTH_SHORT).show();
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









//        JUGADORES.child(user.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(Puntuacionbreakout.this, "puntuacion actualizada", Toast.LENGTH_SHORT).show();
//            }
//        });




    }


