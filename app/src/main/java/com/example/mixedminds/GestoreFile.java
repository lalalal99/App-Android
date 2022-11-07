package com.example.mixedminds;

import android.content.Context;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestoreFile {

    String filepath;
    String filename;

    public GestoreFile(){
        this.filepath = "myDir";
        this.filename = "save_file.txt";

    }

    public void saveFile(Context context, String prenotazione) throws IOException {
        File saveFile = new File(context.getExternalFilesDir(filepath), filename);
        FileOutputStream fos = null;
        fos = new FileOutputStream(saveFile, true);
        fos.write(prenotazione.getBytes());
    }


    public List<List<String>> readFile(Context context) {
        /**
         * Ritorna un array in cui in ogni riga c'è una prenotazione
         * */

        List<List<String>> prenotazioni = new ArrayList<>();
        FileReader fr = null;
        File saveFile = new File(context.getExternalFilesDir(filepath), filename);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fr = new FileReader(saveFile);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null){
                stringBuilder.append(line).append("\n");
                line = br.readLine();
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            List<String> lista = Arrays.asList(stringBuilder.toString().split("\n")); //lista con dentro le prenotazioni in stringa
//            System.out.println(Arrays.toString(lista.toArray()));

            if (!lista.get(0).equals("")) {
//                System.out.println("so mboccato2");
                for (int i=0; i < lista.size(); i++) {
                    prenotazioni.add(Arrays.asList(lista.get(i).split(" ")));
                }
            }

        }

        return prenotazioni;
    }

    public void modificaFile(Context context, String prenotazione) throws IOException {
        File saveFile = new File(context.getExternalFilesDir(filepath), filename);
        FileOutputStream fos = null;
        fos = new FileOutputStream(saveFile);
        fos.write(prenotazione.getBytes());

    }
}
