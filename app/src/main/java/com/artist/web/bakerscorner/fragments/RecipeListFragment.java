package com.artist.web.bakerscorner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artist.web.bakerscorner.MainApplication;
import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.activities.RecipePagerActivity;
import com.artist.web.bakerscorner.adapters.RecipeAdapter;
import com.artist.web.bakerscorner.data.Recipes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by User on 07-Apr-18.
 */

public class RecipeListFragment extends Fragment implements RecipeAdapter.RecipeAdapterOnClickListener {

    private static final String TAG = RecipeListFragment.class.getSimpleName();
    private RecyclerView mRecipesRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private ArrayList<Recipes> mRecipeList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list,container,false);

        mRecipesRecyclerView = view.findViewById(R.id.rv_recipes);
        mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        MainApplication.sHttpClient.getRecipes(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "HTTP Call Failed " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                int statusCode = response.code();
                if (response.isSuccessful()) {

                    String recipeResponse = response.body().string();
                    Gson gson = new GsonBuilder().create();
                    Log.e(TAG, "data received: " + recipeResponse);

                    /**
                     * TypeToken - Gson uses Java reflection API to get the type of the object to which a Json text is to be mapped. But with generics,
                     * this information is lost during serialization. To counter this problem,
                     * Gson provides a class com.google.gson.reflect.TypeToken to store the type of the generic object
                     */
                    mRecipeList = gson.fromJson(recipeResponse, new TypeToken<List<Recipes>>() {
                    }.getType());

                    Log.e(TAG, "No of recipes " + mRecipeList.size());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                if (mRecipeAdapter == null) {
                                    mRecipeAdapter = new RecipeAdapter(mRecipeList, RecipeListFragment.this);
                                    mRecipesRecyclerView.setAdapter(mRecipeAdapter);
                                    mRecipesRecyclerView.setHasFixedSize(true);
                                } else {
                                    mRecipeAdapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "UI Update Failed " + e.getMessage());
                            }
                        }
                    });

                } else {
                    Log.e(TAG, "Failed to get a successful response , status code = " + statusCode);
                }

            }
        });

    }

    @Override
    public void onItemClick(int clickedPosition) {
        Recipes recipe = mRecipeList.get(clickedPosition);
        Intent intent = RecipePagerActivity.newIntent(getActivity(), recipe);
        startActivity(intent);
    }
}
