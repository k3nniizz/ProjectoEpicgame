package com.example.myapplication4.Ordenamiento;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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