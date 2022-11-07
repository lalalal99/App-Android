package com.example.mixedminds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class PrenotazioniNuovaPrenFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Prenotazioni | Nuova Prenotazione");

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(new PrenotazioniFragment()).commit();

//        getFragmentManager().beginTransaction().remove(PrenotazioniFragment.this).commit()
        View view = inflater.inflate(R.layout.fragment_prenotazioni_nuova_pren, container, false);
        return view;
    }
}

    /*public void addActivity(View view) {
        ActivityFragment myFragment = new ActivityFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, myFragment).commit();
    }*/