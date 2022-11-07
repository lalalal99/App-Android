package com.example.mixedminds;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PrenotazioniNuovaPrenFragment extends Fragment{ // implements DatePickerDialog.OnDateSetListener {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Nuova Prenotazione");

        // calling the action bar
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);




        View view = inflater.inflate(R.layout.fragment_prenotazioni_nuova_pren, container, false);

//        boolean a = getArguments().getBoolean("orarietariffe");
//        System.out.println(a);

        CheckBox ckbParco = (CheckBox) view.findViewById(R.id.ckbParco);
        CheckBox ckbPalazzo = (CheckBox) view.findViewById(R.id.ckbPalazzo);
        Button button = (Button) view.findViewById(R.id.btn_continua);
        button.setOnClickListener(v -> {
            try {
                if (checkDataOra(view)) {
                    if (ckbPalazzo.isChecked() || ckbParco.isChecked()) {
                        Fragment fr = new ModalitaPagamentoFragment();
                        Bundle b = new Bundle();
                        TextView data = (TextView) view.findViewById(R.id.txtData);
                        TextView ora = (TextView) view.findViewById(R.id.txtOrario);
                        b.putString("Data", (String) data.getText());
                        b.putString("Ora", (String) ora.getText());
                        b.putString("Prenotati", String.valueOf(getPrenotati(view)));
                        System.out.println(b.toString());
                        fr.setArguments(b);
                        // showing the back button in action bar
                        actionBar.setDisplayHomeAsUpEnabled(false);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, fr).addToBackStack("bla1").commit();
                    } else {
    //                Popup con scritto di selezionare una visita
                        Toast.makeText(getActivity().getBaseContext(), "Seleziona una visita prima di continuare!",Toast.LENGTH_LONG).show();
                    }
                } else {
                    TextView textView = (TextView) view.findViewById(R.id.erroreDataOra);
                    textView.setVisibility(View.VISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });


        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        long date = System.currentTimeMillis();

//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
//        String dateString = sdf.format(date);
        TextView textView = view.findViewById(R.id.txtOrario);
        textView.setVisibility(View.VISIBLE);
        textView.setText("10:00");

        gestisciBottoni(view);
        gestisciCheckbox(view);
        gestisciCalendario(view);
        gestisciOrario(view);
        gestisciTotale(view);


    }

    private void gestisciBottoni(View view){

//        Bottoni Adulti
        view.findViewById(R.id.btn_min_adulto).setOnClickListener(v -> {
            TextView txtnumAdulti = (TextView) view.findViewById(R.id.numAdulti);
            int numAdulti = Integer.parseInt(txtnumAdulti.getText().toString());
            if (numAdulti - 1 >= 0 && !checkValues(view)) {
                txtnumAdulti.setText(String.valueOf(numAdulti - 1));
            }
            gestisciTotale(view);
        });

        view.findViewById(R.id.btn_plus_adulto).setOnClickListener(v -> {
            TextView txtnumAdulti = (TextView) view.findViewById(R.id.numAdulti);
            int numAdulti = Integer.parseInt(txtnumAdulti.getText().toString());
            txtnumAdulti.setText(String.valueOf(numAdulti + 1));
            gestisciTotale(view);
        });

//        Bottoni Ridotti
        view.findViewById(R.id.btn_min_ridotto).setOnClickListener(v -> {
            TextView txtnumRidotti = (TextView) view.findViewById(R.id.numRidotti);
            int numRidotti = Integer.parseInt(txtnumRidotti.getText().toString());
            if (numRidotti - 1 >= 0 && !checkValues(view)) {
                txtnumRidotti.setText(String.valueOf(numRidotti - 1));
            }
            gestisciTotale(view);
        });

        view.findViewById(R.id.btn_plus_ridotto).setOnClickListener(v -> {
            TextView txtnumRidotti = (TextView) view.findViewById(R.id.numRidotti);
            int numRidotti = Integer.parseInt(txtnumRidotti.getText().toString());
            txtnumRidotti.setText(String.valueOf(numRidotti + 1));
            gestisciTotale(view);
        });


//        Bottoni Gratuiti
        view.findViewById(R.id.btn_min_gratuiti).setOnClickListener(v -> {
            TextView txtnumGratuiti = (TextView) view.findViewById(R.id.numGratuiti);
            int numGratuiti = Integer.parseInt(txtnumGratuiti.getText().toString());
            if (numGratuiti - 1 >= 0 && !checkValues(view)) {
                txtnumGratuiti.setText(String.valueOf(numGratuiti - 1));
            }
        });

        view.findViewById(R.id.btn_plus_Gratuito).setOnClickListener(v -> {
            TextView txtnumGratuiti = (TextView) view.findViewById(R.id.numGratuiti);
            int numGratuiti = Integer.parseInt(txtnumGratuiti.getText().toString());
            txtnumGratuiti.setText(String.valueOf(numGratuiti + 1));
        });

        gestisciTotale(view);
    }

    private boolean checkValues(View view){
//        Controlla se la somma dei numeri è uguale a uno
         return getPrenotati(view) == 1;
    }

    private int getPrenotati(View view){
        TextView txtnumAdulti = (TextView) view.findViewById(R.id.numAdulti);
        int numAdulti = Integer.parseInt(txtnumAdulti.getText().toString());

        TextView txtnumRidotti = (TextView) view.findViewById(R.id.numRidotti);
        int numRidotti = Integer.parseInt(txtnumRidotti.getText().toString());

        TextView txtnumGratuiti = (TextView) view.findViewById(R.id.numGratuiti);
        int numGratuiti = Integer.parseInt(txtnumGratuiti.getText().toString());

        return numAdulti + numRidotti + numGratuiti;
    }

    private void gestisciCheckbox(View view){

        CheckBox ckbPalazzo = (CheckBox) view.findViewById(R.id.ckbPalazzo);
        CheckBox ckbPalazzoGuidata = (CheckBox) view.findViewById(R.id.ckbPalazzoGuidata);
        CheckBox ckbParco = (CheckBox) view.findViewById(R.id.ckbParco);
        CheckBox ckbParcoGuidata = (CheckBox) view.findViewById(R.id.ckbParcoGuidata);

//        checkbox Palazzo
        ckbPalazzo.setOnClickListener(v -> {
            if (!ckbPalazzo.isChecked()) {
                ckbPalazzoGuidata.setChecked(false);
            }
            gestisciTotale(view);
        });
        ckbPalazzoGuidata.setOnClickListener(v -> {
            if (!ckbPalazzo.isChecked()) {
                ckbPalazzo.setChecked(true);
            }
            gestisciTotale(view);
        });

//        checkbox Parco
        ckbParco.setOnClickListener(v -> {
            if (!ckbParco.isChecked()) {
                ckbParcoGuidata.setChecked(false);
            }
            gestisciTotale(view);
        });
        ckbParcoGuidata.setOnClickListener(v -> {
            if (!ckbParco.isChecked()) {
                ckbParco.setChecked(true);
            }
            gestisciTotale(view);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void gestisciCalendario(View view){
//        System.out.println(getArguments().getString("giorno"));   //il giorno arriva giusto, dentro giorno c'è 1, 2, etc
        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
        LocalDateTime date = LocalDateTime.now();
        int mm = 0;
        int dd = 0;

        if (getArguments() != null) {
            int giorno = getArguments().getInt("giorno");
            date = date.plusDays(1);
            while (date.getDayOfWeek().getValue() != giorno) {
                date = date.plusDays(1);
            }

            mm = date.getMonthValue();
            dd = date.getDayOfMonth();

            System.out.println(date.toString());
        } else {
            mm = date.getMonthValue();
            date = date.plusDays(1);
            if (date.getDayOfWeek().getValue() == 1)
                date = date.plusDays(1);
            dd = date.getDayOfMonth();

        }
        //trova oggi che giorno del mese è, aggiungi uno fino a che non trovi il giorno selezionato

        int finalDd = dd;
        int finalMm = mm;

        String dateString = finalDd + "/" + finalMm + "/" + LocalDateTime.now().getYear();
        TextView textView = view.findViewById(R.id.txtData);
        textView.setVisibility(View.VISIBLE);
        textView.setText(dateString);

        view.findViewById(R.id.btn_calendario).setOnClickListener(v -> {
            TextView txt = (TextView) view.findViewById(R.id.erroreDataOra);
            txt.setVisibility(View.INVISIBLE);
            //metti qui la roba del giorno selezionato
            int yy = calendar.get(Calendar.YEAR);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = dayOfMonth
                            +"/"+ (monthOfYear + 1) +"/"+ year;

                    TextView textView = (TextView) getView().findViewById(R.id.txtData);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(date);                }
            }, yy, finalMm - 1, finalDd);
            datePicker.show();
        });

        view.findViewById(R.id.txtData).setOnClickListener(v -> {
            TextView txt = (TextView) view.findViewById(R.id.erroreDataOra);
            txt.setVisibility(View.INVISIBLE);
            //metti qui la roba del giorno selezionato
            int yy = calendar.get(Calendar.YEAR);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = dayOfMonth
                            +"/"+ (monthOfYear + 1) +"/"+ year;

                    TextView textView = (TextView) getView().findViewById(R.id.txtData);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(date);                }
            }, yy, finalMm - 1, finalDd);
            datePicker.show();
        });

        view.findViewById(R.id.txtGiorno).setOnClickListener(v -> {
            TextView txt = (TextView) view.findViewById(R.id.erroreDataOra);
            txt.setVisibility(View.INVISIBLE);
            //metti qui la roba del giorno selezionato
            int yy = calendar.get(Calendar.YEAR);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = dayOfMonth
                            +"/"+ (monthOfYear + 1) +"/"+ year;

                    TextView textView = (TextView) getView().findViewById(R.id.txtData);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(date);                }
            }, yy, finalMm - 1, finalDd);
            datePicker.show();
        });


    }

    private void gestisciOrario(View view){
        view.findViewById(R.id.btn_Orario).setOnClickListener(v -> {
                TextView txt = (TextView) view.findViewById(R.id.erroreDataOra);
                txt.setVisibility(View.INVISIBLE);
                DialogFragment newFragment  = new TimePickerFragment();
                //newFragment.show(getActivity().getFragmentManager(), DIALOG_TIME);
                newFragment.show(getActivity().getFragmentManager(), "DIALOG_TIME");
                // if you are using the nested fragment then user the
                //newFragment.show(getChildFragmentManager(), DIALOG_TIME);
        });

        view.findViewById(R.id.txtOra).setOnClickListener(v -> {
            TextView txt = (TextView) view.findViewById(R.id.erroreDataOra);
            txt.setVisibility(View.INVISIBLE);
            DialogFragment newFragment  = new TimePickerFragment();
            //newFragment.show(getActivity().getFragmentManager(), DIALOG_TIME);
            newFragment.show(getActivity().getFragmentManager(), "DIALOG_TIME");
            // if you are using the nested fragment then user the
            //newFragment.show(getChildFragmentManager(), DIALOG_TIME);
        });

        view.findViewById(R.id.txtOrario).setOnClickListener(v -> {
            TextView txt = (TextView) view.findViewById(R.id.erroreDataOra);
            txt.setVisibility(View.INVISIBLE);
            DialogFragment newFragment  = new TimePickerFragment();
            //newFragment.show(getActivity().getFragmentManager(), DIALOG_TIME);
            newFragment.show(getActivity().getFragmentManager(), "DIALOG_TIME");
            // if you are using the nested fragment then user the
            //newFragment.show(getChildFragmentManager(), DIALOG_TIME);
        });
    }

    private void gestisciTotale(View view){
        TextView txtnumAdulti = (TextView) view.findViewById(R.id.numAdulti);
        int numAdulti = Integer.parseInt(txtnumAdulti.getText().toString());

        TextView txtnumRidotti = (TextView) view.findViewById(R.id.numRidotti);
        int numRidotti = Integer.parseInt(txtnumRidotti.getText().toString());

//        TextView txtnumGratuiti = (TextView) view.findViewById(R.id.numGratuiti);
//        int numGratuiti = Integer.parseInt(txtnumGratuiti.getText().toString());

        CheckBox ckbPalazzo = (CheckBox) view.findViewById(R.id.ckbPalazzo);
        CheckBox ckbPalazzoGuidata = (CheckBox) view.findViewById(R.id.ckbPalazzoGuidata);
        CheckBox ckbParco = (CheckBox) view.findViewById(R.id.ckbParco);
        CheckBox ckbParcoGuidata = (CheckBox) view.findViewById(R.id.ckbParcoGuidata);

//        palazzo 6 adulti, 3 ridotti,
//           guidata +1, +1
//        parco 6 adulti, 3 ridotti
//           guidata +1.+1

        TextView text = view.findViewById(R.id.txtTotaleEuro);

        int totalePersone = numAdulti + numRidotti;
        int totalePalazzo = ckbPalazzo.isChecked() ? (6 * numAdulti + 3 * numRidotti + (ckbPalazzoGuidata.isChecked() ? totalePersone : 0)) : 0;
        int totaleParco = ckbParco.isChecked() ? (6 * numAdulti + 3 * numRidotti + (ckbParcoGuidata.isChecked() ? totalePersone : 0)) : 0;

        text.setText((totalePalazzo + totaleParco) + ",00€");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkDataOra(View view) throws ParseException {
        //no lunedi, 10-13, 15:30-18
        //sabato e domenica 15:30-18:30

        LocalDateTime time = LocalDateTime.now();
        Instant instant = time.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        TextView ora = (TextView) view.findViewById(R.id.txtOrario);
        String orario = (String) ora.getText(); //18:35
        TextView data = (TextView) view.findViewById(R.id.txtData);
        String giorno = (String) data.getText();

        String prenotazione = giorno.concat(" "+orario);
        SimpleDateFormat sConfronto = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date dConfronto = sConfronto.parse(prenotazione);
        if (dConfronto.before(date))
            return false;

        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        Date miaData = sd.parse(giorno);
        String miaDataGiorno = new SimpleDateFormat("EE").format(miaData);

        sd = new SimpleDateFormat("HH:mm");
        Date miaOra = sd.parse(orario);
        miaOra.getHours();

        boolean flag = true;

        System.out.println(miaDataGiorno);
        if (miaDataGiorno.equals("Mon") || miaDataGiorno.equals("lun")) {
            flag = false;
        }
        else if (miaOra.getHours() < 10 || miaOra.getHours() > 18){
            flag = false;
        }else if (miaOra.getHours() >= 13) {
            if (miaDataGiorno.equals("Sat") || miaDataGiorno.equals("Sun") || miaDataGiorno.equals("sab") || miaDataGiorno.equals("dom")) {
                if ((miaOra.getHours() <= 15 && miaOra.getMinutes() < 30) || (miaOra.getHours() == 18 && miaOra.getMinutes() > 0)) {
                    flag = false;
                }
            } else if ((miaOra.getHours() <= 15 && miaOra.getMinutes() < 30) || (miaOra.getHours() == 17 && miaOra.getMinutes() > 30) || (miaOra.getHours() > 17)) {
                flag = false;
            }
        }
        return flag;
    }
}


























