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

import com.example.myapplication4.Menu.ui.dashboard.DashboardViewModel;
import com.example.myapplication4.Ordenamiento.PrimerNivel;
import com.example.myapplication4.R;


public class OrdeamientoFragment extends Fragment {

    private Button btnjugarO;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);



        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        btnjugarO =root.findViewById(R.id.Btn_Or);

        btnjugarO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"JUGAR",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getContext(), PrimerNivel.class));
            }
        });
        return root;
}}