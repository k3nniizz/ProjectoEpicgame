package com.example.myapplication4.Ranking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.myapplication4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Ranking extends AppCompatActivity {

    LinearLayoutManager mlayoutManager;
    RecyclerView recyclerViewUsuarios;
    Adaptador adaptador;
    Adaptador2 adaptador2;
    Adaptador3 adaptador3;
    List<Usuario> usuariosList;
    FirebaseAuth firebaseAuth;
    private Button Rgato;
    private Button Ror;
    private Button Rbk;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Top");

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mlayoutManager = new LinearLayoutManager(this);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerViewUsuarios = findViewById(R.id.recyclerViewUsuarios);

        mlayoutManager.setReverseLayout(true);
        mlayoutManager.setStackFromEnd(true);
        recyclerViewUsuarios.setHasFixedSize(true);
        recyclerViewUsuarios.setLayoutManager(mlayoutManager);
        usuariosList = new ArrayList<>();

        Rgato = (Button) findViewById(R.id.btn_Rgato);
        Ror = (Button) findViewById(R.id.btn_Ror);
        Rbk = (Button)findViewById(R.id.btn_Rbk);






        Rbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerTodosLosUsuarios();
            }
        });
        Rgato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerTodosLosUsuarios2();
            }
        });
        Ror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerTodosLosUsuarios3();
            }
        });






    }

    private void ObtenerTodosLosUsuarios(){

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuarios");
        ref.orderByChild("Score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuariosList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren() ) {

                    Usuario usuario = ds.getValue(Usuario.class);


//                if(!usuario.getUid().equals(fUser.getUid())){
//                    usuariosList.add(usuario);
//
//
//                }
                    usuariosList.add(usuario);

                    adaptador = new Adaptador(Ranking.this, usuariosList);
                    recyclerViewUsuarios.setAdapter(adaptador);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError datadaseError) {

            }
        });
    }

    private void ObtenerTodosLosUsuarios2(){

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuarios");
        ref.orderByChild("Score2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuariosList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren() ) {

                    Usuario usuario = ds.getValue(Usuario.class);


//                if(!usuario.getUid().equals(fUser.getUid())){
//                    usuariosList.add(usuario);
//
//
//                }
                    usuariosList.add(usuario);

                    adaptador2 = new Adaptador2(Ranking.this, usuariosList);
                    recyclerViewUsuarios.setAdapter(adaptador2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError datadaseError) {

            }
        });
    }

    private void ObtenerTodosLosUsuarios3(){

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuarios");
        ref.orderByChild("Score3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuariosList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren() ) {

                    Usuario usuario = ds.getValue(Usuario.class);


//                if(!usuario.getUid().equals(fUser.getUid())){
//                    usuariosList.add(usuario);
//
//
//                }
                    usuariosList.add(usuario);

                    adaptador3 = new Adaptador3(Ranking.this, usuariosList);
                    recyclerViewUsuarios.setAdapter(adaptador3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError datadaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}