package com.example.mixedminds;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.media.CamcorderProfile.get;

public class OrariETariffeFragment extends Fragment {

    String[] giorni = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
    int index = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Orari e Tariffe");

        // calling the action bar
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(false);

        View view = inflater.inflate(R.layout.fragment_orari_e_tariffe, container, false);
        Random random = new Random();

        barChart(view, random);

        ImageView leftArrow = (ImageView) view.findViewById(R.id.leftArrow);
        TextView txtgiorno = (TextView) view.findViewById(R.id.txtGiornoAffluenza);
        TextView txtOrarioP = (TextView) view.findViewById(R.id.txtOrariAperturaP);
        ImageView rightArrow = (ImageView) view.findViewById(R.id.rightArrow);

        final Calendar calendar = Calendar.getInstance();

        LocalDateTime date = LocalDateTime.now();
        date = date.plusDays(1);
        if (date.getDayOfWeek().getValue() == 1)
            date = date.plusDays(1);
//        for (int i=0; i<8; i++){
//            System.out.println(String.valueOf(date.getDayOfWeek().getValue()) + " " + String.valueOf(date.getDayOfWeek()));
//            date = date.plusDays(1);
//        }

//        System.out.println(date.getDayOfWeek().getValue());
//        calendar.add(Calendar.DAY_OF_WEEK, 1);
        int gg = date.getDayOfWeek().getValue();
        final int giornoSettimana = gg == 7 ? 0 : date.getDayOfWeek().getValue() - 1;//calendar.get(Calendar.DAY_OF_WEEK);
        txtgiorno.setText(giorni[giornoSettimana]);//(giornoSettimana != 0 && giornoSettimana != 1) ? giornoSettimana - 1 : giornoSettimana]);
        index = giornoSettimana;

        leftArrow.setOnClickListener(v -> {
            if (index - 1 >= 1) index -= 1;
            else index = 6;
            txtgiorno.setText(giorni[index]);
            txtOrarioP.setText(txtgiorno.getText().toString().equals("Sabato") || txtgiorno.getText().toString().equals("Domenica") ?"15:00-18:30" : "15:00-18:00");
            barChart(view, random);
        });

        rightArrow.setOnClickListener(v -> {
            if (index + 1 <= 6) index += 1;
            else index = 1;
            txtgiorno.setText(giorni[index]);
            txtOrarioP.setText(txtgiorno.getText().toString().equals("Sabato") || txtgiorno.getText().toString().equals("Domenica") ?"15:00-18:30" : "15:00-18:00");
            barChart(view, random);
        });


        view.findViewById(R.id.btnPrenotaOrari).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Fragment fragment = new PrenotazioniNuovaPrenFragment();

            String giorno = txtgiorno.getText().toString();
            //martedi 2 domenica 7
            if (giorno.equals("Martedì"))
                bundle.putInt("giorno", 2);
            else if (giorno.equals("Mercoledì"))
                bundle.putInt("giorno", 3);
            else if (giorno.equals("Giovedì"))
                bundle.putInt("giorno", 4);
            else if (giorno.equals("Venerdì"))
                bundle.putInt("giorno", 5);
            else if (giorno.equals("Sabato"))
                bundle.putInt("giorno", 6);
            else if (giorno.equals("Domenica"))
                bundle.putInt("giorno", 7);

            fragment.setArguments(bundle);

            GestoreFile gs = new GestoreFile();
            List<List<String>> prenotazioni = gs.readFile(this.getContext());

            if (prenotazioni.size() >= 2)
                Toast.makeText(getActivity().getBaseContext(), "Massimo due prenotazioni disponibili in questa versione. Cancellane una per continuare.",Toast.LENGTH_LONG).show();
            else
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, fragment).addToBackStack("frags").commit();
        });
        return view;
    }

    private void barChart(View view, Random random) {
        /** Contiene
         *  {(9 vuota) 10 11 12 (13 14 vuote)
         *  15 16 17 18 (19 sab, dom)}
         */

        Map<Integer, Integer> orari = new HashMap<>();
        orari.put(9, 0);
        orari.put(10, random.nextInt(24));
        orari.put(11, random.nextInt(24));
        orari.put(12, random.nextInt(24));
        orari.put(13, 0);
        orari.put(14, 0);
        orari.put(15, random.nextInt(24));
        orari.put(16, random.nextInt(24));
        orari.put(17, random.nextInt(24));
        orari.put(18, random.nextInt(24));
        orari.put(19, 0);

        BarChart bc = (BarChart) view.findViewById(R.id.chart);

        Description desc = new Description();
        desc.setText("Affluenza");
        bc.setDescription(desc);    // Hide the description

        bc.getAxisLeft().setDrawLabels(false);
        bc.getAxisRight().setDrawLabels(false);

        bc.getAxisRight().setDrawAxisLine(false);
        bc.getAxisLeft().setDrawAxisLine(false);
        bc.getXAxis().setEnabled(true);

        bc.getAxisRight().setDrawGridLines(false);
        bc.getAxisLeft().setDrawGridLines(false);
        bc.getXAxis().setDrawGridLines(false);

        bc.setDrawValueAboveBar(false);

        bc.getLegend().setEnabled(false);   // Hide the legend

        List<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i=9; i <= 19; i++){
            entries.add(new BarEntry(i, orari.get(i)));

        }
        BarDataSet dataSet = new BarDataSet(entries, "Affluenza");
//        dataSet.setColor();
//        dataSet.setValueTextColor(...); // styling, ...

        BarData barData = new BarData(dataSet);
        bc.setData(barData);
        bc.invalidate(); // refresh
    }
}
