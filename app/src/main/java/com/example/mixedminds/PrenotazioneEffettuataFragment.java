package com.example.mixedminds;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static android.os.ParcelFileDescriptor.MODE_APPEND;

public class PrenotazioneEffettuataFragment extends Fragment {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Usa questo QR Code per saltare la fila alla cassa, puoi trovarlo nella sezione prenotazioni
        getActivity().setTitle("Prenotazione Effettuata");
        // calling the action bar
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(false);
        View view = inflater.inflate(R.layout.fragment_pren_effettuata, container, false);;
        TextView txt = view.findViewById(R.id.txtPrenConfermata);

        boolean popUp = getArguments().getBoolean("popUp");
        TextView txtQr = view.findViewById(R.id.txtQrCode);
        txtQr.setText(popUp ? "Usa questo QR Code per saltare la fila alla cassa." :
                "Usa questo QR Code per saltare la fila alla cassa, puoi trovarlo nella sezione prenotazioni");

        if (!popUp) {
            boolean inApp = getArguments().getBoolean("inApp");
            String prenotazione = getArguments().getString("Data");
            prenotazione = prenotazione.concat(" " + getArguments().getString("Ora"));
            prenotazione = prenotazione.concat(" " + (inApp ? "si" : "no"));
            prenotazione = prenotazione.concat(" " + getArguments().getString("Prenotati") + "\n");
            System.out.println(prenotazione);


            GestoreFile gs = new GestoreFile();
            txt.setText(inApp ? "Pagamento Effettuato" : "Prenotazione Confermata");

            try {
                gs.saveFile(this.getContext(), prenotazione);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            getActivity().setTitle("QR Code Prenotazione");
            txt.setVisibility(popUp ? View.INVISIBLE : View.VISIBLE);
        }

        Button btn = view.findViewById(R.id.btnChiudiFinePren);
        btn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, new PrenotazioniFragment()).commit();
        });
        return view;
    }
}
