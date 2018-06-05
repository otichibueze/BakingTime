package com.chibusoft.bakingtime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import timber.log.Timber;

/**
 * Created by EBELE PC on 6/5/2018.
 */

public class StepsIngredientsFragment extends Fragment {

    private RecyclerView mBakingSteps_RV;

    public StepsIngredientsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //here we load whatever we save in the savedInstanceState
        if(savedInstanceState != null) {
           // mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
           // mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_steps_ingredients, container, false);

       //mBakingSteps_RV =


        return rootView;
    }
}
