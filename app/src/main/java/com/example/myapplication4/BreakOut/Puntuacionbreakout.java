package com.example.myapplication4.BreakOut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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


    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference JUGADORES;
    DatabaseReference Comparador;




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
        final Long score = b.getLong("Score");

        infoScore();


        textpuntuacion2.setText(score+"  Pts");

        if (score > 200) {
            GuardarResultados("Score",score);
        }





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

    private void infoScore(){

        //consulta
         int pt;
        //con la siguiente consulta se va hacer que lo ordene por correo //se puede ordenar por puntuacion


    }






//        JUGADORES.child(user.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(Puntuacionbreakout.this, "puntuacion actualizada", Toast.LENGTH_SHORT).show();
//            }
//        });




    }


