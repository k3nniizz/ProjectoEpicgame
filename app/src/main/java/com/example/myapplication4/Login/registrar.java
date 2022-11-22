package com.example.myapplication4.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import com.google.firebase.auth.FirebaseUser;

public class registrar extends AppCompatActivity {

    //forma parte de la barra desplegable
    String[] items={"Argentina","Brasil","Bolivia","Chile","Colombia","Ecuador","Perú","Uruguay","Venezuela"};

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    ArrayAdapter<String> adapterItems;

    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private AutoCompleteTextView autoCompletar_pais;
    private EditText mEditTextEdad;
    private EditText mEditTextAlias;

    private TextView btnRegistrar;
    private Button btncerrarlogin;


    //variables de los datos que vamos a registrar
    private String nombre = "";
    private String email = "";
    private String contrasena = "";
    private String edad= "";
    private String alias="";
    private String pais= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //llamada al archivo xml
        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mEditTextEdad=(EditText) findViewById(R.id.editTextedad);
        mEditTextAlias=(EditText) findViewById(R.id.editTextAlias);
        autoCompletar_pais= (AutoCompleteTextView) findViewById(R.id.autoCompletePais);


        //codigo sobre barra desplegable

        adapterItems=new ArrayAdapter<String>(this,R.layout.list_item,items);

        autoCompletar_pais.setAdapter(adapterItems);

        autoCompletar_pais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item"+items,Toast.LENGTH_SHORT).show();
            }
        });


        btnRegistrar = (Button) findViewById(R.id.btnRegistrarId);
        btncerrarlogin = (Button) findViewById(R.id.btnCerrarloginId);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=mEditTextName.getText().toString();
                email=mEditTextEmail.getText().toString();
                contrasena=mEditTextPassword.getText().toString();
                pais=autoCompletar_pais.getText().toString();
                edad=mEditTextEdad.getText().toString();
                alias=mEditTextAlias.getText().toString();

                if(!nombre.isEmpty() && !email.isEmpty() && !contrasena.isEmpty() && !pais.isEmpty() && !edad.isEmpty() && !alias.isEmpty()){
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

                    FirebaseUser user = mAuth.getCurrentUser();
                    int contador=0;
                    assert user !=null;

                    String uidString= user.getUid(); //uid se genera automaticamente (id)


                    Map<String, Object> map = new HashMap<>();
                    map.put("Uid",uidString);
                    map.put("Nombre",nombre);
                    map.put("Email", email);
                    map.put("Contraseña", contrasena);
                    map.put("Edad",edad);
                    map.put("Alias",alias);
                    map.put("Pais",pais);
                    map.put("Tiempo",contador);


                    Toast.makeText(registrar.this,"USUARIO REGISTRADO EXITOSAMENTE",Toast.LENGTH_SHORT).show();

                    // String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Usuarios").child(uidString).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                startActivity(new Intent(registrar.this, numeros.class)); //en esta parte se redirige en tal parte cuando se registrar
                                finish();
                            } else {
                                Toast.makeText(registrar.this, "No se crearon los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
//
                        }
                    });
                } else {
                    Toast.makeText(registrar.this, "no se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    }
