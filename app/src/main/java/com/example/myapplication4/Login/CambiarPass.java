package com.example.myapplication4.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication4.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class CambiarPass extends AppCompatActivity {

    EditText ActualPass, NuevoPass;
    Button CambiarPass;
    DatabaseReference BASEDEDATOS;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Volver");

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ActualPass=findViewById(R.id.ActualPass);
        NuevoPass=findViewById(R.id.NuevaPass);
        CambiarPass=findViewById(R.id.CambiarPass);

        BASEDEDATOS= FirebaseDatabase.getInstance().getReference("Usuarios");
        firebaseAuth=FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();


        CambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ACTUAL=ActualPass.getText().toString().trim();
                String NUEVA=NuevoPass.getText().toString().trim();

                if(TextUtils.isEmpty(ACTUAL)) {
                    Toast.makeText(CambiarPass.this, "Llenar campos contraseña actual", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(NUEVA)){
                    Toast.makeText(CambiarPass.this, "Llenar campos nueva contraseña", Toast.LENGTH_SHORT).show();

                }
                if(!TextUtils.isEmpty(ACTUAL)&&!TextUtils.isEmpty(NUEVA)&& ActualPass.length()>=6 &&NUEVA.length()>=6){
                    CambioDePassJugador(ACTUAL,NUEVA);

                }
            }
        });



    }

    private void CambioDePassJugador(String actual, String nueva) {

        AuthCredential authCredential= EmailAuthProvider.getCredential(user.getEmail(),actual);

        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        user.updatePassword(nueva)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        String value=NuevoPass.getText().toString().trim();
                                        HashMap<String, Object> result=new HashMap<>();
                                        result.put("Contraseña",value);

                                        BASEDEDATOS.child(user.getUid()).updateChildren(result)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {


                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(CambiarPass.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                        firebaseAuth.signOut();
                                        startActivity(new Intent(CambiarPass.this,login.class));
                                        Toast.makeText(CambiarPass.this,"Se ha cerrado sesión ",Toast.LENGTH_SHORT).show();
                                        Toast.makeText(CambiarPass.this,"Se ha cambiado la contraseña correctamente ",Toast.LENGTH_SHORT).show();
                                        finish();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CambiarPass.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CambiarPass.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}