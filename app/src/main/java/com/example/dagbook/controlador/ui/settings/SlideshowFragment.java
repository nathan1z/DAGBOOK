package com.example.dagbook.controlador.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dagbook.R;
import com.example.dagbook.controlador.ContraListActivity;
import com.example.dagbook.controlador.ProspectList;
import com.example.dagbook.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {
    private Button btnGestioContra,btnGestioProspect;
    private View view;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_slideshow,container, false);
        btnGestioContra=(Button) view.findViewById(R.id.bRecordatorios);
        btnGestioProspect=(Button) view.findViewById(R.id.bEventos);
        btnGestioContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ContraListActivity.class);
                startActivity(intent);
            }
        });
        btnGestioProspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProspectList.class);
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