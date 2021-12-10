package com.fr.blackjack;

public class Card {
    private int id;
    private ColorBj color;
    private CardValue card_value;
    private String string_value;
    private int int_value = 0;
    private int [] as_value = new int[2];
    private Symbol symbol;

    public Card (int id,CardValue card_value,Symbol symbol){
        this.id= id;
        this.card_value = card_value;
        this.symbol = symbol;
        setString_value(card_value);
        setInt_value(card_value);

        if (symbol==Symbol.TREFLE || symbol==Symbol.PIQUE){
            this.color = ColorBj.NOIR;
        }else{
            this.color = ColorBj.ROUGE;
        }
    }

    public CardValue getCard_value() {
        return card_value;
    }

    public ColorBj getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setString_value(CardValue card_value){
        switch (card_value){
            case AS:
                string_value = "A";
                break;
            case DEUX:
                string_value = "2";
                break;
            case TROIS:
                string_value = "3";
                break;
            case QUATRE:
                string_value = "4";
                break;
            case CINQ:
                string_value = "5";
                break;
            case SIX:
                string_value = "6";
                break;
            case SEPT:
                string_value = "7";
                break;
            case HUIT:
                string_value = "8";
                break;
            case NEUF:
                string_value = "9";
                break;
            case DIX:
                string_value = "10";
                break;
            case VALET:
                string_value = "V";
                break;
            case DAME:
                string_value = "D";
                break;
            case ROI:
                string_value = "R";
                break;
        }
    }

    public void setInt_value(CardValue card_value){
        switch (card_value){
            case AS:
                int_value = 0;
                break;
            case DEUX:
                int_value = 2;
                break;
            case TROIS:
                int_value = 3;
                break;
            case QUATRE:
                int_value = 4;
                break;
            case CINQ:
                int_value = 5;
                break;
            case SIX:
                int_value = 6;
                break;
            case SEPT:
                int_value = 7;
                break;
            case HUIT:
                int_value = 8;
                break;
            case NEUF:
                int_value = 9;
                break;
            case DIX:
                int_value = 10;
                break;
            case VALET:
                int_value = 10;
                break;
            case DAME:
                int_value = 10;
                break;
            case ROI:
                int_value = 10;
                break;
        }
    }

    public String getString_value() {
        return string_value;
    }

    public int getInt_value() {
        return int_value;
    }

    public int[] getAs_value() {
        return as_value;
    }


}

