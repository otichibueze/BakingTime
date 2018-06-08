package com.chibusoft.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EBELE PC on 6/5/2018.
 */

public class StepsFragment extends Fragment implements StepsAdapter.ListItemClickListener {

    private RecyclerView mBakingSteps_RV;

    private StepsAdapter stepsAdapter;
    private List<Baking.steps> mStepList;
    private TextView ingredient;

    public static final String INDEX = "index";

    public StepsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //here we load whatever we save in the savedInstanceState
        if(savedInstanceState != null) {
            mStepList = savedInstanceState.getParcelableArrayList(MainActivity.EXTRA_STEPS);
            stepsAdapter = new StepsAdapter(mStepList,this);
        }


        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

      //  ingredient = (TextView) rootView.findViewById(R.id.ingredient_text);

        mBakingSteps_RV = (RecyclerView) rootView.findViewById(R.id.rv_steps);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBakingSteps_RV.setLayoutManager(layoutManager);
        mBakingSteps_RV.setHasFixedSize(true);
        mBakingSteps_RV.setAdapter(stepsAdapter);



        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex)
    {
        Intent intent = new Intent(getContext() , info.class);
        intent.putParcelableArrayListExtra(MainActivity.EXTRA_STEPS,  (ArrayList) mStepList);
        intent.putExtra(INDEX,clickedItemIndex);
        intent.putExtra(details.VIEW, details.VIEW_STEPS);
        startActivity(intent);
    }


    public void setData(List<Baking.steps> steps) {
        mStepList = steps;
        stepsAdapter = new StepsAdapter(mStepList,this);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MainActivity.EXTRA_STEPS, (ArrayList) mStepList);
        super.onSaveInstanceState(outState);
    }


}
