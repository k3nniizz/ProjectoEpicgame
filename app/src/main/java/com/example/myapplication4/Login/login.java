package com.example.myapplication4.Login;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication4.Menu.Menu5;
import com.example.myapplication4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText editTextCorreo, editTextPassword;
    private Button btnLogin,btninvitado;
    private TextView btnRegistrar;
    private FirebaseAuth mAuth;
    private String email = "";
    private String password = "";
    //notificaciones
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    //notifiicaciones

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextCorreo = (EditText) findViewById(R.id.email_edittext_activity_login);
        editTextPassword = (EditText) findViewById(R.id.pass_edittext_activity_login);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrarId);

        btnLogin = (Button) findViewById(R.id.btnLoginId);
        btninvitado = (Button) findViewById(R.id.btninvitado);

        mAuth = FirebaseAuth.getInstance();


        ClickRegistrar();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextCorreo.getText().toString();
                password = editTextPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {
                    loginUser();
                    //metodo notificacion
                    createNotificationChannel();
                    notificacion1();

                } else {
                    Toast.makeText(login.this, "Completa los campos", Toast.LENGTH_LONG).show();

                }


            }
        });

        btninvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(login.this, Menu5.class));

            }
        });

    }

    private void loginUser() {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(login.this, Perfilusuario.class));
                    finish();
                } else {
                    Toast.makeText(login.this, "No se pudo iniciar sesion compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void ClickRegistrar() {
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, registrar.class);
                login.this.startActivity(intent);

            }
        });
    }

    //metodo notificacion

    private void notificacion1(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_add_comment_24);
        builder.setContentTitle("Epicgame");
        builder.setContentText(editTextCorreo.getText().toString()+" se encuentra en linea");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());

    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NOtificacion";
            NotificationChannel notificacionChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificacionChannel);
        }



    }
}