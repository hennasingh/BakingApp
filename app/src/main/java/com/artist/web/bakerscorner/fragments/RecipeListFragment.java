package com.artist.web.bakerscorner.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artist.web.bakerscorner.IdlingResource.SimpleIdlingResource;
import com.artist.web.bakerscorner.MainApplication;
import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.activities.RecipeListActivity;
import com.artist.web.bakerscorner.activities.RecipePagerActivity;
import com.artist.web.bakerscorner.adapters.RecipeAdapter;
import com.artist.web.bakerscorner.models.Ingredients;
import com.artist.web.bakerscorner.models.Recipes;
import com.artist.web.bakerscorner.network.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by User on 07-Apr-18.
 */

public class RecipeListFragment extends Fragment implements RecipeAdapter.RecipeAdapterOnClickListener {

    private static final String TAG = RecipeListFragment.class.getSimpleName();
    @BindView(R.id.rv_recipes)
    RecyclerView mRecipesRecyclerView;
    SimpleIdlingResource mSimpleIdlingResource;
    boolean isUpdateSuccessful;
    private RecipeAdapter mRecipeAdapter;
    private ArrayList<Recipes> mRecipeList;
    private Unbinder unbinder;
    private RecipeListActivity mParentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mParentActivity = (RecipeListActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        unbinder = ButterKnife.bind(this, view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), gridColumns());
        mRecipesRecyclerView.setLayoutManager(layoutManager);

        mSimpleIdlingResource = (SimpleIdlingResource) mParentActivity.getIdlingResource();
        if (mSimpleIdlingResource != null) {
            mSimpleIdlingResource.setIdleState(false);
        }

        updateUI();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().getSupportLoaderManager().initLoader(RECIPE_LOADER,null,this);
    }

    private int gridColumns() {
        int size = 0;
        switch (getActivity().getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                size = 1;
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                size = 2;
                break;
        }
        return size;
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

                    loadSuccessful(true);
                    String recipeResponse = response.body().string();
                    Gson gson = new GsonBuilder().create();

                    /**
                     * TypeToken - Gson uses Java reflection API to get the type of the object to which a Json text is to be mapped. But with generics,
                     * this information is lost during serialization. To counter this problem,
                     * Gson provides a class com.google.gson.reflect.TypeToken to store the type of the generic object
                     */
                    mRecipeList = gson.fromJson(recipeResponse, new TypeToken<List<Recipes>>() {
                    }.getType());

                    ArrayList<Ingredients> ingredientsList = new ArrayList<>();
                    String recipeName;

                    for (Recipes recipe : mRecipeList) {
                        recipeName = recipe.getRecipeName();
                        ingredientsList.addAll(recipe.getIngredientsList());
                        Utils.writeIngredientsToDb(ingredientsList, recipeName, getActivity());

                        //clear the list for next set
                        ingredientsList.clear();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                if (mRecipeAdapter == null) {
                                    mRecipeAdapter = new RecipeAdapter(mRecipeList, RecipeListFragment.this, getActivity());
                                    mRecipesRecyclerView.setAdapter(mRecipeAdapter);
                                    mRecipesRecyclerView.setHasFixedSize(true);
                                } else {
                                    mRecipeAdapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                loadSuccessful(false);
                                Log.e(TAG, "UI Update Failed " + e.getMessage());
                            }
                        }
                    });

                } else {
                    loadSuccessful(false);
                    Log.e(TAG, "Failed to get a successful response , status code = " + statusCode);
                }

            }
        });

    }

    private void loadSuccessful(boolean isSuccessful) {

        if (mSimpleIdlingResource != null) {
            mSimpleIdlingResource.setIdleState(isSuccessful);
        }
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Recipes recipe = mRecipeList.get(clickedPosition);
        Intent intent = RecipePagerActivity.newIntent(getActivity(), recipe);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
