package com.example.delicious.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.delicious.R;

import java.util.ArrayList;

public class IngredientAdapter extends ArrayAdapter<String> {
    private ArrayList<String> ingredientList;
    private Context mContext;

    public IngredientAdapter(Context context, ArrayList<String> list) {
        super(context, 0, list);
        this.mContext = context;
        this.ingredientList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.card_layout_ingredient, parent, false);
        }

        String ingredient = ingredientList.get(position);

        TextView textViewIngredient = listItem.findViewById(R.id.textViewIngredient);
        textViewIngredient.setText(ingredient);

        Button btnRemoveIngredient = listItem.findViewById(R.id.btnRemoveIngredient);
        btnRemoveIngredient.setOnClickListener(v -> {
            // Remove the ingredient from the list
            ingredientList.remove(position);
            notifyDataSetChanged();
        });

        return listItem;
    }
}
