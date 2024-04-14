package com.example.delicious.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.delicious.activity.MainActivity;
import com.example.delicious.R;
import com.example.delicious.models.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateRecipeStep4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRecipeStep4 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE_REQUEST = 1 ;
    ImageView imageView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateRecipeStep4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateRecipe.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateRecipeStep4 newInstance(String param1, String param2) {
        CreateRecipeStep4 fragment = new CreateRecipeStep4();
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
        View view = inflater.inflate(R.layout.fragment_create_recipe_step4, container, false);
        imageView = view.findViewById(R.id.imageView);

        Button BackToStep3 = view.findViewById(R.id.logoutBtnStep1);
        Button confirmBtn = view.findViewById(R.id.confirmBtn);
        Button addImageBtn = view.findViewById(R.id.addImageBtn);

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery(v);
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imageUri = (Uri) imageView.getTag();

                // Get reference to Firebase Storage
                if (imageUri != null) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String recipeId = UUID.randomUUID().toString();
                    Recipe recipe = MainActivity.currentRecipe;
                    recipe.setImageUrl(imageUri.toString());
                    recipe.setId(recipeId);
                    saveRecipeToDatabase(user, recipe);
                    Navigation.findNavController(view).navigate(R.id.action_createRecipe_to_accountPage2);

                } else {
                    // Handle case where no image is selected
                    Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        BackToStep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_createRecipe_to_createRecipeStep3);
            }
        });
        return  view;
    }
    public void saveRecipeToDatabase(FirebaseUser user,Recipe recipe) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        // Get a reference to the "recipes" node under the user's node
        DatabaseReference recipesRef = userRef.child("recipes");
        DatabaseReference yourOwn = recipesRef.child("your own");
        // Create a new recipe id
        String recipeId = recipesRef.push().getKey();
        recipe.setId(recipeId);
        // Save the recipe under the user's recipes node
        yourOwn.child(recipeId).setValue(recipe)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Recipe added successfully
                        Log.d("success", "ok");
                        Toast.makeText(getContext(), "Recipe added successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to add recipe
                        Log.d("fail", ":(");
                        Toast.makeText(getContext(), "Failed to add recipe: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void selectImageFromGallery(View view) {
        // Start the image selection process using the ActivityResultContracts
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
            imageView.setTag(selectedImageUri);
        }
    }



}