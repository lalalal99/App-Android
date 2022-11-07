package com.example.mixedminds;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ContattiFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // calling the action bar
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(false);
        return inflater.inflate(R.layout.fragment_contatti, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupHyperlink();
    }

    private void setupHyperlink() {
        TextView linkTextViewF = getActivity().findViewById(R.id.txtAccountFacebook);
        TextView linkTextViewT = getActivity().findViewById(R.id.txtAccountTwitter);
        TextView linkTextViewP = getActivity().findViewById(R.id.txtNumTel);
        TextView linkTextViewE = getActivity().findViewById(R.id.txtIndEmail);
        linkTextViewF.setMovementMethod(LinkMovementMethod.getInstance());
        linkTextViewT.setMovementMethod(LinkMovementMethod.getInstance());
        linkTextViewP.setMovementMethod(LinkMovementMethod.getInstance());
        linkTextViewE.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
