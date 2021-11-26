package com.fr.blackjack;

import android.util.Log;

public class CardInventory {
    Card [] inventory;

    public CardInventory(){
        inventory = new Card[52];
        initializeInventory();
    }

    public Card[] getCardInventory() {
        return this.inventory;
    }

    public void setCardInventory(Card[] tab) {
        this.inventory = tab;
    }

    public void addCard(int id,CardValue card_value,Symbol symbol) {
        inventory[id]= new Card(id,card_value,symbol);
    }

    public void initializeInventory(){
        inventory[0]= new Card(0,CardValue.AS,Symbol.TREFLE);
        inventory[1]= new Card(1,CardValue.DEUX,Symbol.TREFLE);
        inventory[2]= new Card(2,CardValue.TROIS,Symbol.TREFLE);
        inventory[3]= new Card(3,CardValue.QUATRE,Symbol.TREFLE);
        inventory[4]= new Card(4,CardValue.CINQ,Symbol.TREFLE);
        inventory[5]= new Card(5,CardValue.SIX,Symbol.TREFLE);
        inventory[6]= new Card(6,CardValue.SEPT,Symbol.TREFLE);
        inventory[7]= new Card(7,CardValue.HUIT,Symbol.TREFLE);
        inventory[8]= new Card(8,CardValue.NEUF,Symbol.TREFLE);
        inventory[9]= new Card(9,CardValue.DIX,Symbol.TREFLE);
        inventory[10]= new Card(10,CardValue.VALET,Symbol.TREFLE);
        inventory[11]= new Card(11,CardValue.DAME,Symbol.TREFLE);
        inventory[12]= new Card(12,CardValue.ROI,Symbol.TREFLE);


        inventory[13]= new Card(13,CardValue.AS,Symbol.COEUR);
        inventory[14]= new Card(14,CardValue.DEUX,Symbol.COEUR);
        inventory[15]= new Card(15,CardValue.TROIS,Symbol.COEUR);
        inventory[16]= new Card(16,CardValue.QUATRE,Symbol.COEUR);
        inventory[17]= new Card(17,CardValue.CINQ,Symbol.COEUR);
        inventory[18]= new Card(18,CardValue.SIX,Symbol.COEUR);
        inventory[19]= new Card(19,CardValue.SEPT,Symbol.COEUR);
        inventory[20]= new Card(20,CardValue.HUIT,Symbol.COEUR);
        inventory[21]= new Card(21,CardValue.NEUF,Symbol.COEUR);
        inventory[22]= new Card(22,CardValue.DIX,Symbol.COEUR);
        inventory[23]= new Card(23,CardValue.VALET,Symbol.COEUR);
        inventory[24]= new Card(24,CardValue.DAME,Symbol.COEUR);
        inventory[25]= new Card(25,CardValue.ROI,Symbol.COEUR);


        inventory[26]= new Card(26,CardValue.AS,Symbol.PIQUE);
        inventory[27]= new Card(27,CardValue.DEUX,Symbol.PIQUE);
        inventory[28]= new Card(28,CardValue.TROIS,Symbol.PIQUE);
        inventory[29]= new Card(29,CardValue.QUATRE,Symbol.PIQUE);
        inventory[30]= new Card(30,CardValue.CINQ,Symbol.PIQUE);
        inventory[31]= new Card(31,CardValue.SIX,Symbol.PIQUE);
        inventory[32]= new Card(32,CardValue.SEPT,Symbol.PIQUE);
        inventory[33]= new Card(33,CardValue.HUIT,Symbol.PIQUE);
        inventory[34]= new Card(34,CardValue.NEUF,Symbol.PIQUE);
        inventory[35]= new Card(35,CardValue.DIX,Symbol.PIQUE);
        inventory[36]= new Card(36,CardValue.VALET,Symbol.PIQUE);
        inventory[37]= new Card(37,CardValue.DAME,Symbol.PIQUE);
        inventory[38]= new Card(38,CardValue.ROI,Symbol.PIQUE);


        inventory[39]= new Card(39,CardValue.AS,Symbol.CARREAU);
        inventory[40]= new Card(40,CardValue.DEUX,Symbol.CARREAU);
        inventory[41]= new Card(41,CardValue.TROIS,Symbol.CARREAU);
        inventory[42]= new Card(42,CardValue.QUATRE,Symbol.CARREAU);
        inventory[43]= new Card(43,CardValue.CINQ,Symbol.CARREAU);
        inventory[44]= new Card(44,CardValue.SIX,Symbol.CARREAU);
        inventory[45]= new Card(45,CardValue.SEPT,Symbol.CARREAU);
        inventory[46]= new Card(46,CardValue.HUIT,Symbol.CARREAU);
        inventory[47]= new Card(47,CardValue.NEUF,Symbol.CARREAU);
        inventory[48]= new Card(48,CardValue.DIX,Symbol.CARREAU);
        inventory[49]= new Card(49,CardValue.VALET,Symbol.CARREAU);
        inventory[50]= new Card(50,CardValue.DAME,Symbol.CARREAU);
        inventory[51]= new Card(51,CardValue.ROI,Symbol.CARREAU);

    }

    public String toString() {
        String toString ="";
        for (int i = 0; i < inventory.length; i++) {
           toString = toString+inventory[i];
           Log.i("String", inventory[i].getString_value()+" + "+inventory[i].getSymbol());
        }
        return toString;
    }
}
