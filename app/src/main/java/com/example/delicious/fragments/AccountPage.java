package com.example.delicious.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.delicious.R;
import com.example.delicious.Recipe;
import com.example.delicious.adapters.CustomAdapterAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountPage extends Fragment {

    private ArrayList<Recipe> datasetFromApi;
    private RecyclerView recyclerViewFromApi;
    private LinearLayoutManager linearLayoutManagerFromApi;
    private CustomAdapterAccount adapterFromApi;
    private ArrayList<Recipe> datasetYourRecipes;
    private RecyclerView recyclerViewYourRecipes;
    private LinearLayoutManager linearLayoutManagerYourRecipes;
    private CustomAdapterAccount adapterYourRecipes;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountPage newInstance(String param1, String param2) {
        AccountPage fragment = new AccountPage();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_page, container, false);
        Button searchBtn = view.findViewById(R.id.searchBtnAcount);
        Button createBtn = view.findViewById(R.id.createBtnAcount);
        Button logoutBtn = view.findViewById(R.id.logoutBtnAccount);
        Button homeBtn = view.findViewById(R.id.homeBtnAccount);


        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_accountPage2_to_homePage3);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_accountPage2_to_login);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_accountPage2_to_search);
            }
        });

                //Navigation.findNavController(view).navigate(R.id.action_homePage_to_search);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_accountPage2_to_createRecipe1);
            }
        });
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewFromApi = view.findViewById(R.id.recyclerViewFromApi);
        datasetFromApi = new ArrayList<Recipe>();
        datasetFromApi.clear();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        DatabaseReference recipesRef = userRef.child("recipes");
        DatabaseReference fromApi = recipesRef.child("from api");
        fromApi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    // Extract data from each recipe snapshot
                    String name = recipeSnapshot.child("name").getValue(String.class);
                    String description = recipeSnapshot.child("description").getValue(String.class);
                    String category = recipeSnapshot.child("category").getValue(String.class);
                    String image = recipeSnapshot.child("imageUrl").getValue(String.class);
                    String instructions = recipeSnapshot.child("steps").getValue(String.class);
                    String time = recipeSnapshot.child("preparationTime").getValue(String.class);
                    ArrayList<String> ingredients = new ArrayList<>();
                    DataSnapshot ingredientsSnapshot = recipeSnapshot.child("ingredients");
                    for (DataSnapshot ingredientSnapshot : ingredientsSnapshot.getChildren()) {
                        String ingredientName = ingredientSnapshot.getValue(String.class);
                        ingredients.add(ingredientName);
                    }

                    // Create DataModel object and add to dataset
                    Recipe recipe = new Recipe( name, time, category,description,ingredients,instructions,image);
                    datasetFromApi.add(recipe);
                }
                // Notify adapter that dataset has changed
                adapterFromApi.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        // Initialize your adapter with the dataset
        adapterFromApi = new CustomAdapterAccount(datasetFromApi,requireContext());

        // Set up your RecyclerView with the adapter
        linearLayoutManagerFromApi = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFromApi.setLayoutManager(linearLayoutManagerFromApi);
        recyclerViewFromApi.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFromApi.setAdapter(adapterFromApi);

        recyclerViewYourRecipes = view.findViewById(R.id.recyclerViewYourOwn);
        datasetYourRecipes = new ArrayList<Recipe>();
        datasetYourRecipes.clear();
        DatabaseReference yourRecipes = recipesRef.child("your own");
        yourRecipes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    // Extract data from each recipe snapshot
                    String name = recipeSnapshot.child("name").getValue(String.class);
                    String description = recipeSnapshot.child("description").getValue(String.class);
                    String category = recipeSnapshot.child("category").getValue(String.class);
                    String image = recipeSnapshot.child("imageUrl").getValue(String.class);
                    String instructions = recipeSnapshot.child("steps").getValue(String.class);
                    String time = recipeSnapshot.child("preparationTime").getValue(String.class);
                    ArrayList<String> ingredients = new ArrayList<>();
                    DataSnapshot ingredientsSnapshot = recipeSnapshot.child("ingredients");
                    for (DataSnapshot ingredientSnapshot : ingredientsSnapshot.getChildren()) {
                        String ingredientName = ingredientSnapshot.getValue(String.class);
                        ingredients.add(ingredientName);
                    }

                    // Create DataModel object and add to dataset
                    Recipe recipe = new Recipe( name, time, category,description,ingredients,instructions,image);
                    datasetYourRecipes.add(recipe);
                }
                // Notify adapter that dataset has changed
                adapterYourRecipes.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        // Initialize your adapter with the dataset
        adapterYourRecipes = new CustomAdapterAccount(datasetYourRecipes,requireContext());

        // Set up your RecyclerView with the adapter
        linearLayoutManagerYourRecipes = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerViewYourRecipes.setLayoutManager(linearLayoutManagerYourRecipes);
        recyclerViewYourRecipes.setItemAnimator(new DefaultItemAnimator());
        recyclerViewYourRecipes.setAdapter(adapterYourRecipes);
    }


}