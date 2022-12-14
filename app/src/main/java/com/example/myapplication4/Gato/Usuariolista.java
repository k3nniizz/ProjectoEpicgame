package com.example.myapplication4.Gato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Usuariolista extends AppCompatActivity {
    //instanciar la base de datos
    //lista desplegable
    private TextView TextViewcuenta;
    Spinner SpinnerCuentas;
    Button btnusuarios;
    //conexión a realtime de database
    DatabaseReference mDatabase;
    String UsuariosSeleccionado = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuariolista);

        //instanciar lista desplegable
        TextViewcuenta = findViewById(R.id.TextViewcuenta1);
        SpinnerCuentas = (Spinner) findViewById(R.id.SpinnerCuentas2);//desplegable

        btnusuarios =findViewById(R.id.btn_gato);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        cargarUsuarios();//se carga el metodo


    }

    public void cargarUsuarios() {
        //se crea una lista mediante la clase Usaurios.java
        List<Usuarios> Usuarioslist = new ArrayList<>();
        mDatabase.child("Usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //hacer la validación
                if (snapshot.exists()) {
                    for (DataSnapshot almacenado : snapshot.getChildren()) {
                        String id = almacenado.getKey();
                        String correo = almacenado.child("Email").getValue().toString();
                        Usuarioslist.add(new Usuarios(id, correo));

                    }
                    ArrayAdapter<Usuarios> arrayAdapter = new ArrayAdapter<>(Usuariolista.this, android.R.layout.simple_dropdown_item_1line, Usuarioslist);
                    SpinnerCuentas.setAdapter(arrayAdapter);
                    SpinnerCuentas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            UsuariosSeleccionado = parent.getItemAtPosition(position).toString();
                            TextViewcuenta.setText("Correo:" + UsuariosSeleccionado);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}