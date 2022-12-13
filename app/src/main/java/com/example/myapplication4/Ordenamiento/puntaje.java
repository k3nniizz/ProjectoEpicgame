package com.example.myapplication4.Ordenamiento;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication4.Login.Perfilusuario;
import com.example.myapplication4.Menu.Menu5;
import com.example.myapplication4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.cache.DiskLruCache;

public class puntaje extends AppCompatActivity {

    TextView puntajef;
    int rec;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference JUGADORES;
    int puntosbd;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 2;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajeordenamiento);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.musicabotones);
        mp.stop();
        puntajef = (TextView) findViewById(R.id.puntosob);
        Bundle recibido2 = this.getIntent().getExtras();
        rec = recibido2.getInt("ptjpasa");
        puntajef.setText("Tu puntaje final fue: " + rec);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        JUGADORES = firebaseDatabase.getReference("Usuarios");
        String id = user.getUid();
        JUGADORES.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    puntosbd = Integer.parseInt(snapshot.child("Score3").getValue().toString());

                    if (rec>puntosbd){

                        subirPuntaje();
                        notificacion1();
                       createNotificationChannel();


                    }


                }
            }

            private void subirPuntaje() {
                String id = user.getUid();
                JUGADORES.child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){

                            Map<String, Object> scoreMap = new HashMap<>();
                            scoreMap.put("Score3", rec);
                            JUGADORES.child(id).updateChildren(scoreMap);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            private void notificacion1(){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
                builder.setSmallIcon(R.drawable.eg2);
                builder.setContentTitle("Epicgame");
                builder.setContentText(" Has superado tu puntaje máximo en Ordenamiento");
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Button finalizar =(Button)findViewById(R.id.btfin);

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                startActivity(new Intent(puntaje.this, Perfilusuario.class));
            }
        });



    }
}