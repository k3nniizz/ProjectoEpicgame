package com.example.myapplication4.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication4.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreatePetActivity extends AppCompatActivity {
    Button btn_add;
    EditText name, edad, color;
    private FirebaseFirestore mfirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);



        this.setTitle("Crear mascota");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mfirestore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.nombre);
        edad = findViewById(R.id.edad);
        color = findViewById(R.id.color);
        btn_add = findViewById(R.id.btn_add2);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String np = name.getText().toString().trim();
                String ep = edad.getText().toString().trim();
                String cp = color.getText().toString().trim();

                if(np.isEmpty() && ep.isEmpty() && cp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresa los datos", Toast.LENGTH_SHORT).show();

                }else{
                    postPet(np,ep,cp);

                }
            }
        });

    }

    private void postPet(String np, String ep, String cp) {
        Map<String, Object> map = new HashMap<>();
        map.put("name",np);
        map.put("edad",ep);
        map.put("color",cp);

        mfirestore.collection("pet").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Creado exitosamente",Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"error al ingresar",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}