package com.example.myapplication4.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class guardarperfil extends AppCompatActivity {


    //para cerrar cession y mostrar perfil
    private Button BtnCerrar;
    private FirebaseAuth mAuth;
    private TextView mtxtnombre;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardarperfil);

        mAuth= FirebaseAuth.getInstance();
        BtnCerrar=(Button) findViewById(R.id.btnCerrar_perfil);

        mtxtnombre=(TextView)findViewById(R.id.textnombre);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        BtnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(guardarperfil.this,login.class));
                finish();
            }
        });

        mostrarinfousuario();

    }

    private void mostrarinfousuario(){
        String id=mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre= snapshot.child("Nombre").getValue().toString();
                    //se puede mostrar el correo, pero hay que incluir otro TextView en activity_guardarperfil

                    mtxtnombre.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}