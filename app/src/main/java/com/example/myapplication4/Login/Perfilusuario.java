package com.example.myapplication4.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication4.Menu.Menu5;
import com.example.myapplication4.Ordenamiento.PrimerNivel;
import com.example.myapplication4.R;
import com.example.myapplication4.Ranking.Ranking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.net.URI;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class Perfilusuario extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;

    //Para que salga un dialogo (Button AcercaDe)-->INFO de quién realizó la app
    Dialog dialog;



    private StorageReference ReferenciaDeAlmacenamiento;
    private String RutaAlmacenamiento="FotosDePerfil/*"; //Aqui se crea la carpeta en STORAGE, puede ser cualquier nombre

    //Permisos
    private static final int CODIGO_SOLICITUD_ALMACENAMIENTO=200;
    private static final int CODIGO_SELECCION_IMAGEN=300;

    //matrices
    private String []Permisosdealmacenamiento;
    private Uri imagen_uri;
    private String perfil; //sirve como parametro para cambiar imagen

    Button Jugarbtn, Putuacionesbtn,AcercaDebtn,cerrarsesion,Editarbtn,CambiarPassbtn;
    TextView Mipuntuaciontxt,Correo,Nombreusuario,Menutxt,uid,Alias,puntuaciontxt;
    CircleImageView imagenPerfil;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilusuario);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();


        //se inicializa la base de datos con su nombre raiz
        firebaseDatabase=FirebaseDatabase.getInstance();
        /*JUGADORES*/
        mDatabase=firebaseDatabase.getReference("Usuarios");

        //la clase donde se va hacer el llamado
        dialog=new Dialog(Perfilusuario.this);

        //Se inicializa el storage de la base de datos
        ReferenciaDeAlmacenamiento=FirebaseStorage.getInstance().getReference();
        //Se inicializa los permisos de almacenamiento
        Permisosdealmacenamiento=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};




        //imagen xml
        imagenPerfil =findViewById(R.id.imageperfil);
        //TextView llamando a xml
        Mipuntuaciontxt=findViewById(R.id.Mipuntuaciontxt);
        Nombreusuario=findViewById(R.id.Nombreusuario);
        Menutxt=findViewById(R.id.Menutxt);
        Correo=findViewById(R.id.Correo);
       // uid=findViewById(R.id.uid);
        Alias=findViewById(R.id.Alias);
        puntuaciontxt=findViewById(R.id.puntuacion);//lo que se verá reflejado en perfilusuario (arriba de la imagen)







        //botones llamando a xml
        Jugarbtn=findViewById(R.id.Jugarbtn);
        Editarbtn=findViewById(R.id.Editarbtn);
        CambiarPassbtn=findViewById(R.id.CambiarPassBtn);
        Putuacionesbtn=findViewById(R.id.Puntuacionesbtn);
        AcercaDebtn=findViewById(R.id.Acercadebtn);
        cerrarsesion=findViewById(R.id.btnCerrar_perfil);


        //evento de jugar lleva al menu
        Jugarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Perfilusuario.this,"JUGAR",Toast.LENGTH_SHORT).show();
                //aqui debería enlazar a los fragment para llevar al sitio de los tres juegos
                startActivity(new Intent(Perfilusuario.this, Menu5.class));



            }
        });

        //EDITAR BTN
        Editarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(Perfilusuario.this,"EDITAR",Toast.LENGTH_SHORT).show();
                EditarDatos();
            }
        });

        CambiarPassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfilusuario.this,CambiarPass.class));
                Toast.makeText(Perfilusuario.this,"Cambiar contraseña",Toast.LENGTH_SHORT).show();
            }
        });

        //evento de puntuaciones el tema del ranking
        Putuacionesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Perfilusuario.this,"PUNTUACIONES",Toast.LENGTH_SHORT).show();
                //aqui el tema de ver los puntajes generales de cada jugador

                startActivity(new Intent(Perfilusuario.this, Ranking.class));





            }
        });
        //información sobre el equipo de quien construyo la app
        AcercaDebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Perfilusuario.this,"ACERCA DE",Toast.LENGTH_SHORT).show();
                AcercaDe();

            }
        });


        //evento de cerrar sesion
        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfilusuario.this,login.class));
            }
        });


    }
 //Button del Miperfil AcercaDe-->INFO
    private void AcercaDe() {
        TextView Desarrolladopor,NombresDesarrollador;
        Button OK;

        dialog.setContentView(R.layout.acerca_de);

        Desarrolladopor=dialog.findViewById(R.id.DesarrolladorPORTXT);
        NombresDesarrollador=dialog.findViewById(R.id.IntegrantesTXT);
        OK=dialog.findViewById(R.id.OK);

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    //METODO EDITAR DATOS
    private void EditarDatos() {
        //definido el arreglo con las opciones que podemos elegir
        String [] Opciones={"Foto de perfil"};

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(Opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cuando se hace click en la foto de perfil=0
                if(i==0){

                    perfil="Imagen";
                    ActualizarFotoPerfil();

                }




            }
        });
        builder.create().show();//para que lo visualice
    }

    //cambio de foto de perfil
    private void ActualizarFotoPerfil() {
        String [] opciones={"Galeria"};
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar imagen de: ");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    //Selecciono a la galeria
                    if(!ComprobarPermisoAlmacenamiento()){ //si de hace click en galeria este metodo comproborá el permiso V o F
                        //SI NO SE HABILITÓ EL PERMISO
                        SolicitarPermisoAlmacenamiento();
                    }else{
                        //SI SE HABILITÓ EL PERMISO
                        ElegirImagenDeGaleria();//cuando sea falso, vote la solicitud del permiso
                    }
                }
            }
        });

        builder.create().show();


    }

    //PERMISO DE ALMACENAMIENTO EN TIEMPO DE EJECUCION
    private void SolicitarPermisoAlmacenamiento() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(Permisosdealmacenamiento,CODIGO_SOLICITUD_ALMACENAMIENTO);
        }
    }
//

    //COMPROBAR SI LOS PERMISOS DE ALMACENAMIENTO ESTAN HABILITADOS O NO
    private boolean ComprobarPermisoAlmacenamiento() {
        Boolean resultado= ContextCompat.checkSelfPermission(Perfilusuario.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                (PackageManager.PERMISSION_GRANTED);
        return resultado;

    }

    //SE LLAMA CUANDO EL USUARIO O JUGADOR PRESIONA PERMITIR O DENEGAR EL CUADRO DE GIALOGO (por ejemplo esta app desea utilizar tu camara)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case CODIGO_SOLICITUD_ALMACENAMIENTO:{
                //SELECCION DE LA GALERIA
                if(grantResults.length >0){
                    boolean EscrituraDeAlmacenamientoAceptado=grantResults[0]==PackageManager.PERMISSION_GRANTED;

                    if(EscrituraDeAlmacenamientoAceptado){
                        //PERMISO QUE HABILITADO
                        ElegirImagenDeGaleria();
                    }else{
                        //EN EL CASO DE QUE EL USUARIO DIJO QUE NO
                        Toast.makeText(this,"HABILITE EL PERMISO DE GALERIA",Toast.LENGTH_SHORT).show();
                    }
                }


            }
            break;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //SE LLAMA CUANDO EL JUGADOR YA HA ELEGIDO LA IMAGEN DE LA GALERIA
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            //DE LA IMAGEN VAMOS A OBTENER LA URI
            if(requestCode==CODIGO_SELECCION_IMAGEN){
                imagen_uri=data.getData(); //obtener el uri de la imagen
                SubirFoto(imagen_uri);

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //ESTE METODO CAMBIA LA FOTO DE PERFIL DEL JUGADOR Y ACTUALIZA LA INFORMACION EN LA BASE DE DATOS DE FIREBASE
    private void SubirFoto(Uri imagen_uri) {
        String RutaDeArchivoYNombre=RutaAlmacenamiento+""+perfil+""+user.getUid();
        StorageReference storageReference=ReferenciaDeAlmacenamiento.child(RutaDeArchivoYNombre);
        storageReference.putFile(imagen_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloaduri=uriTask.getResult();

                        if(uriTask.isSuccessful()){
                            HashMap<String,Object> resultado= new HashMap<>();
                            resultado.put(perfil,downloaduri.toString());
                            mDatabase.child(user.getUid()).updateChildren(resultado)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(Perfilusuario.this,"LA IMAGEN HA SIDO CAMBIADA CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {//en el caso de que algo falle
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Perfilusuario.this,"HA OCURRIDO UN ERROR",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else{
                            Toast.makeText(Perfilusuario.this,"ALGO HA SALIDO MAL",Toast.LENGTH_SHORT).show();
                        }

                    }
                    //Los listener sirve para gestionar mejor los errores
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Perfilusuario.this,"",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //METODO ABRE LA GALERIA
    private void ElegirImagenDeGaleria() {
        Intent IntentGaleria=new Intent(Intent.ACTION_PICK);
        IntentGaleria.setType("image/*");
        startActivityForResult(IntentGaleria,CODIGO_SELECCION_IMAGEN); //problema con startActivityForResult , deprecated version api
    }

    @Override
    protected void onStart() {
        Usuarioenlinea();
        super.onStart();
    }

    //se menciona con un mensaje que el jugador esta en linea
    //con un if se menciona si el el usuario tiene un valor distinto a null, aparecerá la notificación
    private void Usuarioenlinea(){
        if(user!=null){
            Toast.makeText(this, "jugador en linea",Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(Perfilusuario.this, PrimerNivel.class));
            finish();
        }

        mostrarinfo();
    }

    //este metodo es para hacer una consulta a la base de datos

    private void mostrarinfo(){

        //consulta

        //con la siguiente consulta se va hacer que lo ordene por correo //se puede ordenar por puntuacion
        Query query=mDatabase.orderByChild("Email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //haciendo un recorrido constante para obtener los datos solicitados
                for(DataSnapshot ds :snapshot.getChildren()){
                    String nombreusuarioString=""+ds.child("Nombre").getValue();
                 //   String UidString=""+ds.child("Uid").getValue(); //
                    String EmailString=""+ds.child("Email").getValue();
                    String AliasString=""+ds.child("Alias").getValue();
                    String puntuacionString=""+ds.child("Score").getValue();
                    String imagen=""+ds.child("Imagen").getValue();


                    //mostrar los datos
                    Nombreusuario.setText("Usuario: "+nombreusuarioString);
                    //  uid.setText(UidString);
                    Correo.setText("Correo: "+EmailString);
                    Alias.setText("Alias: "+AliasString);
                    puntuaciontxt.setText("Score: "+puntuacionString);


                    try{
                        Picasso.get().load(imagen).into(imagenPerfil);
                    }catch (Exception e){
                        Picasso.get().load(R.mipmap.desconocido).into(imagenPerfil);
                    }



                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }





}
