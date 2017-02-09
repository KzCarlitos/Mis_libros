package com.example.a2daw.mis_libros;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listaLibros;
    private Cursor cursor;
    BDLibro DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //BOTÓN +
        FloatingActionButton btn_MAS = (FloatingActionButton) findViewById(R.id.btn_MAS);
        btn_MAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Botón más pulsado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), VistaLibro.class);
                intent.putExtra("id", 0);
                startActivity(intent);
            }
        });

        DB = new BDLibro(this);
        InsertaDatosIniciales();
        ActualizaVista();
        listaLibros.setOnItemClickListener(this);//Click en cada linea de la lista

    }

    /**
     * Actualiza la vista con los datos actuales de la base de datos
     */
    protected void onResume(){
        super.onResume();
        ActualizaVista();
    }

    /**
     * Click en cada fila de la lista, le pasamos el ID del libro
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, VistaLibro.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    /**
     * Carga todos los libros guardados en la lista
     */
    public void ActualizaVista() {
        listaLibros = (ListView) findViewById(R.id.listaLibros);

        try {
            DB.openBDL();//Abrimos la Bd en modo lectura
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cursor = DB.RecuperaLibros();//Guardamos en el cursor los libros

        if (cursor.moveToFirst()) {
            Adaptador adaptador = new Adaptador(this, cursor, 0);

            listaLibros.setAdapter(adaptador);//Pasamos al listview los libros del cursor
        }else{
            listaLibros.removeAllViewsInLayout();//Si no hay libros, borra sus información en el layout
        }

        DB.Cerrar();
    }

    /**
     * Añade a la base de datos todos los libros iniciales
     */
    public void InsertaDatosIniciales() {

        try {
            DB.openBDE();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (DB.RecuperaReg() == 0) {
                DB.InsertLibro("Don Quijote", "Antilla", "FRIDA", "9788494398902", "2015",
                        "172", 1, 0, 1f, "El aventurero Don quijote.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DB.Cerrar();
    }
}
