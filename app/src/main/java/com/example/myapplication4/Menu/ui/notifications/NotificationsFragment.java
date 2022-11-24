package com.example.myapplication4.Menu.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.example.myapplication4.Menu.databinding.FragmentNotificationsBinding;
import com.example.myapplication4.BreakOut.BreakoutActivity;
import com.example.myapplication4.Ordenamiento.PrimerNivel;
import com.example.myapplication4.R;
import com.example.myapplication4.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {


    private Button jugar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        jugar =root.findViewById(R.id.btn_BreakO);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"JUGAR",Toast.LENGTH_SHORT).show();
                //aqui debería enlazar a los fragment para llevar al sitio de los tres juegos
                startActivity(new Intent(getContext(), BreakoutActivity.class));
            }
        });
        return root;
    }


}