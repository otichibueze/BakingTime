package com.chibusoft.bakingtime;

/**
 * Created by EBELE PC on 6/5/2018.
 */
import timber.log.Timber;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;


import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    @BindView(R.id.step_text) TextView step_text;

    private List<Baking.steps> mStepsList;

   // private int selected_position = RecyclerView.NO_POSITION;

    private final ListItemClickListener mOnCLickListener;

    public interface ListItemClickListener{
        //interface of item we want to capture click for
        //here we are interested in only one item interger clicked
        void onListItemClick(int clickedItemIndex);
    }

    public StepsAdapter(List<Baking.steps> StepsItems, StepsAdapter.ListItemClickListener listener) {
        mStepsList = StepsItems;
        mOnCLickListener = listener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.steps_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        StepViewHolder viewHolder = new StepViewHolder(view);

        ButterKnife.bind(this, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        // Log.d(TAG, "#" + position);
        Timber.d("#" + position);
        holder.bind(position);

        // Here I am just highlighting the background
       // holder.itemView.setBackgroundColor(selected_position == position ? R.color.colorPrimaryLight : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        if (mStepsList == null) return 0;

        return mStepsList.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public StepViewHolder(View itemView) {
            super(itemView);

            //This will also cache the view items
            itemView.setOnClickListener(this);
        }

        void bind(int index) {
           step_text.setText(mStepsList.get(index).shortDescription);
        }

        @Override
        public void onClick(View view)
        {
            int clickedPosition = getAdapterPosition();
            mOnCLickListener.onListItemClick(clickedPosition);


        }

    }

    public void setData(List<Baking.steps> StepsList) {
        mStepsList = StepsList;
        notifyDataSetChanged();
    }


}
