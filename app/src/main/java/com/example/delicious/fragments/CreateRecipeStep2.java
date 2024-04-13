package com.example.delicious.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.delicious.MainActivity;
import com.example.delicious.R;
import com.example.delicious.Recipe;
import com.example.delicious.adapters.IngredientAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateRecipeStep2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRecipeStep2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView listViewIngredients;
    private Button btnAddIngredient;
    private ArrayList<String> ingredientsList;
    private IngredientAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateRecipeStep2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateRecipeStep2.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateRecipeStep2 newInstance(String param1, String param2) {
        CreateRecipeStep2 fragment = new CreateRecipeStep2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe_step2, container, false);
        Recipe currentRecipe = MainActivity.currentRecipe;
        EditText editTextIngredientName = view.findViewById(R.id.editTextIngredientName);
        EditText editTextQuantity = view.findViewById(R.id.editTextQuantity);
        Button btnAddIngredient = view.findViewById(R.id.btnAddIngredient);
        ListView listViewIngredients = view.findViewById(R.id.listViewIngredients);

        ingredientsList = new ArrayList<>();
        adapter = new IngredientAdapter(requireContext(), ingredientsList);
        listViewIngredients.setAdapter(adapter);

        btnAddIngredient.setOnClickListener(v -> {
            String ingredientName = editTextIngredientName.getText().toString().trim();
            String quantity = editTextQuantity.getText().toString().trim();

            if (!ingredientName.isEmpty() && !quantity.isEmpty()) {
                String ingredient = ingredientName + ": " + quantity;
                ingredientsList.add(ingredient);
                adapter.notifyDataSetChanged();

                // Clear input fields after adding ingredient
                editTextIngredientName.getText().clear();
                editTextQuantity.getText().clear();
            }
        });

        Button toStep3 = view.findViewById(R.id.NextToStep3Btn);
        Button backToStep1 = view.findViewById(R.id.backToHomePageFromSearchBtn);
        backToStep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_createRecipeStep2_to_createRecipe1);
            }
        });
        toStep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRecipe.setIngredients(ingredientsList);
                MainActivity.currentRecipe= currentRecipe;
                Navigation.findNavController(view).navigate(R.id.action_createRecipeStep2_to_createRecipeStep3);

            }
        });


        return view;

    }

}