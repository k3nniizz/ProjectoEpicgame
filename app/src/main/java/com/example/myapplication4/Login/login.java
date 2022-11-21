package com.example.myapplication4.Login;

import android.annotation.SuppressLint;
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

public class login extends AppCompatActivity {

    private EditText editTextCorreo, editTextPassword;
    private Button btnLogin;
    private TextView btnRegistrar;


    private FirebaseAuth mAuth;
    private String email="";
    private String password="";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextCorreo = (EditText) findViewById(R.id.email_edittext_activity_login);
        editTextPassword = (EditText) findViewById(R.id.pass_edittext_activity_login);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrarId);

        btnLogin = (Button) findViewById(R.id.btnLoginId);

        mAuth = FirebaseAuth.getInstance();


        ClickRegistrar();
//el que hace el mayor trabajo
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=editTextCorreo.getText().toString();
                password=editTextPassword.getText().toString();
                if(!email.isEmpty()&&!password.isEmpty()){
                    loginUser();

                }else{
                    Toast.makeText(login.this,"Completa los campos",Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void loginUser(){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(login.this,guardarperfil.class));
                    finish();
                }
                else{
                    Toast.makeText(login.this,"No se pudo iniciar sesion compruebe los datos",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void ClickRegistrar() {
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, registrar.class);
                login.this.startActivity(intent);

            }
        });
    }}