package com.example.mixedminds;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class PrenotazioniFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Prenotazioni");
        final Calendar calendar = Calendar.getInstance();
//        System.out.println("giorno: ");
//        System.out.println(Calendar.DAY_OF_WEEK);

        // calling the action bar
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(false);


        List<List<String>> prenotazioni = null;
        View view = inflater.inflate(R.layout.fragment_prenotazioni, container, false);

        final LayoutInflater factory = getLayoutInflater();

        final View textEntryView = factory.inflate(R.layout.activity_main, null);

        BottomNavigationView bottomNav = textEntryView.findViewById(R.id.bottom_navigation);
//        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_prenotazioni);

        GestoreFile gs = new GestoreFile();
        prenotazioni = gs.readFile(this.getContext());

        Button button = (Button) view.findViewById(R.id.buttonPrenota);
        button.setOnClickListener(v -> {
            if (view.findViewById(R.id.card1).getVisibility() == View.INVISIBLE)
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, new PrenotazioniNuovaPrenFragment()).addToBackStack("bla").commit();
            else
                Toast.makeText(getActivity().getBaseContext(), "Massimo due prenotazioni disponibili in questa versione. Cancellane una per continuare.",Toast.LENGTH_LONG).show();
        });

        if (!prenotazioni.isEmpty())
            creaPrenotazioni(view, prenotazioni);

        cancellaPrenotazioni(view, prenotazioni);

        ImageView popup = (ImageView) view.findViewById(R.id.qrCodeCard);
        popup.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putBoolean("popUp", true);
            Fragment fr = new PrenotazioneEffettuataFragment();
            fr.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, fr).commit();
        });

        Button btnMod = (Button) view.findViewById(R.id.btnPrenCard);
        Button btnMod1 = (Button) view.findViewById(R.id.btnPrenCard1);
//        ImageView imgMod3 = (ImageView) view.findViewById(R.id.imgPrenMod3);

        Bundle modifica = new Bundle();

        Fragment fr = new ModificaPrenFragment();
        fr.setArguments(modifica);

        List<List<String>> finalPrenotazioni = prenotazioni;
        btnMod.setOnClickListener(v -> {
            TextView txtData = (TextView) view.findViewById(R.id.txtPrenData);
            TextView txtOra = (TextView) view.findViewById(R.id.txtPrenOra);
            ImageView imgEuro = (ImageView) view.findViewById(R.id.imgCardEuro);
            TextView prenotati = view.findViewById(R.id.txtPrenotati);
            modifica.putBoolean("inApp", imgEuro.getVisibility() == View.VISIBLE);
            modifica.putString("Data", txtData.getText().toString());
            modifica.putString("Ora", txtOra.getText().toString());
            modifica.putString("Prenotati", prenotati.getText().toString());
            modifica.putInt("index", 0);

            try {
                modifica.putString("stringa", modificaPren(v, 0, finalPrenotazioni));
            } catch (IOException e) {
                e.printStackTrace();
            }
            fr.setArguments(modifica);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, fr).addToBackStack("bla").commit();
        });

        btnMod1.setOnClickListener(v -> {
            TextView txtData = (TextView) view.findViewById(R.id.txtPrenData1);
            TextView txtOra = (TextView) view.findViewById(R.id.txtPrenOra1);
            ImageView imgEuro = (ImageView) view.findViewById(R.id.imgCardEuro1);
            TextView prenotati = view.findViewById(R.id.txtPrenotati1);
            modifica.putBoolean("inApp", imgEuro.getVisibility() == View.VISIBLE);
            modifica.putString("Data", txtData.getText().toString());
            modifica.putString("Ora", txtOra.getText().toString());
            modifica.putString("Prenotati", prenotati.getText().toString());
            modifica.putInt("index", 1);

            try {
                modifica.putString("stringa", modificaPren(v, 1, finalPrenotazioni));
            } catch (IOException e) {
                e.printStackTrace();
            }
            fr.setArguments(modifica);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, fr).addToBackStack("bla").commit();
        });
//
//        imgMod3.setOnClickListener(v -> {
//            TextView txtData = (TextView) view.findViewById(R.id.txtPrenData3);
//            TextView txtOra = (TextView) view.findViewById(R.id.txtPrenOra3);
//            ImageView imgEuro = (ImageView) view.findViewById(R.id.imgPrenEur3);
//            modifica.putBoolean("inApp", imgEuro.getVisibility() == View.VISIBLE);
//            modifica.putString("Data", txtData.getText().toString());
//            modifica.putString("Ora", txtOra.getText().toString());
//            modifica.putInt("index", 2);
//
//            try {
//                modificaPren(v, 2, finalPrenotazioni, true);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, fr).commit();
//        });

        return view;

    }

    public String modificaPren(View v, int index, List<List<String>> prenotazioni) throws IOException {

//        prenotazioni.remove(index);

//        GestoreFile gs = new GestoreFile();
        String stringa = "";
        for (int i=0; i < prenotazioni.size();i++){
            stringa = stringa.concat(prenotazioni.get(i).get(0));
            stringa = stringa.concat(" " + prenotazioni.get(i).get(1));
            stringa = stringa.concat(" " + prenotazioni.get(i).get(2));
            stringa = stringa.concat(" " + prenotazioni.get(i).get(3)+ "\n");
        }
        //è chiamato dal bottone modifica della card, ritorna la stringa qui sopra, viene passata come argomento, insieme a index, a modifica pren fragment, li dentro ci sono
        //remove, modifica file e resto
//        gs.modificaFile(this.getContext(), stringa);
        return stringa;
    }

    private void creaPrenotazioni(View view, List<List<String>> prenotazioni) {

        TextView txtPrenData = (TextView) view.findViewById(R.id.txtPrenData);
        TextView txtPrenOra = (TextView) view.findViewById(R.id.txtPrenOra);
        ImageView imgPrenEuro = (ImageView) view.findViewById(R.id.imgCardEuro);
        TextView txtPrenotati = (TextView) view.findViewById(R.id.txtPrenotati);
        TextView txtPrenotatiPersone = (TextView) view.findViewById(R.id.txtPrenotatiPersone);
        CardView card = view.findViewById(R.id.card);

        TextView txtPrenData1 = (TextView) view.findViewById(R.id.txtPrenData1);
        TextView txtPrenOra1 = (TextView) view.findViewById(R.id.txtPrenOra1);
        ImageView imgPrenEuro1 = (ImageView) view.findViewById(R.id.imgCardEuro1);
        TextView txtPrenotati1 = (TextView) view.findViewById(R.id.txtPrenotati1);
        TextView txtPrenotatiPersone1 = (TextView) view.findViewById(R.id.txtPrenotatiPersone1);
        CardView card1 = view.findViewById(R.id.card1);
//        ImageView imgPrenMod1 = (ImageView) view.findViewById(R.id.imgPrenMod1);
//        ImageView imgPrenCanc1 = (ImageView) view.findViewById(R.id.imgPrenCanc1);
//        View linea1 = (View) view.findViewById(R.id.linea1);

//        TextView txtPrenData2 = (TextView) view.findViewById(R.id.txtPrenData2);
//        TextView txtPrenOra2 = (TextView) view.findViewById(R.id.txtPrenOra2);
//        ImageView imgPrenEuro2 = (ImageView) view.findViewById(R.id.imgPrenEur2);
//        ImageView imgPrenMod2 = (ImageView) view.findViewById(R.id.imgPrenMod2);
//        ImageView imgPrenCanc2 = (ImageView) view.findViewById(R.id.imgPrenCanc2);
//        View linea2 = (View) view.findViewById(R.id.linea2);
//
//        TextView txtPrenData3 = (TextView) view.findViewById(R.id.txtPrenData3);
//        TextView txtPrenOra3 = (TextView) view.findViewById(R.id.txtPrenOra3);
//        ImageView imgPrenEuro3 = (ImageView) view.findViewById(R.id.imgPrenEur3);
//        ImageView imgPrenMod3 = (ImageView) view.findViewById(R.id.imgPrenMod3);
//        ImageView imgPrenCanc3 = (ImageView) view.findViewById(R.id.imgPrenCanc3);



//        far apparire le prenotazioni a seconda del file
//        controlla prima riga: se invisibile mettici le cose, se invisibile controlla la prossima
        try {
//            System.out.println(prenotazioni.get(0));

            if (!prenotazioni.get(0).isEmpty() && card.getVisibility() == View.INVISIBLE) {
                card.setVisibility(View.VISIBLE);
//                System.out.println("so mboccato");
//                linea1.setVisibility(View.VISIBLE);
//                txtPrenData1.setVisibility(View.VISIBLE);
//                txtPrenOra1.setVisibility(View.VISIBLE);
//                imgPrenMod1.setVisibility(View.VISIBLE);
//                imgPrenCanc1.setVisibility(View.VISIBLE);

                txtPrenData.setText(prenotazioni.get(0).get(0));
                txtPrenOra.setText(prenotazioni.get(0).get(1));
                if (prenotazioni.get(0).get(2).equals("si"))
                    imgPrenEuro.setVisibility(View.VISIBLE);
                else
                    imgPrenEuro.setVisibility(View.INVISIBLE);
                txtPrenotati.setText(prenotazioni.get(0).get(3));
                txtPrenotatiPersone.setText(!prenotazioni.get(0).get(3).equals(String.valueOf(1)) ? " persone" : " persona");
            }

            if (!prenotazioni.get(1).isEmpty() && card1.getVisibility() == View.INVISIBLE) {
                card1.setVisibility(View.VISIBLE);
//                linea2.setVisibility(View.VISIBLE);
//                txtPrenData2.setVisibility(View.VISIBLE);
//                txtPrenOra2.setVisibility(View.VISIBLE);
//                imgPrenMod2.setVisibility(View.VISIBLE);
//                imgPrenCanc2.setVisibility(View.VISIBLE);

                txtPrenData1.setText(prenotazioni.get(1).get(0));
                txtPrenOra1.setText(prenotazioni.get(1).get(1));
                if (prenotazioni.get(1).get(2).equals("si"))
                    imgPrenEuro1.setVisibility(View.VISIBLE);
                else
                    imgPrenEuro1.setVisibility(View.INVISIBLE);
                txtPrenotati1.setText(prenotazioni.get(1).get(3));
                txtPrenotatiPersone1.setText(!prenotazioni.get(1).get(3).equals(String.valueOf(1)) ? " persone" : " persona");
            }
//
//            if (!prenotazioni.get(2).isEmpty() && txtPrenData3.getVisibility() == View.INVISIBLE) {
//                txtPrenData3.setVisibility(View.VISIBLE);
//                txtPrenOra3.setVisibility(View.VISIBLE);
//                imgPrenMod3.setVisibility(View.VISIBLE);
//                imgPrenCanc3.setVisibility(View.VISIBLE);
//
//                txtPrenData3.setText(prenotazioni.get(2).get(0));
//                txtPrenOra3.setText(prenotazioni.get(2).get(1));
//                if (prenotazioni.get(2).get(2).equals("si"))
//                    imgPrenEuro3.setVisibility(View.VISIBLE);
//            }
        } catch (IndexOutOfBoundsException e){

        }
    }

    private void cancellaPrenotazioni(View view, List<List<String>> prenotazioni) {

        Button imgCanc = (Button) view.findViewById(R.id.btnCancCard);
        Button imgCanc1 = (Button) view.findViewById(R.id.btnCancCard1);
//        ImageView imgCanc3 = (ImageView) view.findViewById(R.id.imgPrenCanc3);
        GestoreFile gs = new GestoreFile();

        List<List<String>> finalPrenotazioni = prenotazioni;

        imgCanc.setOnClickListener(v -> {
            try {
                finalPrenotazioni.remove(0);
                String stringa = "";
                for (int i=0; i < finalPrenotazioni.size();i++){
                    stringa = stringa.concat(finalPrenotazioni.get(i).get(0));
                    stringa = stringa.concat(" " + finalPrenotazioni.get(i).get(1));
                    stringa = stringa.concat(" " + finalPrenotazioni.get(i).get(2));
                    stringa = stringa.concat(" " + finalPrenotazioni.get(i).get(3)+ "\n");
                }
                gs.modificaFile(this.getContext(), stringa);
//                modificaPren(v, 0, finalPrenotazioni);
            } catch (IOException e) {
                e.printStackTrace();
            }
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, new PrenotazioniFragment()).commit();
        });

        imgCanc1.setOnClickListener(v -> {
            try {
                finalPrenotazioni.remove(1);
                String stringa = "";
                for (int i=0; i < finalPrenotazioni.size();i++){
                    stringa = stringa.concat(finalPrenotazioni.get(i).get(0));
                    stringa = stringa.concat(" " + finalPrenotazioni.get(i).get(1));
                    stringa = stringa.concat(" " + finalPrenotazioni.get(i).get(2));
                    stringa = stringa.concat(" " + finalPrenotazioni.get(i).get(3)+ "\n");
                }
                gs.modificaFile(this.getContext(), stringa);
//                modificaPren(v, 1, finalPrenotazioni);
            } catch (IOException e) {
                e.printStackTrace();
            }
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, new PrenotazioniFragment()).commit();
        });
//        imgCanc3.setOnClickListener(v -> {
//            try {
//                modificaPren(v, 2, finalPrenotazioni, true);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, new PrenotazioniFragment()).commit();
//        });
    }
}

























