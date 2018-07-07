package com.chibusoft.bakingtime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EBELE PC on 6/5/2018.
 */

public class StepsFragment extends Fragment  {

    @BindView(R.id.rv_steps)
     RecyclerView mBakingSteps_RV;
    @BindView(R.id.img_text)
    TextView textImage;
    @BindView(R.id.toolbarImage)
    ImageView toolbarImage;


    private StepsAdapter mStepsAdapter;
    private List<Baking.steps> mStepList;
   // private TextView ingredient;

    //public static final String INDEX = "index";

    private String title;
    private int img_Resource;


//    // Define a new interface OnImageClickListener that triggers a callback in the host activity
//    ClickListener mCallback;
//
//    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
//    public interface ClickListener {
//        void itemClicked (int clickedItemIndex);
//    }

    public StepsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //here we load whatever we save in the savedInstanceState
        if(savedInstanceState != null) {
            mStepList = savedInstanceState.getParcelableArrayList(MainActivity.EXTRA_STEPS);
           // stepsAdapter = new StepsAdapter(mStepList,this);
        }


        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        ButterKnife.bind(this, rootView);
        textImage.setText(title);
        toolbarImage.setImageResource(img_Resource);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBakingSteps_RV.getContext(),
                layoutManager.getOrientation());
        mBakingSteps_RV.setFocusable(false);
        mBakingSteps_RV.addItemDecoration(dividerItemDecoration);
        mBakingSteps_RV.setLayoutManager(layoutManager);
        mBakingSteps_RV.setHasFixedSize(true);
        mBakingSteps_RV.setAdapter(mStepsAdapter);



        return rootView;
    }




//    @Override
//    public void onDetach() {
//        super.onDetach();
//        // remove the reference to your Activity
//        mCallback = null;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        // This makes sure that the host activity has implemented the callback interface for onclick
//        // If not, it throws an exception
//        try {
//            mCallback = (ClickListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement OnImageClickListener");
//        }
//    }


    public void setData(List<Baking.steps> steps, StepsAdapter stepsAdapter,String text_title, int image) {
        mStepList = steps;
        mStepsAdapter = stepsAdapter;
        title = text_title;
        img_Resource = image;

        //stepsAdapter = new StepsAdapter(mStepList,this);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MainActivity.EXTRA_STEPS, (ArrayList) mStepList);
        super.onSaveInstanceState(outState);
    }




}
