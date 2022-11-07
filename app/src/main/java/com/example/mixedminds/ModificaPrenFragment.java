package com.example.mixedminds;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ModificaPrenFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Modifica Prenotazione");
        // calling the action bar
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        View view = inflater.inflate(R.layout.fragment_modifica_pren, container, false);

        TextView txtData = (TextView) view.findViewById(R.id.txtModData);
        TextView txtOra = (TextView) view.findViewById(R.id.txtModOrario);

        txtData.setVisibility(View.VISIBLE);
        txtOra.setVisibility(View.VISIBLE);

        txtData.setText(getArguments().getString("Data"));
        txtOra.setText(getArguments().getString("Ora"));

        view.findViewById(R.id.txtModData).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = dayOfMonth
                            +"/"+ (monthOfYear + 1) +"/"+ year;

                    txtData.setText(date);
                }
            }, yy, mm, dd);
            datePicker.show();
        });

        view.findViewById(R.id.btn_Modcalendario).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = dayOfMonth
                            +"/"+ (monthOfYear + 1) +"/"+ year;

                    txtData.setText(date);
                }
            }, yy, mm, dd);
            datePicker.show();

        });

        view.findViewById(R.id.txtModGiorno).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = dayOfMonth
                            +"/"+ (monthOfYear + 1) +"/"+ year;

                    txtData.setText(date);
                }
            }, yy, mm, dd);
            datePicker.show();
        });

        view.findViewById(R.id.txtModOra).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = 0;//calendar.get(Calendar.MINUTE);
            TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String string = String.valueOf(minute);
                    String ora = hourOfDay
                            +":"+ (string.length() == 1 ? ("0" + minute) : minute);

                    txtOra.setText(ora);
                }

            }, hour, minute, true);
            timePicker.show();
        });

        view.findViewById(R.id.btn_ModOrario).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = 0;//calendar.get(Calendar.MINUTE);
            TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String string = String.valueOf(minute);
                    String ora = hourOfDay
                            +":"+ (string.length() == 1 ? ("0" + minute) : minute);

                    txtOra.setText(ora);
                }

            }, hour, minute, true);
            timePicker.show();

        });

        view.findViewById(R.id.txtModOrario).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = 0;//calendar.get(Calendar.MINUTE);
            TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String string = String.valueOf(minute);
                    String ora = hourOfDay
                            +":"+ (string.length() == 1 ? ("0" + minute) : minute);

                    txtOra.setText(ora);
                }

            }, hour, minute, true);
            timePicker.show();
        });
//        view.findViewById(R.id.btn_indietroMod).setOnClickListener(v -> {
//            boolean inApp = getArguments().getBoolean("inApp");
//            String prenotazione = txtData.getText().toString();
//            prenotazione = prenotazione.concat(" " + txtOra.getText().toString());
//            prenotazione = prenotazione.concat(" " + (inApp ? "si" : "no"));
//            prenotazione = prenotazione.concat(" " + getArguments().getString("Prenotati") + "\n");
//
//
//            GestoreFile gs = new GestoreFile();
//            try {
//                gs.modificaFile(this.getContext(), prenotazione);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, new PrenotazioniFragment()).commit();
//        });

        view.findViewById(R.id.btn_modifica).setOnClickListener(v -> {
            boolean inApp = getArguments().getBoolean("inApp");
            String prenotazione = txtData.getText().toString();
            prenotazione = prenotazione.concat(" " + txtOra.getText().toString());
            prenotazione = prenotazione.concat(" " + (inApp ? "si" : "no"));
            prenotazione = prenotazione.concat(" " + getArguments().getString("Prenotati") + "\n");
            System.out.println(prenotazione);

            GestoreFile gs = new GestoreFile();

//            List<List<String>> prenotazioni = new ArrayList<>();
//            List<String> lista = Arrays.asList(getArguments().getString("stringa").split("\n"));
//            for (int i=0; i < lista.size(); i++) {
//                prenotazioni.add(Arrays.asList(lista.get(i).split(" ")));
//            }

            //leggi da file, costruisci lista, rimuovi index, scrivi su file

            List<List<String>> prenotazioni = gs.readFile(this.getContext());
            prenotazioni.remove(getArguments().getInt("index"));

            String stringa = "";
            for (int i=0; i < prenotazioni.size();i++) {
                stringa = stringa.concat(prenotazioni.get(i).get(0));
                stringa = stringa.concat(" " + prenotazioni.get(i).get(1));
                stringa = stringa.concat(" " + prenotazioni.get(i).get(2));
                stringa = stringa.concat(" " + prenotazioni.get(i).get(3) + "\n");
            }

            stringa = stringa.concat(prenotazione);

            try {
                gs.modificaFile(this.getContext(), stringa);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                gs.modificaFile(this.getContext(), stringa);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, new PrenotazioniFragment()).commit();
        });
        return view;
    }
}
