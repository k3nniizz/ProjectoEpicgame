package com.example.myapplication4.Login;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication4.R;

import java.util.ArrayList;
import java.util.Collections;

public class numeros extends AppCompatActivity {
    //variables del tiempo
    Chronometer crono;
    Button iniciar,terminar;
    TextView funcion;
    String mensaje;

    long Time=0;
    String currentTime="";
    Boolean activity=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeros);




        int numeroBoton = (int) (Math.random() * 12 + 1);

        final TextView texto = (TextView) findViewById(R.id.texto);

        final ArrayList numeros = new ArrayList();
        ArrayList<Button> listabotones = new ArrayList<Button>();
        listabotones.add((Button) findViewById(R.id.btn1));
        listabotones.add((Button) findViewById(R.id.btn2));
        listabotones.add((Button) findViewById(R.id.btn3));
        listabotones.add((Button) findViewById(R.id.btn4));
        listabotones.add((Button) findViewById(R.id.btn5));
        listabotones.add((Button) findViewById(R.id.btn6));
        listabotones.add((Button) findViewById(R.id.btn7));
        listabotones.add((Button) findViewById(R.id.btn8));
        listabotones.add((Button) findViewById(R.id.btn9));
        listabotones.add((Button) findViewById(R.id.btn10));
        listabotones.add((Button) findViewById(R.id.btn11));
        listabotones.add((Button) findViewById(R.id.btn12));


        //area del codigo del tiempo
        crono=(Chronometer) findViewById(R.id.chronometer1);
        iniciar=(Button) findViewById(R.id.btniniciar); //contar=iniciar

        iniciar.setEnabled(true);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciar.setEnabled(false);
                crono.setBase(SystemClock.elapsedRealtime());


            }

        });


        for (final Button btn : listabotones) {
            int num = (int) (Math.random() * 12) + 1;
            numeros.add(num);
            btn.setText(num + "");
            //empieza el tiempo de inmediato y el boton
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    texto.setText(texto.getText() + " " + btn.getText()); //

                    //aqui tambien
                    btn.setVisibility(View.INVISIBLE);
                    //hasta que aprete un numero empieza el tiempo, pero muestra el tiempo de cuanto se demoró en apretar el boton

                }

            });
            //boton star una vez abierto el juego empieza el tiempo

        }


        Button validar = (Button) findViewById(R.id.btnValidar);
        validar.setOnClickListener(new View.OnClickListener() { //al hacer clik realiza tal accion

            @Override
            public void onClick(View view) {

                validarContenido(texto, numeros);

            }


        });

    }

    private void validarContenido(TextView texto, ArrayList numeros){ //cuando apreta los botones se va agregando el texto y el numero

        Collections.sort(numeros); //ordena los numeros
        String cadena=""; //crea una nueva cadena

        for(Object num: numeros) {
            cadena += (int) num + "";  //aqui los numero se irán agregando a la cadena, el + que tu numero lo va a transformar en un string

        }

        String cadena2 =texto.getText().toString().replaceAll(" ",""); //el replace busca todos los espacio y los borra.




        if(cadena.equals(cadena2)) { //cadena(ordenada) equals(es igual) a cadena2 //si los dos array son iguales, el mensaje dirá que ganó/"OK"
            // Button btnValidar= (Button) findViewById(R.id.btnValidar);

            //final TextView texto2 = (TextView) findViewById(R.id.texto);
            texto.setText("ganaste");
           /* Bundle b = new Bundle();
            b.putString("Mensaje",mensaje);*/


            //para pasar los datos

               /* b.putString("seconds", String.valueOf(seconds));  //sirve para enviarle el dato a la otra ventana
                b.putString("minutes", String.valueOf(minutes));
                b.putString("minutes", String.valueOf(hours));*/


        }else
        {

            texto.setText("perdiste");
            finish(); //
            startActivity(getIntent());

        }

    }



}