package com.example.dagbook.controlador.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dagbook.controlador.ContractActivity;
import com.example.dagbook.controlador.ProspectActivity;
import com.example.dagbook.R;
import com.example.dagbook.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment /*implements View.OnClickListener*/ {
    View view;
    private FragmentHomeBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home, container, false);
        //Prospectos
        ImageButton prospect = (ImageButton) view.findViewById(R.id.btnprospectos);
        prospect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProspectActivity.class);
                startActivity(intent);
            }
        });
        //Contratos
        ImageButton contrato= (ImageButton) view.findViewById(R.id.btncontratos);
        contrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContractActivity.class);
                startActivity(intent);
            }
        });
    return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}