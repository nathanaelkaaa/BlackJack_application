package com.fr.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.StackView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


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
    private int PlayerTotal = 0;
    static int partyi=0;
    private BjEnd end;
    private Player player;
    private Player player1;
    private CardInventory cardInventory;

////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//////////////////////////////////////////////////////////////

        this.stackView = (StackView) findViewById(R.id.stackView);
        this.stackView1 = (StackView) findViewById(R.id.stackView1);
        this.buttonTirer = (Button) findViewById(R.id.buttonTirer);
        this.buttonRester = (Button) findViewById(R.id.buttonRester);
        this.buttonDoubler = (Button) findViewById(R.id.buttonDoubler);
        this.buttonDiviser = (Button) findViewById(R.id.buttonDiviser);
        this.stopView = (View) findViewById(R.id.stopView);
        this.buttonBet = (Button) findViewById(R.id.betButton);
        this.betLayout = (ConstraintLayout) findViewById(R.id.betLayout);
        this.total = (TextView) findViewById(R.id.total);
        this.total1 = (TextView) findViewById(R.id.total1);
        this.player = new Player();
        this.player1 = new Player();
        this.cardInventory = new CardInventory();
        this.test1 = (TextView) findViewById(R.id.test1);
        this.test2 = (TextView) findViewById(R.id.test2);
        //initializeParty();
        //stackView.setAdapter(new StackAdapter(this, R.layout.card, player.getMyHand(),true));
        //stackView1.setAdapter(new StackAdapter(this, R.layout.card, player1.getMyHand(),false));
        stackView.setHorizontalScrollBarEnabled(false);
        stackView1.setHorizontalScrollBarEnabled(false);


        buttonBet.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                betLayout.setVisibility(ConstraintLayout.INVISIBLE);
                total1.setVisibility(TextView.INVISIBLE);
                initializeParty();
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
            testPlayer();
            }
        });

        buttonDiviser.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                testPlayer1();
            }
        });



        stopView.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View v) { }});

        betLayout.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View v) { }});

        //////////////////////////////////////////////////////////////////////
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
        stackView.setAdapter(new StackAdapter(this, R.layout.card, player.getMyHand(),true));
        stackView1.setAdapter(new StackAdapter(this, R.layout.card, player1.getMyHand(),false));
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
        test1.setText(String.valueOf(player.isStand)+" + "+String.valueOf(player.getMyHandValue()));
        test2.setText(String.valueOf(player1.isStand)+" + "+String.valueOf(player1.getMyHandValue()));
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
        total.setText(String.valueOf(player.getMyHandValue()));
        test1.setText(String.valueOf(player.isStand)+" + "+String.valueOf(player.getMyHandValue()));
        test2.setText(String.valueOf(player1.isStand)+" + "+String.valueOf(player1.getMyHandValue()));
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

    public void winLoseEquality(){
        if (!(end==null)){
            switch (end){
                case WIN:
                    Log.i("String","gagné");
                    break;

                case LOSE:
                    Log.i("String","perdu");
                    break;

                case EQUALITY:
                    Log.i("String","égualité");
                    break;
            }
            total1.setVisibility(TextView.VISIBLE);
            total1.setText(String.valueOf(player1.getMyHandValue()));
            betLayout.setVisibility(View.VISIBLE);
            //initializeParty();
        }
    }


    public void toTheFirstCard(){
            for(int i=0;i<11;i++){
                stackView.showNext();
                stackView1.showNext();
            }
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