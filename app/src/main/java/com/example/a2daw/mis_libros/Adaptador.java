package com.example.a2daw.mis_libros;

/**
 * Created by 2DAW on 08/02/2017.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class Adaptador extends CursorAdapter {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Adaptador(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lista, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Cogemos los elementos de la vista de libros para depues procesarlos.
        TextView tv_titulo = (TextView) view.findViewById(R.id.tv_titulo);
        TextView tv_autor = (TextView) view.findViewById(R.id.tv_autor);
        RatingBar rating_nota = (RatingBar) view.findViewById(R.id.rating_nota);
        ImageView imagen = (ImageView) view.findViewById(R.id.imagen);

        //Extraemos los datos del cursor  y lo almacenamos en las variables.
        String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
        String autor = cursor.getString(cursor.getColumnIndexOrThrow("autor"));
        Float nota = cursor.getFloat(cursor.getColumnIndexOrThrow("nota"));


        //Guardamos en los elementos de la vista los datos almacenados en el cursor.
        tv_titulo.setText(titulo);
        tv_autor.setText(autor);
        rating_nota.setRating(nota);

        //Establece una imagen  de forma aleatoria. Y una por defecto.
        switch ((int) (Math.random() * 3)) {
            case 0:
                imagen.setImageResource(R.drawable.libro1);
                break;
            case 1:
                imagen.setImageResource(R.drawable.libro2);
                break;
            case 2:
                imagen.setImageResource(R.drawable.libro3);
                break;
            default:
                imagen.setImageResource(R.drawable.libro3);
                break;

        }
    }
}