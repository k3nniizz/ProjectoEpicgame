package com.example.myapplication4.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registrar extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditTextPais;
    private EditText mEditTextEdad;
    private TextView btnRegistrar;
    private Button btncerrarlogin;


    //variables de los datos que vamos a registrar
    private String nombre = "";
    private String email = "";
    private String contrasena = "";
    private String pais= "";
    private String edad= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mEditTextPais=(EditText) findViewById(R.id.editTextpais);
        mEditTextEdad=(EditText) findViewById(R.id.editTextedad);




        btnRegistrar = (Button) findViewById(R.id.btnRegistrarId);
        btncerrarlogin = (Button) findViewById(R.id.btnCerrarloginId);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=mEditTextName.getText().toString();
                email=mEditTextEmail.getText().toString();
                contrasena=mEditTextPassword.getText().toString();
                pais=mEditTextPais.getText().toString();
                edad=mEditTextEdad.getText().toString();

                if(!nombre.isEmpty() && !email.isEmpty() && !contrasena.isEmpty() && !pais.isEmpty() && !edad.isEmpty()){
                    if(contrasena.length()>=6){
                        registerUser();
                    }else{
                        Toast.makeText(registrar.this,"La contraseña debe tener al menos 6 caracteres",Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    Toast.makeText(registrar.this,"Debe completar los campos",Toast.LENGTH_SHORT).show();
                }


            }
        });


        btncerrarlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registrar.this,login.class));

            }
        });



    }


    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(email, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Map<String, Object> map = new HashMap<>();
                    map.put("Nombre",nombre);
                    map.put("Email", email);
                    map.put("Contraseña", contrasena);
                    map.put("Pais",pais);
                    map.put("Edad",edad);


                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                startActivity(new Intent(registrar.this, numeros.class)); //en esta parte se redirige en tal parte cuando se registrar
                                finish();
                            } else {
                                Toast.makeText(registrar.this, "No se crearon los datos correctamente", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(registrar.this, "no se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //quedarse con el perfil abierto una vez registrado
}