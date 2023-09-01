package com.example.proyectocrud;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Contacto> lista;
    daoContacto dao;
    Contacto c;
    Activity a;
    int id=0;
    public Adaptador(Activity a, ArrayList<Contacto> lista,  daoContacto dao){
        this.lista=lista;
        this.a=a;
        this.dao=dao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Contacto getItem(int i) {
        c=lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        c=lista.get(i);
        return c.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v=view;
        if(v==null){
            LayoutInflater li=(LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=li.inflate(R.layout.item, null);
        }
        c=lista.get(posicion);
        TextView nombre = (TextView)v.findViewById(R.id.t_nombre);
        TextView tel = (TextView)v.findViewById(R.id.t_telefono);
        TextView email = (TextView)v.findViewById(R.id.t_email);
        TextView edad = (TextView)v.findViewById(R.id.t_edad);
        ImageButton editar= v.findViewById(R.id.editar1);
        ImageButton eliminar=v.findViewById(R.id.eliminar1);
        nombre.setText(c.getNombre());
        tel.setText(c.getTelefono());
        email.setText(c.getEmail());
        edad.setText(""+c.getEdad());
        editar.setOnClickListener(v1 -> {
            c=lista.get(posicion);
            final Dialog dialogo = new Dialog(a);
            dialogo.setTitle("Editar registro");
            dialogo.setCancelable(true);
            dialogo.setContentView(R.layout.dialogo);
            dialogo.show();
            final EditText nombreDialogo = (EditText) dialogo.findViewById(R.id.nombre);
            final EditText telDialogo = (EditText) dialogo.findViewById(R.id.telefono);
            final EditText emailDialogo = (EditText) dialogo.findViewById(R.id.email);
            final EditText edadDialogo = (EditText) dialogo.findViewById(R.id.edad);
            Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);
            guardar.setText("Guardar");
            Button cancelar = (Button)dialogo.findViewById(R.id.d_cancelar);

            setId(c.getId());
            nombreDialogo.setText(c.getNombre());
            telDialogo.setText(c.getTelefono());
            emailDialogo.setText(c.getEmail());
            edadDialogo.setText(""+c.getEdad());
            guardar.setOnClickListener(v2 -> {
                try {
                    c = new Contacto(getId(), nombreDialogo.getText().toString(),
                            telDialogo.getText().toString(),
                            emailDialogo.getText().toString(),
                            Integer.parseInt(edadDialogo.getText().toString()));
                    boolean actualizacionExitosa = dao.editar(c, a);
                    if (actualizacionExitosa) {
                        lista = dao.verTodos();
                        notifyDataSetChanged();
                        dialogo.dismiss();
                    } else {
                        Toast.makeText(a, "La actualización falló", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(a, "ERROR", Toast.LENGTH_SHORT).show();
                }
            });

            cancelar.setOnClickListener(v3 -> {
                dialogo.dismiss();
            });

        });
        eliminar.setOnClickListener(v4 -> {
            c=lista.get(posicion);
            setId(c.getId());
            AlertDialog.Builder del=new AlertDialog.Builder(a);
            del.setMessage("Estas seguro de eliminar el contecto?");
            del.setCancelable(false);
            del.setPositiveButton("Si", (dialog, which) -> {
                dao.eliminar(getId());
                lista=dao.verTodos();
                notifyDataSetChanged();

            });
            del.setNegativeButton("No", (dialog, which) -> {

            });
            del.show();
        });
        return v;
    }
}
