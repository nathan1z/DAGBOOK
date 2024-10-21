package com.example.dagbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dagbook.modelo.Persona2;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerView_Config2 {
    private Context mContext;
    private RecyclerView_Config2.ProspectAdapter mProspectAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Persona2> personas, List<String> keys){
        mContext= context;
        mProspectAdapter= new RecyclerView_Config2.ProspectAdapter(personas,keys);
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
            mail= (TextView)itemView.findViewById(R.id.tvemail);
            CharSequence text2 = "Correo  a Enviar";
            int duration = Toast.LENGTH_LONG;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s2=mail.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL,new String[]{s2});
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Citación");
                    intent.putExtra(Intent.EXTRA_TEXT,"Estimado .\n"+name.getText().toString());
                    intent.setType("message/rfc822");
                    mContext.startActivity(Intent.createChooser(intent,"Elije un cliente de Correo: "));
                    Toast toast = Toast.makeText(mContext.getApplicationContext(), text2, duration);
                    toast.show();
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
    class ProspectAdapter extends RecyclerView.Adapter<RecyclerView_Config2.ProspectItemView>{
        private List<Persona2> mPersonaList;
        private List<String> mKeys;

        public ProspectAdapter(List<Persona2> mPersonaList, List<String> mKeys) {
            this.mPersonaList = mPersonaList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public RecyclerView_Config2.ProspectItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return  new RecyclerView_Config2.ProspectItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView_Config2.ProspectItemView holder, int position) {
            holder.bind(mPersonaList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mPersonaList.size();
        }
    }
}
