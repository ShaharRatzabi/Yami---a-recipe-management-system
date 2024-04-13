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
import android.widget.EditText;

import com.example.delicious.DataService;
import com.example.delicious.R;
import com.example.delicious.Recipe;
import com.example.delicious.adapters.CustomAdapterSearch;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment {

    private ArrayList<Recipe> dataset;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterSearch adapter;
    EditText editTextSearch;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Button backBtn = view.findViewById(R.id.logoutBtnStep1);
        Button searchBtn = view.findViewById(R.id.searchRecipeBtn);
        Button createBtn = view.findViewById(R.id.createBtnSearch);
        Button accountBtn = view.findViewById(R.id.accountBtnSearch);
        Button homeBtn = view.findViewById(R.id.homeBtnSearch);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_search_to_homePage3);
            }
        });
        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_search_to_accountPage2);
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_search_to_createRecipe1);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_search_to_login);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSearch = view.findViewById(R.id.editTextSearch);
                String q = editTextSearch.getText().toString();
                if(!q.isEmpty()){
                    recyclerView = view.findViewById(R.id.recyclerViewSearch);
                    dataset = new ArrayList<>();
                    dataset.clear();
                    try {
                        ;
                        for (Recipe recipe : DataService.getArrRecipes(q))
                            dataset.add(recipe);

                        // Initialize your adapter with the dataset
                        adapter = new CustomAdapterSearch(dataset, requireContext());

                        // Set up your RecyclerView with the adapter
                        linearLayoutManager = new LinearLayoutManager(requireContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });



        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}