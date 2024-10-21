package com.example.dagbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dagbook.modelo.Persona2;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private ProspectAdapter mProspectAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Persona2>personas, List<String> keys){
        mContext= context;
        mProspectAdapter= new ProspectAdapter(personas,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mProspectAdapter);
    }
    class ProspectItemView extends RecyclerView.ViewHolder {
private TextView name,phone,address,mail;
private String key;
public  ProspectItemView(ViewGroup parent){
    super(LayoutInflater.from(mContext).inflate(R.layout.item,parent,false));
    name= (TextView) itemView.findViewById(R.id.tvname);
    phone=(TextView)itemView.findViewById(R.id.tvphone);
    address=(TextView)itemView.findViewById(R.id.tvaddress);
    mail= (TextView) itemView.findViewById(R.id.tvemail);
    itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent= new Intent(mContext,ProspectDetailsActivity.class);
            intent.putExtra("key",key);
            intent.putExtra("name",name.getText().toString());
            intent.putExtra("phone",phone.getText().toString());
            intent.putExtra("address",address.getText().toString());
            intent.putExtra("mail",mail.getText().toString());

            mContext.startActivity(intent);

        }
    });
}
public void bind(Persona2 persona, String key){
    name.setText(persona.getName());
    phone.setText(persona.getPhone());
    address.setText(persona.getAddress());
    mail.setText(persona.getMail());
    this.key=key;
}
    }
    class ProspectAdapter extends RecyclerView.Adapter<ProspectItemView>{
        private List<Persona2> mPersonaList;
        private List<String> mKeys;

        public ProspectAdapter(List<Persona2> mPersonaList, List<String> mKeys) {
            this.mPersonaList = mPersonaList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ProspectItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return  new ProspectItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProspectItemView holder, int position) {
            holder.bind(mPersonaList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mPersonaList.size();
        }
    }
}
