package com.fr.blackjack;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Card> myHand;
    List<Card> myAsHand;
    boolean isStand=false;
    int myHandValue;


    public Player (){
        this.myHand = new ArrayList<Card>();
    }

    public void setMyHand(CardInventory cardInventory) {
        int r ;

        while(myHand.size()>0){
            myHand.remove(0);
        }

        r = (int) (Math.random() * 51);
        for(int i = 0; i<11; i++){
            while (cardInventory.inventory[r]==null){
                r = (int) (Math.random() * 51);
            }
            //Log.i("String", String.valueOf(r)+" : "+cardInventory.inventory[r].getString_value()+" + "+cardInventory.inventory[r].getSymbol());


            this.myHand.add(cardInventory.inventory[r]);
            cardInventory.inventory[r]=null;
        }
    }

    public void setStandTrue() {
        isStand=true;
    }
    public void setStandFalse() {
        isStand=false;
    }

    public List<Card> getMyHand() {
        return myHand;
    }

    public int getMyHandValue() { return myHandValue; }

    public List<Card> getMyAsHand() {
        return myAsHand;
    }

    public int setTotal(int partyi){
        int total = 0;
        int nbAs = 0;

       for(int i=myHand.size()-1; i>=(myHand.size()-partyi); i--){
           if(myHand.get(i).getCard_value()==CardValue.AS){
               nbAs++;
           }
           total = total + myHand.get(i).getInt_value();
       }

       for(int b=0; b<nbAs; b++){
            if((total+11)>21){
                total= total+1;
            }
            else {
                total= total+11;
       }}


        myHandValue=total;
        return total;
    }

}
