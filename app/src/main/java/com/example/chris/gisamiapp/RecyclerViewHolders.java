package com.example.chris.gisamiapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.gisamiapp.com.example.chris.gisamiapp.tematicas.TemAnticonActivity;
import com.example.chris.gisamiapp.com.example.chris.gisamiapp.tematicas.TemCuidcuerpActivity;
import com.example.chris.gisamiapp.com.example.chris.gisamiapp.tematicas.TemEmbarzActivity;
import com.example.chris.gisamiapp.com.example.chris.gisamiapp.tematicas.TemEtsActivity;
import com.example.chris.gisamiapp.com.example.chris.gisamiapp.tematicas.TemSaludActivity;


public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView countryName;
    public ImageView countryPhoto;

    public RecyclerViewHolders(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        countryName = (TextView)itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(View view)
    {

        if (getPosition() ==2 )
        {
            Toast.makeText(view.getContext(), "Bienvenida!", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(view.getContext(), TemAnticonActivity.class);
            view.getContext().startActivity(myIntent);
        }

        if (getPosition() ==0 )
        {
            Toast.makeText(view.getContext(), "Bienvenida!", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(view.getContext(), TemEtsActivity.class);
            view.getContext().startActivity(myIntent);
        }

        if (getPosition() ==1 )
        {
            Toast.makeText(view.getContext(), "Bienvenida!", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(view.getContext(), TemCuidcuerpActivity.class);
            view.getContext().startActivity(myIntent);
        }

        if (getPosition() ==3 )
        {
            Toast.makeText(view.getContext(), "Bienvenida!", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(view.getContext(), TemEmbarzActivity.class);
            view.getContext().startActivity(myIntent);
        }

        if (getPosition() ==4 )
        {
            Toast.makeText(view.getContext(), "Bienvenida!", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(view.getContext(), TemSaludActivity.class);
            view.getContext().startActivity(myIntent);
        }
    }

}