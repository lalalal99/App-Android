package com.example.mixedminds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ModalitaPagamentoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Modalità Pagamento");
        // calling the action bar
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        View view = inflater.inflate(R.layout.fragment_modalita_pagamento, container, false);

        Bundle data_ora = getArguments();

        Button btnIndietro = (Button) view.findViewById(R.id.btnIndietroModPag);
        btnIndietro.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, new PrenotazioniNuovaPrenFragment()).commit();
        });

        Button btncontinua = (Button) view.findViewById(R.id.btnContinuaModPag);
        ImageView visa = (ImageView) view.findViewById(R.id.imgVisa);
        ImageView mastercard = (ImageView) view.findViewById(R.id.imgMastercard);
        ImageView paypal = (ImageView) view.findViewById(R.id.imgPaypal);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case R.id.radio_app:
                    btncontinua.setVisibility(View.INVISIBLE);
                    visa.setVisibility(View.VISIBLE);
                    mastercard.setVisibility(View.VISIBLE);
                    paypal.setVisibility(View.VISIBLE);
                    break;
                case R.id.radio_loco:
                    btncontinua.setVisibility(View.VISIBLE);
                    visa.setVisibility(View.INVISIBLE);
                    mastercard.setVisibility(View.INVISIBLE);
                    paypal.setVisibility(View.INVISIBLE);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + checkedId);
            }
        });
        Fragment fr = new PrenotazioneEffettuataFragment();
        Bundle b = new Bundle();
        System.out.println(b.toString());
        b.putAll(data_ora);
        System.out.println(b.toString());
        //aggiungi data e ora dal fragment prima di questo
        btncontinua.setOnClickListener(v -> {
            b.putBoolean("inApp", false);
            fr.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, fr ).commit();
        });
        visa.setOnClickListener(v -> {
            b.putBoolean("inApp", true);
            fr.setArguments(b);
            System.out.println(b.toString());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, fr ).commit();
        });
        mastercard.setOnClickListener(v -> {
            b.putBoolean("inApp", true);
            fr.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, fr ).commit();
        });
        paypal.setOnClickListener(v -> {
            b.putBoolean("inApp", true);
            fr.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, fr ).commit();
        });

        return view;
    }
}
