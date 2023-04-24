package com.android.crud_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class Editar extends AppCompatActivity {
    TextView txt1,txt2,txt3,txt4,txt5;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = simpleDateFormat.format(new Date());
        txt5=findViewById(R.id.id);
        txt1=findViewById(R.id.txtNombre);
        txt2=findViewById(R.id.txtDescripcion);
        txt3=findViewById(R.id.txtFecha);
        txt4=findViewById(R.id.txtEstado);
        txt3.setText(currentDateandTime);
        Intent intent=getIntent();
        position=intent.getExtras().getInt("position");
        txt5.setText(Inicio.users.get(position).getId());
        txt1.setText(Inicio.users.get(position).getNombre());
        txt2.setText(Inicio.users.get(position).getDescripcion());
        //txt3.setText(Inicio.users.get(position).getFecha());
        txt4.setText(Inicio.users.get(position).getEstado());



    }

    public void actualizar(View view){
        final String id =txt5.getText().toString();
        final String nombre = txt1.getText().toString();
        final String descripcion = txt2.getText().toString();
        final String fecha = txt3.getText().toString();
        final String estado = txt4.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Actualizando....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.5/CRUD_ANDROID_PHP/conexionEditar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Editar.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Inicio.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Editar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();

                params.put("id",id);
                params.put("nombre",nombre);
                params.put("descripcion",descripcion);
                params.put("fecha",fecha);
                params.put("estado",estado);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Editar.this);
        requestQueue.add(request);

    }
}