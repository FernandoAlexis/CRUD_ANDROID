package com.android.crud_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inicio extends AppCompatActivity {

    private ListView list;
    private EditText buscador;
    Adaptador adaptador;
    public static ArrayList<Usuarios>users=new ArrayList<>();
    String url="http://192.168.1.5/CRUD_ANDROID_PHP/conexionMostrar.php";
    Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        list=findViewById(R.id.listView);
        adaptador=new Adaptador(this,users);
        list.setAdapter(adaptador);


        buscador=findViewById(R.id.buscador);
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrar(editable.toString());
            }
        });


        FloatingActionButton fab = findViewById(R.id.btnAgregar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Inicio.this,Insertar.class);
                startActivity(i);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder =new AlertDialog.Builder(view.getContext());

                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogoItem={"Ver datos","Editar Datos","Eliminar Datos"};

                builder.setTitle(users.get(position).getNombre());
                builder.setItems(dialogoItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                startActivity(new Intent(getApplicationContext(),Detalles.class)
                                        .putExtra("position",position));
                                break;
                            case 1:
                                startActivity(new Intent(getApplicationContext(),Editar.class)
                                        .putExtra("position",position));
                                break;
                            case 2:
                                EliminarDatos(users.get(position).getId());
                                break;
                        }
                    }
                });
                builder.create().show();

            }
        });
        mostrarDatos();



    }

    private void EliminarDatos(String id){
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.5/CRUD_ANDROID_PHP/conexionEliminar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("Datos Eliminados con exito")){
                            Toast.makeText(Inicio.this, "eliminado correctamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Inicio.class));

                        }
                        else{
                            Toast.makeText(Inicio.this, "no se puedo eliminar", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Inicio.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void mostrarDatos(){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                users.clear();
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    String success=jsonObject.getString("succes");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if(success.equals("1")){
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String nombre = object.getString("nombre");
                            String descripcion = object.getString("descripcion");
                            String fecha = object.getString("fecha");
                            String estado = object.getString("estado");

                            usuarios= new Usuarios(id,nombre,descripcion,fecha,estado);
                            users.add(usuarios);
                            adaptador.notifyDataSetChanged();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Inicio.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void filtrar(String texto){
        ArrayList<Usuarios>filtrarLista = new ArrayList<>();
        for(Usuarios usuario: users ){
            if(usuario.getNombre().toLowerCase().contains(texto.toLowerCase())){
                filtrarLista.add(usuario);
            }
        }

        adaptador.filtrar(filtrarLista);
    }


}