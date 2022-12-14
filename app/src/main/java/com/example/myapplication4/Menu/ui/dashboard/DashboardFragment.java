package com.example.myapplication4.Menu.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.myapplication4.Gato.Usuariolista;
import com.example.myapplication4.Gato.Usuarios;
import com.example.myapplication4.Ordenamiento.PrimerNivel;
import com.example.myapplication4.R;
import com.example.myapplication4.databinding.FragmentDashboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    //lista desplegable
    TextView TextViewcuenta;
    Spinner SpinnerCuentas;

    //coneción a realtime de database
    DatabaseReference mDatabase;
    String UsuariosSeleccionado="";


    private FragmentDashboardBinding binding;
    private Button btnjugarO;
    private Button btnusuarios;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

       // binding = FragmentDashboardBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();

        View root = inflater.inflate(R.layout.fragment_gato, container, false);


        btnjugarO =root.findViewById(R.id.btn_gato);
        //btnusuarios =root.findViewById(R.id.btn_usuarios);

        //instanciar lista desplegable
      //  TextViewcuenta=root.findViewById(R.id.TextViewcuenta);
       // SpinnerCuentas=root.findViewById(R.id.SpinnerCuentas);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        cargarUsuarios();//se carga el metodo




        btnusuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(getContext(), Usuariolista.class));
                //aqui debería enlazar a los fragment para llevar al sitio de los tres juegos

            }
        });




        btnjugarO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"JUGAR",Toast.LENGTH_SHORT).show();
                //aqui debería enlazar a los fragment para llevar al sitio de los tres juegos
                startActivity(new Intent(getContext(), PrimerNivel.class));
            }
        });

        return root;




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
                    ArrayAdapter <Usuarios> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_dropdown_item_1line, Usuarioslist);
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

// ArrayAdapter <Usuarios> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_dropdown_item_1line, Usuarioslist);