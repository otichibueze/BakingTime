package com.chibusoft.bakingtime;

/**
 * Created by EBELE PC on 6/7/2018.
 */

import timber.log.Timber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;


import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>{

    @BindView(R.id.ingredient_name) TextView ingredient_name;
    @BindView(R.id.ingredient_quantity) TextView ingredient_quantity;
    @BindView(R.id.ingredient_measure) TextView ingredient_measure;

    private List<Baking.ingredients> mIngredientsList;

    public IngredientsAdapter(List<Baking.ingredients> ingredientItems) {
        mIngredientsList = ingredientItems;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        IngredientViewHolder viewHolder = new IngredientViewHolder(view);

        ButterKnife.bind(this, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        // Log.d(TAG, "#" + position);
        Timber.d("#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mIngredientsList == null) return 0;

        return mIngredientsList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {


        public IngredientViewHolder(View itemView) {
            super(itemView);


        }

        void bind(int index) {
            ingredient_name.setText(mIngredientsList.get(index).ingredient);
            ingredient_measure.setText(mIngredientsList.get(index).measure);
            ingredient_quantity.setText(mIngredientsList.get(index).quantity + "");

        }


    }

    public void setData(List<Baking.ingredients> ingredients) {
        mIngredientsList = ingredients;
        notifyDataSetChanged();
    }

}
