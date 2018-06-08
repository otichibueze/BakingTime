package com.chibusoft.bakingtime;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFragment extends Fragment {
    @BindView(R.id.step_description)
    TextView description;
    @BindView(R.id.next_btn)
    ImageButton next_button;
    @BindView(R.id.previous_btn)
    ImageButton previous_button;

    private List<Baking.steps> mStepList;
    private int index;

    public static final String EXTRA_INDEX = "index";

    private SimpleExoPlayer mExoPlayer;


    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    ClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface ClickListener {
        void next(View view);
        void previous(View view);
    }


    public StepFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //here we load whatever we save in the savedInstanceState
        if(savedInstanceState != null) {
            mStepList = savedInstanceState.getParcelableArrayList(MainActivity.EXTRA_STEPS);
            index = savedInstanceState.getInt(EXTRA_INDEX,0);
        }


        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        if(mStepList != null) {
            description.setText(mStepList.get(index).description);
        }


        if(index == 0) previous_button.setVisibility(View.INVISIBLE);
        if(index > 0) previous_button.setVisibility(View.VISIBLE);
        if(index == mStepList.size() -1) next_button.setVisibility(View.INVISIBLE);
        if(index < mStepList.size() -1 ) next_button.setVisibility(View.VISIBLE);



        return rootView;
    }

    public void setData(List<Baking.steps> steps,int i){
        mStepList = steps;
        index = i;
    }

    public void loadNext()
    {

        if(index < mStepList.size() -1) index++;
        description.setText(mStepList.get(index).description);

        if(index > 0) previous_button.setVisibility(View.VISIBLE);
        if(index == mStepList.size() -1) next_button.setVisibility(View.INVISIBLE);
    }

    public void loadPrevious()
    {

        if(index > 0) index--;
        description.setText(mStepList.get(index).description);

        if(index < mStepList.size() - 1) next_button.setVisibility(View.VISIBLE);
        if(index == 0) previous_button.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface for onclick
        // If not, it throws an exception
        try {
            mCallback = (ClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MainActivity.EXTRA_STEPS, (ArrayList) mStepList);
        outState.putInt(EXTRA_INDEX,index);
        super.onSaveInstanceState(outState);
    }

}
