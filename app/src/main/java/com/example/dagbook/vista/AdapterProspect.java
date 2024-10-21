package com.example.dagbook.vista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dagbook.R;
import com.example.dagbook.modelo.Persona;
import com.example.dagbook.modelo.Persona2;

import java.util.ArrayList;

public class AdapterProspect extends RecyclerView.Adapter<AdapterProspect.MyViewHolder> {
    Context context;
    ArrayList<Persona2> list;

    public AdapterProspect(Context context, ArrayList<Persona2> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Persona2 pros= list.get(position);
        holder.name.setText(pros.getName());
        holder.phone.setText(pros.getPhone());
        holder.address.setText(pros.getAddress());
        holder.mail.setText(pros.getMail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,phone,address,mail;
        public  MyViewHolder(@NonNull View itemView){
            super(itemView);
            name= itemView.findViewById(R.id.tvname);
            phone=itemView.findViewById(R.id.tvphone);
            address=itemView.findViewById(R.id.tvaddress);
            mail=itemView.findViewById(R.id.txtEmail);
        }
    }
}
