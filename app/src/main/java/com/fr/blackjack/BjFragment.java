package com.fr.blackjack;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.StackView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BjFragment extends Fragment {


    ///////////////////////////////////////////////////////////
    private StackView stackView;
    private StackView stackView1;
    private Button buttonRester;
    private Button buttonTirer;
    private Button buttonDoubler;
    private Button buttonDiviser;
    private Button buttonBet;
    private View stopView;
    private ConstraintLayout betLayout;
    private TextView total;
    private TextView total1;
    private TextView test1;
    private TextView test2;
    private TextView solde;
    private EditText betInput;
    private int PlayerTotal = 0;
    static int partyi = 0;
    private BjEnd end;
    private Player player;
    private Player player1;
    private CardInventory cardInventory;

    private double bet;
    private double walletSolde;
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    ////////////////////////////////////////////////////////////


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bj, container, false);
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.stackView = (StackView) getView().findViewById(R.id.stackView);
        this.stackView1 = (StackView) getView().findViewById(R.id.stackView1);
        this.buttonTirer = (Button) getView().findViewById(R.id.buttonTirer);
        this.buttonRester = (Button) getView().findViewById(R.id.buttonRester);
        this.buttonDoubler = (Button) getView().findViewById(R.id.buttonDoubler);
        this.stopView = (View) getView().findViewById(R.id.stopView);
        this.buttonBet = (Button) getView().findViewById(R.id.betButton);
        this.betLayout = (ConstraintLayout) getView().findViewById(R.id.betLayout);
        this.betInput = (EditText) getView().findViewById(R.id.betInput);
        this.total = (TextView) getView().findViewById(R.id.total);
        this.total1 = (TextView) getView().findViewById(R.id.total1);
        this.player = new Player();
        this.player1 = new Player();
        this.cardInventory = new CardInventory();
        this.solde = (TextView) getView().findViewById(R.id.solde);
        //initializeParty();
        //stackView.setAdapter(new StackAdapter(this, R.layout.card, player.getMyHand(),true));
        //stackView1.setAdapter(new StackAdapter(this, R.layout.card, player1.getMyHand(),false));
        stackView.setHorizontalScrollBarEnabled(false);
        stackView1.setHorizontalScrollBarEnabled(false);



        ///////////////////////////////// UPDATE DATA /////////////////////////////////////////////////
        dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    if (dsp.child("mail").exists()) {
                        solde.setText(dsp.child("solde").getValue().toString());
                    }}}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //////////////////////////////////////////////////////////////////////////////////




        buttonBet.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot dsp : snapshot.getChildren()) {
                            if (dsp.child("mail").exists()) {
                                //Si valeur mis dans l'input > au solde
                                if (betInput.getText().toString().isEmpty()){
                                    Toast.makeText(getContext(),"Vous devez mettre une mise",Toast.LENGTH_LONG).show();
                                }
                                else{
                                bet = Double.parseDouble(betInput.getText().toString());
                                walletSolde = Double.parseDouble(dsp.child("solde").getValue().toString());
                                if (bet <= walletSolde){
                                    betLayout.setVisibility(ConstraintLayout.INVISIBLE);
                                    total1.setVisibility(TextView.INVISIBLE);
                                    initializeParty();
                                }else{
                                    Toast.makeText(getContext(),"Solde insuffisant",Toast.LENGTH_LONG).show();
                                }
                            }}}}
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
            });

        buttonTirer.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partyi==0){
                    initializeParty();
                }
                else{
                    if(!(partyi>= player.getMyHand().size())){
                        stackView.showPrevious();
                        partyi++;
                        croupierAction();
                        loopTirer(player,player1);
                    }
                }

            }
        });

        buttonRester.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                partyi++;
                loopStand(player,player1);
                croupierAction();
            }
        });



        buttonDoubler.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(partyi==0){
                    initializeParty();
                }
                else{
                    if(!(partyi>= player.getMyHand().size())){
                        stackView.showPrevious();
                        partyi++;
                        bet = bet*2;
                        croupierAction();
                        loopTirer(player,player1);
                    }
                }
            }
        });


        stopView.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View v) { }});

        betLayout.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View v) { }});

    }



    public void initializeParty(){
        partyi=0;
        end=null;
        player.setStandFalse();
        player1.setStandFalse();
        cardInventory.initializeInventory();
        player.setMyHand(cardInventory);
        player1.setMyHand(cardInventory);
        partyi++;
        loopTirer(player,player1);
        croupierAction();
        stackView.setAdapter(new StackAdapter(this.getContext(), R.layout.card, player.getMyHand(),true));
        stackView1.setAdapter(new StackAdapter(this.getContext(), R.layout.card, player1.getMyHand(),false));
        toTheFirstCard();
    }

    public void loopTirer(Player player,Player player1){

        int playerTotal;
        int playerTotal1;
        Card actualCardValue = player.getMyHand().get(player.getMyHand().size()-partyi-1);

        if(!(player.isStand)){
            playerTotal= player.setTotal(partyi);
        }
        if(!(player1.isStand)){
            playerTotal1= player1.setTotal(partyi);
        }

        winTest(player,player1);
        if(!(end==null)){
            winLoseEquality();
        }

        //////////Rafraîchissement score

        total.setText(String.valueOf(player.getMyHandValue()));
        ////////////////////////////////
    }

    public void loopStand(Player player,Player player1){
        // int playerTotal1;
        player.setStandTrue();
        //playerTotal1= player1.getTotal(partyi);

        //////////Rafraîchissement score
        while(!(player1.isStand)){
            Log.i("String","action bot");
            partyi++;
            croupierAction();
            winTest(player,player1);
        }
        winTest(player,player1);
        total.setText(String.valueOf(player.getMyHandValue()));
        ////////////////////////////////
    }

    public void croupierAction(){
        if (!(player1.getMyHandValue()>=17)){
            stackView1.showPrevious();
            loopTirer(player,player1);
        }else{
            player1.setStandTrue();
        }
    }

    public void winTest(Player player,Player player1){
        int playerTotal =player.getMyHandValue();
        int playerTotal1 =player1.getMyHandValue();
        blackjackTest(player.getMyHandValue(),player1.getMyHandValue());

        if(end==null){
            if (player.isStand&&player1.isStand){
                if (playerTotal>playerTotal1){
                    end = BjEnd.WIN;
                }else if(playerTotal==playerTotal1){
                    end = BjEnd.EQUALITY;
                    Log.i("String","égualité (winTest): "+player.getMyHandValue()+" == "+player1.getMyHandValue()+"/"+playerTotal+" == "+playerTotal1);
                }else{
                    end = BjEnd.LOSE;
                }
            }
            winLoseEquality();
        }

    }

    public void blackjackTest(int playerTotal,int playerTotal1){

        if(playerTotal>21){
            //si au dessus de 21 -> perdu
            end = BjEnd.LOSE;

        }else if(playerTotal1>21){
            end = BjEnd.WIN;
        }
        else if(playerTotal==21){
            if(playerTotal==playerTotal1){
                Log.i("String","égualité (bjTest): "+player.getMyHandValue());
                //si blackjack des deux cotés -> égalité
                end = BjEnd.EQUALITY;
            }
            else{
                //si blackjack -> gagné
                end = BjEnd.WIN;
            }
        }

    }

    public void winLoseEquality() {

        dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    if (dsp.child("mail").exists()) {
                        //Si valeur mis dans l'input > au solde
                        if (!(end == null)) {
                            switch (end) {
                                case WIN:
                                    Log.i("String", "gagné");
                                    dsp.getRef().child("solde").setValue(Integer.valueOf(dsp.child("solde").getValue().toString()) + bet);
                                    dsp.getRef().child("gagne").setValue(Integer.valueOf(dsp.child("gagne").getValue().toString()) + 1);
                                    ToastWinLose(bet,0);
                                    break;

                                case LOSE:
                                    Log.i("String", "perdu");
                                    dsp.getRef().child("solde").setValue(Integer.valueOf(dsp.child("solde").getValue().toString()) - bet);
                                    dsp.getRef().child("perdu").setValue(Integer.valueOf(dsp.child("perdu").getValue().toString()) + 1);
                                    ToastWinLose(bet,1);
                                    break;

                                case EQUALITY:
                                    Log.i("String", "égualité");
                                    ToastWinLose(bet,2);
                                    break;
                            }
                            resetBetLayout();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    public void resetBetLayout() {
        dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    if (dsp.child("mail").exists()) {
                            total1.setVisibility(TextView.VISIBLE);
                            solde.setText(dsp.child("solde").getValue().toString());
                            total1.setText(String.valueOf(player1.getMyHandValue()));
                            betLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    public void toTheFirstCard(){
        for(int i=0;i<11;i++){
            stackView.showNext();
            stackView1.showNext();
        }
    }
    public void ToastWinLose(double bet,int winLoseEquality){
        Toast toast;
        View view;

        switch (winLoseEquality){
            case 0:
                toast = Toast.makeText(getContext(), "Win +"+bet, Toast.LENGTH_LONG);
                view = toast.getView();
                break;

            case 1 :
                toast = Toast.makeText(getContext(), "Lose -"+bet, Toast.LENGTH_LONG);

                break;

            case 2 :
                toast = Toast.makeText(getContext(), "EQUALITY tu récupère "+bet, Toast.LENGTH_LONG);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + winLoseEquality);
        }
        view = toast.getView();
        TextView text = (TextView) view.findViewById(android.R.id.message);
        Typeface font = ResourcesCompat.getFont(getContext(), R.font.kanit);
        text.setTypeface(font);
        Drawable leftDrawable = getResources().getDrawable(R.drawable.ic_claim);
        text.setCompoundDrawables(leftDrawable, null , null, null);

        text.setTextSize(30);
        switch (winLoseEquality) {
            case 0:
                text.setTextColor(Color.parseColor("#2ecc71"));
                break;
            case 1:
                text.setTextColor(Color.parseColor("#e74c3c"));
                break;
            case 2:
                text.setTextColor(Color.DKGRAY);
                break;
        }
        toast.show();
    }

    //------------------------------Test--------------------------------------------------
    public void testCardInventory(){
        for(int i=0;i<51;i++){
            if (cardInventory.inventory[i] != null){
                Log.i("String", String.valueOf(i)+":    "+cardInventory.inventory[i].getString_value()+" + "+cardInventory.inventory[i].getSymbol() );
            }
            else{
                Log.i("String", String.valueOf(i)+":    "+String.valueOf(0));
            }
        }
    }

    public void testPlayer1(){
        Log.i("String","Test player1");
        for(int i=0;i<11;i++){
            if (player1.getMyHand().get(i) != null){
                Log.i("String", String.valueOf(i)+":    "+player1.getMyHand().get(i).getString_value()+" + "+player1.getMyHand().get(i).getSymbol()+"   id:"+player1.getMyHand().get(i).getId());
            }
            else{
                Log.i("String", String.valueOf(i)+":    "+String.valueOf(0));
            }
        }
    }

    public void testPlayer(){
        Log.i("String","Test player");
        for(int i=0;i<11;i++){
            if (player.getMyHand().get(i) != null){
                Log.i("String", String.valueOf(i)+":    "+player.getMyHand().get(i).getString_value()+" + "+player.getMyHand().get(i).getSymbol()+"   id:"+player.getMyHand().get(i).getId());
            }
            else{
                Log.i("String", String.valueOf(i)+":    "+String.valueOf(0));
            }
        }
    }




    //-----------------------------------Get---------------------------------------------


    public static int getPartyi() {
        return partyi;
    }

    public Player getPlayer1() {
        return player1;
    }

    public CardInventory getCardInventory() {
        return cardInventory;
    }
}

