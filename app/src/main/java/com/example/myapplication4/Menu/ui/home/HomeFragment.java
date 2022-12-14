package com.example.myapplication4.Menu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.example.myapplication4.Menu.databinding.FragmentHomeBinding;
import com.example.myapplication4.Gato.Gato;
import com.example.myapplication4.R;
import com.example.myapplication4.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button btnjugarO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        View root = inflater.inflate(R.layout.fragment_ordeamiento, container, false);
        btnjugarO =root.findViewById(R.id.Btn_Or);
        btnjugarO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"JUGAR",Toast.LENGTH_SHORT).show();
                //aqui debería enlazar a los fragment para llevar al sitio de los tres juegos
                startActivity(new Intent(getContext(), Gato.class));
            }
        });
        return root;
    }




}