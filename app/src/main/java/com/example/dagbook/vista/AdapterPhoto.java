package com.example.dagbook.vista;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dagbook.R;
import com.example.dagbook.modelo.imageModel;

import java.util.ArrayList;

public class AdapterPhoto extends RecyclerView.Adapter<AdapterPhoto.ViewHolder> {
ArrayList<imageModel> model;
Context context;

    public AdapterPhoto(ArrayList<imageModel> model, Context context) {
        this.model = model;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_photo,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(model.get(position).getImagenurl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}

