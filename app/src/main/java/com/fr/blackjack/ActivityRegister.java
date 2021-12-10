package com.fr.blackjack;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;

public class ActivityRegister extends AppCompatActivity {
    private Button button_register;
    private EditText inputpseudo;
    private EditText inputemail;
    private EditText inputmdp;
    private EditText inputcmdp;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //android.graphics.drawable.Drawable background = ActivityRegister.this.getResources().getDrawable(R.drawable.background_gradient);
        //getWindow().setBackgroundDrawable(background);  //Pour le fond


        dbRef = FirebaseDatabase.getInstance().getReference();

        inputpseudo = (EditText) findViewById(R.id.inputpseudo);
        inputemail = (EditText) findViewById(R.id.inputemail);
        inputmdp = (EditText) findViewById(R.id.inputmdp);
        inputcmdp = (EditText) findViewById(R.id.inputcmdp);
        button_register = (Button) findViewById(R.id.button_register);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("String","Click :");
                if (!inputpseudo.getText().toString().matches("") && !inputemail.getText().toString().matches("") && !inputmdp.getText().toString().matches("") && !inputcmdp.getText().toString().matches("") && inputmdp.getText().toString().matches(inputcmdp.getText().toString())) {
                    final boolean[] alreadycompte = {false};
                    Log.i("String","-entrée dans le if");

                    dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            Log.i("String","-entrée dans child");
                            for (DataSnapshot dsp : snapshot.getChildren()) {
                                if (dsp.child("mail").exists()) {
                                    if (dsp.child("mail").getValue().toString().equalsIgnoreCase(inputemail.getText().toString())){
                                        alreadycompte[0] = true;
                                    }
                                }
                            }
                            if (alreadycompte[0] == false){
                                Log.i("String","-compte existe pas");
                                User newUser = new User(inputmdp.getText().toString(),inputemail.getText().toString(),inputpseudo.getText().toString(),"",0,0,0,0);
                                Log.i("String","Ajout nv user");
                                String key = dbRef.child("users").push().getKey();
                                dbRef.child("users").child(key).setValue(newUser);
                                Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                                startActivity(intent);
                                FileOutputStream fos = null;

                            }else{
                                Log.i("String","-compte existe");
                                Toast.makeText(getApplicationContext(),"Cette email est déja utilisé !",Toast.LENGTH_LONG).show();
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
    }

}
