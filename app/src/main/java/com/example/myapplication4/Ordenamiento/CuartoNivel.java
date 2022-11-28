package com.example.myapplication4.Ordenamiento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication4.Login.Perfilusuario;
import com.example.myapplication4.R;

import java.util.ArrayList;
import java.util.Collections;

public class CuartoNivel extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuarto_nivel);

        try{
            Bundle recibido = this.getIntent().getExtras();
            final String mensaje = recibido.getString("mensaje");
        }
        catch (Exception e)
        {}

        ArrayList<Button> listado = new ArrayList<Button>();

        listado.add((Button) findViewById(R.id.but1));
        listado.add((Button) findViewById(R.id.but2));
        listado.add((Button) findViewById(R.id.but3));
        listado.add((Button) findViewById(R.id.but4));
        listado.add((Button) findViewById(R.id.but5));
        listado.add((Button) findViewById(R.id.but6));
        listado.add((Button) findViewById(R.id.but7));
        listado.add((Button) findViewById(R.id.but8));
        listado.add((Button) findViewById(R.id.but9));
        listado.add((Button) findViewById(R.id.but10));
        listado.add((Button) findViewById(R.id.but11));
        listado.add((Button) findViewById(R.id.but12));
        listado.add((Button) findViewById(R.id.but13));
        listado.add((Button) findViewById(R.id.but14));
        listado.add((Button) findViewById(R.id.but15));
        listado.add((Button) findViewById(R.id.but16));
        listado.add((Button) findViewById(R.id.but17));
        listado.add((Button) findViewById(R.id.but18));
        listado.add((Button) findViewById(R.id.but19));
        listado.add((Button) findViewById(R.id.but20));
        listado.add((Button) findViewById(R.id.but21));
        listado.add((Button) findViewById(R.id.but22));
        listado.add((Button) findViewById(R.id.but23));
        listado.add((Button) findViewById(R.id.but24));

        final TextView texto = (TextView)findViewById(R.id.texto);

        final ArrayList numeros = new ArrayList();

        for (final Button bt:listado) {
            int num = (int) (Math.random() * 24) + 1;
            numeros.add(num);
            bt.setText(num + "");
            bt.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    texto.setText(texto.getText() + " " + bt.getText());
                    bt.setVisibility(View.INVISIBLE);
                }
            });
        }
        Button validar2 =(Button)findViewById(R.id.btValidar2);
        Button automatico =(Button)findViewById(R.id.btautomatico);
        Button perfil =(Button)findViewById(R.id.btperfil);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CuartoNivel.this, Perfilusuario.class));
            }
        });

        validar2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validarContenido2(texto, numeros);}
        });
        automatico.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                automatizar(texto, numeros);}
        });

    }
    public void validarContenido2(TextView texto, ArrayList numeros){
        Collections.sort(numeros, Collections.reverseOrder());
        String cadena="";
        for (Object num: numeros){
            cadena+=(int)num+"";
        }
        String cadena2 = texto.getText().toString().replaceAll(" ","");
        String mensaje= null;

        if(cadena.equals(cadena2)){
            Intent in2 = new Intent(this,Cara.class);
            mensaje= "Ok";
            Bundle b = new Bundle();
            b.putString("mensaje",mensaje);
            in2.putExtras(b);
            startActivity(in2);
        } else {
            Intent in2 = new Intent(this,Cara.class);
            mensaje = "fail";
            Bundle b = new Bundle();
            b.putString("mensaje",mensaje);
            in2.putExtras(b);
            startActivity(in2);
        }
    }

    public void automatizar(TextView texto, ArrayList numeros){
        String mensaje;


        Collections.sort(numeros, Collections.reverseOrder());
        for (int i =0; i<numeros.size();i++){
            texto.setText(texto.getText() + " " + numeros.get(i));;
        }



    }
}