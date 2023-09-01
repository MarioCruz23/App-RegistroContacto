package com.example.proyectocrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    daoContacto dao;
    Adaptador adapter;
    ArrayList<Contacto> lista;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new daoContacto(this);
        dao=new daoContacto(this);
        lista=dao.verTodos();
        adapter=new Adaptador(this, lista, dao);
        ListView list=(ListView)findViewById(R.id.lista);
        Button agregar=(Button)findViewById(R.id.agregar);
        list.setAdapter(adapter);
        list.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long id) -> {


        });
        agregar.setOnClickListener(v -> {
            final Dialog dialogo = new Dialog(MainActivity.this);
            dialogo.setTitle("Nuevo registro");
            dialogo.setCancelable(true);
            dialogo.setContentView(R.layout.dialogo);
            dialogo.show();
            final EditText nombre=(EditText)dialogo.findViewById(R.id.nombre);
            final EditText tel=(EditText)dialogo.findViewById(R.id.telefono);
            final EditText email=(EditText)dialogo.findViewById(R.id.email);
            final EditText edad=(EditText)dialogo.findViewById(R.id.edad);
            Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);
            guardar.setText("Agregar");
            Button cancelar = (Button)dialogo.findViewById(R.id.d_cancelar);
            guardar.setOnClickListener(v1 -> {
                try{
                    c=new Contacto(nombre.getText().toString(),
                            tel.getText().toString(),
                            email.getText().toString(),
                            Integer.parseInt(edad.getText().toString()));
                    dao.insertar(c);
                    lista=dao.verTodos();
                    adapter.notifyDataSetChanged();
                    dialogo.dismiss();
                }catch (Exception e){
                    Toast.makeText(getApplication(),"ERROR", Toast.LENGTH_SHORT).show();
                }
            });
            cancelar.setOnClickListener(v2 -> {
                dialogo.dismiss();
            });
        });
    }
    public void editarContacto(Contacto c) {
        dao.editar(c, this);
    }
}