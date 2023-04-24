package com.android.crud_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Adaptador  extends ArrayAdapter<Usuarios> {
    Context context;
    List<Usuarios> arraylistUsuario;
    public Adaptador(@NonNull Context context, List<Usuarios>arraylistUsuario){
        super(context, R.layout.my_list_item,arraylistUsuario);

        this.context=context;
        this.arraylistUsuario = arraylistUsuario;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_item,null,true);
        TextView textView=view.findViewById(R.id.tvid);
        TextView textView1 = view.findViewById(R.id.tvnombre);

        textView.setText(arraylistUsuario.get(position).getId());
        textView1.setText(arraylistUsuario.get(position).getNombre());
        return view;
    }

    @Override
    public int getCount() {
        return arraylistUsuario.size();
    }

    public void filtrar(ArrayList<Usuarios> filtroUsuarios){
        this.arraylistUsuario=filtroUsuarios;
        notifyDataSetChanged();
    }
}
