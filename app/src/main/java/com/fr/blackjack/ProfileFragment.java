package com.fr.blackjack;


import static java.util.Calendar.HOUR;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class ProfileFragment extends Fragment {

    private Button button_claim;
    private TextView pseudo;
    private TextView gagne;
    private TextView perdu;
    private TextView ratio;
    private TextView solde;
    private int win,lose;
    private double ratioWL;
    static int ONEHOUR = 60 * 60 * 1000;
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        button_claim = (Button) getView().findViewById(R.id.claim_button);
        pseudo = (TextView) getView().findViewById(R.id.testView);
        gagne = (TextView) getView().findViewById(R.id.gagné);
        perdu = (TextView) getView().findViewById(R.id.perdu);
        ratio = (TextView) getView().findViewById(R.id.ratio);
        solde = (TextView) getView().findViewById(R.id.solde);



        ///////////////////////////////// UPDATE DATA /////////////////////////////////////////////////
        dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    if (dsp.child("mail").exists() && dsp.child("mail").getValue().toString().equals(MainActivity.email)) {
                        Log.i("String", "Test -- "+dsp.child("solde").getValue().toString());
                        win  =  Integer.parseInt(dsp.child("gagne").getValue().toString());
                        lose =  Integer.parseInt(dsp.child("perdu").getValue().toString());
                        Log.i("String","email = "+MainActivity.email);
                        ratioWL = calculRatio(win,lose);
                        pseudo.setText(dsp.child("pseudo").getValue().toString());
                        gagne.setText(String.valueOf(win));
                        perdu.setText(String.valueOf(lose));
                        Log.i("String", "ratio = "+ratioWL);
                        ratio.setText( String.valueOf(ratioWL));
                        solde.setText(dsp.child("solde").getValue().toString());
                            //int r = (int) (Math.random() * (10000 - 1000)) + 1000;
                    }}}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //////////////////////////////////////////////////////////////////////////////////




        button_claim.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot dsp : snapshot.getChildren()) {
                            if (dsp.child("mail").exists() && dsp.child("mail").getValue().toString().equals(MainActivity.email)) {
                                Date date = new Date();

                                long lastDate = Long.valueOf(dsp.child("rewardClock").getValue().toString()) + 1800000;
                                if (lastDate == 0 || date.getTime()>(lastDate)){
                                    Log.i("String","1h après ");
                                    int r = (int) (Math.random() * (10000 - 1000)) + 1000;
                                    dsp.getRef().child("solde").setValue(Integer.valueOf(dsp.child("solde").getValue().toString())+r);
                                    dsp.getRef().child("rewardClock").setValue(date.getTime());
                                    updateSold();
                                }else{
                                    solde.setText(dsp.child("solde").getValue().toString());
                                    double tempsRestant = (lastDate-date.getTime())*1.6666666666667E-5 ;
                                    int minutesRestantes = (int)tempsRestant;
                                    Toast.makeText(getContext(),"Vous pourez réclamer dans "+minutesRestantes+" minutes",Toast.LENGTH_LONG).show();
                                }
                                //3600000

                            }}}
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });
    }

    public double calculRatio(int win1, int lose1){
        double result;
        double win = (double) win1;
        double lose = (double) lose1;
        Log.i("String","win ="+win+ " lose ="+lose);
        if (win == 0){
            result = 0;
        }else if (lose == 0){
            result = win;
        }else{
            result = win/lose;
        }
        return result;
    }

    public void updateSold(){
        dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    if (dsp.child("mail").exists() && dsp.child("mail").getValue().toString().equals(MainActivity.email)) {
                        solde.setText(dsp.child("solde").getValue().toString());
                    }}}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
