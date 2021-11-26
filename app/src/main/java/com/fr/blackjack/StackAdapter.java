package com.fr.blackjack;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StackAdapter extends ArrayAdapter<Card> {

    private List<Card> items;
    private Context context;
    private boolean isPlayer;

    public StackAdapter(Context context, int textViewResourceId,
                        List<Card> objects,boolean isPlayer) {
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.context = context;
        this.isPlayer= isPlayer;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.card, null);
        }
        Card card = items.get(position);
        if(card== null)  {
            Log.i("MyLog", "card at " + position + " is null!!!");
            return itemView;
        }

        // ImageView defined in the stack_item.xml
        ImageView image = (ImageView) itemView.findViewById(R.id.symbol);
        TextView cardValue1 = (TextView) itemView.findViewById(R.id.cardvalue1);
        TextView cardValue2 = (TextView) itemView.findViewById(R.id.cardvalue2);
        View backcard = (View) itemView.findViewById(R.id.back_card);

        cardValue1.setText(card.getString_value());
        cardValue2.setText(card.getString_value());

        if((position<=9)&& !isPlayer){
            //Log.i("String","position = "+String.valueOf(position));
            backcard.setVisibility(View.VISIBLE);
        }

        switch (card.getSymbol()){
            case COEUR:
                image.setImageResource(R.drawable.coeur);
                break;
            case PIQUE:
                image.setImageResource(R.drawable.pique);
                break;
            case CARREAU:
                image.setImageResource(R.drawable.carreau);
                break;
            case TREFLE:
                image.setImageResource(R.drawable.trefle);
                break;
        }


        return itemView;
    }


    // Find Image ID corresponding to the name of the image (in the drawable folder).
    public int getDrawableResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
        Log.i("MyLog", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

}