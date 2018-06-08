package com.chibusoft.bakingtime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chibusoft.bakingtime.Baking.steps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EBELE PC on 6/7/2018.
 */

public class IngredientsFragment extends Fragment {

    public static final String SAVED_INGREDIENTS = "bake_ingredient";

    private RecyclerView mBakingIngredient_RV;

    private IngredientsAdapter ingredientsAdapter;
    private List<Baking.ingredients> mIngredientList;

    public IngredientsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //here we load whatever we save in the savedInstanceState
        if(savedInstanceState != null) {
            mIngredientList = savedInstanceState.getParcelableArrayList(SAVED_INGREDIENTS);
            ingredientsAdapter = new IngredientsAdapter(mIngredientList);
        }


        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        //  ingredient = (TextView) rootView.findViewById(R.id.ingredient_text);

        mBakingIngredient_RV = (RecyclerView) rootView.findViewById(R.id.rv_ingredients);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBakingIngredient_RV.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBakingIngredient_RV.getContext(),
                layoutManager.getOrientation());
        mBakingIngredient_RV.addItemDecoration(dividerItemDecoration);

        mBakingIngredient_RV.setHasFixedSize(true);
        mBakingIngredient_RV.setAdapter(ingredientsAdapter);



        return rootView;
    }



    public void setData(List<Baking.ingredients> ingredients) {
        mIngredientList = ingredients;
        ingredientsAdapter = new IngredientsAdapter(mIngredientList);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVED_INGREDIENTS, (ArrayList) mIngredientList);
        super.onSaveInstanceState(outState);
    }

}
