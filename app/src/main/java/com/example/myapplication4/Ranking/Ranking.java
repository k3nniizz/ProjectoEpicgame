package com.example.myapplication4.Ranking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

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
    List<Usuario> usuariosList;
    FirebaseAuth firebaseAuth;



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

        ObtenerTodosLosUsuarios();






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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}