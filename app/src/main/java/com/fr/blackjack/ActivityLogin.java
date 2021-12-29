package com.fr.blackjack;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActivityLogin extends AppCompatActivity {
    private Button button_login;
    private TextView registertext;
    private EditText inputlemail;
    private EditText inputlmdp;
    private static final String FILE_NAME = "ke.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //android.graphics.drawable.Drawable background = ActivityLogin.this.getResources().getDrawable(R.drawable.background_gradient);
        //getWindow().setBackgroundDrawable(background);


        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        inputlemail = (EditText) findViewById(R.id.inputlemail);
        inputlmdp = (EditText) findViewById(R.id.inputlmdp);

        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            dbRef.child("users").child(sb.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.child("pseudo").exists()) {
                        Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                        startActivity(intent);
                        MainActivity.email = snapshot.child("mail").getValue().toString();
                        Toast.makeText(getApplicationContext(),"Bon retour "+snapshot.child("pseudo").getValue().toString()+" !",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        button_login = (Button) findViewById(R.id.button_register);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputlemail.getText().toString().matches("") && !inputlmdp.getText().toString().matches("")){
                    final boolean[] exists = {false};
                    final String[] key = new String[1];
                    dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot dsp : snapshot.getChildren()) {
                                if (dsp.child("mail").exists()) {
                                    if (dsp.child("mail").getValue().toString().equalsIgnoreCase(inputlemail.getText().toString()) && dsp.child("mdp").getValue().toString().equalsIgnoreCase(inputlmdp.getText().toString())){
                                        exists[0] = true;
                                        MainActivity.email = inputlemail.getText().toString();
                                        key[0] = dsp.getKey();
                                    }
                                }
                            }
                            if (exists[0] == true){
                                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                                intent.putExtra("email",inputlemail.getText().toString());
                                startActivity(intent);
                                FileOutputStream fos = null;
                                try {
                                    fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
                                    fos.write(key[0].getBytes());
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }finally {
                                    if (fos != null){
                                        try {
                                            fos.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"Mauvais mdp ou email!",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Un champs est invalide !",Toast.LENGTH_LONG).show();
                }

            }
        });

        registertext = (TextView) findViewById(R.id.registertext);
        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
            }
        });


    }


}
