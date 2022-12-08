package com.example.myapplication4.Ranking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador3 extends RecyclerView.Adapter<Adaptador3.MyHolder>{

    private Context context;
    private List <Usuario> usuarioList;

    public Adaptador3(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
    }

    @NonNull
    @Override
    // inflar el diseño
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jugadores,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {

        String  Imagen = usuarioList.get(i).getImagen();
        String Nombres = usuarioList.get(i).getNombre();
        String Correo = usuarioList.get(i).getEmail();
        int Score3 = usuarioList.get(i).getScore3();

        String Z = String.valueOf(Score3);

        holder.NombreJugador.setText(Nombres);
        holder.CorreoJugador.setText(Correo);
        holder.PuntajeJugador.setText(Z);

        try {
            Picasso.get().load(Imagen).into(holder.ImagenJugador);

        }catch (Exception e){

        }



    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder{

        CircleImageView ImagenJugador;
        TextView NombreJugador, CorreoJugador, PuntajeJugador;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            ImagenJugador = itemView.findViewById(R.id.ImageJugador);
            NombreJugador= itemView.findViewById(R.id.NombreJugador);
            CorreoJugador= itemView.findViewById(R.id.CorreoJugador);
            PuntajeJugador = itemView.findViewById(R.id.PuntajeJugador);




        }
    }

}