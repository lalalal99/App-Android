package com.example.mixedminds;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PrenotazioniFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Prenotazioni");
        View view = inflater.inflate(R.layout.fragment_prenotazioni, container, false);

        Button button = (Button) view.findViewById(R.id.buttonPrenota);
        button.setOnClickListener(v -> {
//            Intent intent = new Intent (getActivity(),PrenotazioniNuovaPrenFragment.class);
//            startActivity(intent);

            /*Fragment fragment = new PrenotazioniNuovaPrenFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_prenotazione, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/

            getFragmentManager().beginTransaction().
            replace(R.id.fragment_prenotazione, new PrenotazioniNuovaPrenFragment()).
            addToBackStack("frags").commit();
        });
        return view;

    }
}
