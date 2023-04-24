package com.android.crud_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Insertar extends AppCompatActivity {

    EditText textNombre,textDescripcion,textFecha,textEstado;
    Button btnInsertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = simpleDateFormat.format(new Date());
        textNombre=findViewById(R.id.txtNombre);
        textDescripcion=findViewById(R.id.txtDescripcion);

        textFecha= findViewById(R.id.txtFecha);
        textEstado= findViewById(R.id.txtEstado);

        btnInsertar=findViewById(R.id.btnInsertar);
        textFecha.setText(currentDateandTime);

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();

            }
        });
    }


    private void insertar(){
        String nombre = textNombre.getText().toString().trim();
        String descripcion = textDescripcion.getText().toString().trim();
        String fecha= textFecha.getText().toString().trim();
        String estado= textEstado.getText().toString().trim();


       final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando...");
        if(nombre.isEmpty()){
            textNombre.setError("Complete los campos");

        }else if(descripcion.isEmpty()){
            textDescripcion.setError("Complete los campos");
        }else if (fecha.isEmpty()){
            textFecha.setError("Complete los campos");
        }else if (estado.isEmpty()){
            textEstado.setError("Complete los campos");
        }else{
            progressDialog.show();
            StringRequest request= new StringRequest(Request.Method.POST, "http://192.168.1.5/CRUD_ANDROID_PHP/conexion.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("Datos insertados con exito")) {
                        Toast.makeText(Insertar.this, "datas insertados", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),Inicio.class));
                        finish();

                    } else {
                        Toast.makeText(Insertar.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Insertar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String ,String>params=new HashMap<String,String>();
                   params.put("nombre",nombre);
                   params.put("descripcion",descripcion);
                   params.put("fecha",fecha);
                   params.put("estado",estado);
                   return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(Insertar.this);

            requestQueue.add(request);

        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}