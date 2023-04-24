package com.android.crud_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Detalles extends AppCompatActivity {

    TextView txt1,txt2,txt3,txt4,txt5;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        txt1=findViewById(R.id.id);
        txt2=findViewById(R.id.Nombre);
        txt3=findViewById(R.id.Descripcion);
        txt4=findViewById(R.id.Fecha);
        txt5=findViewById(R.id.Estado);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        txt1.setText(" ID: " +Inicio.users.get(position).getId());
        txt2.setText(" Nombre: " +Inicio.users.get(position).getNombre());
        txt3.setText(" Descripcion: " +Inicio.users.get(position).getDescripcion());
        txt4.setText(" Fecha: " +Inicio.users.get(position).getFecha());
        txt5.setText(" Estado: " +Inicio.users.get(position).getEstado());





    }
}