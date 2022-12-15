package com.example.myapplication4.Menu.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication4.Gato.Gato;
import com.example.myapplication4.Gato.Usuariolista;
import com.example.myapplication4.Menu.ui.home.HomeViewModel;
import com.example.myapplication4.R;


public class gatoFragment extends Fragment {


    private Button btnjugarO;
    private Button btninvO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        View root = inflater.inflate(R.layout.fragment_gato, container, false);
        btnjugarO =root.findViewById(R.id.btn_gato);
        btninvO = root.findViewById(R.id.btn_invitad);
        btnjugarO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"JUGAR",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getContext(), Gato.class));
            }
        });
        btninvO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Usuariolista.class));
            }
        });

        return root;
    }
}