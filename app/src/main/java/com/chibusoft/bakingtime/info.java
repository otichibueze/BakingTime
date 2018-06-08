package com.chibusoft.bakingtime;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chibusoft.bakingtime.Baking.ingredients;
import com.chibusoft.bakingtime.Baking.steps;

import java.util.ArrayList;
import java.util.List;

public class info extends AppCompatActivity implements StepFragment.ClickListener{

    private List<Baking.ingredients> ingredients;
    private List<Baking.steps> steps;
    private int index_step;

    private StepFragment stepFragment;

    private int view_type;

    //public static final String VIEW_TYPE = "view";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        view_type = intent.getIntExtra(details.VIEW,1);
        if(view_type ==  details.VIEW_INGREDIENTS && intent.hasExtra(MainActivity.EXTRA_INGREDIENTS)) {
            ingredients = new ArrayList<Baking.ingredients>();
            ingredients = intent.getParcelableArrayListExtra(MainActivity.EXTRA_INGREDIENTS);
        }
        else if(view_type ==  details.VIEW_STEPS && intent.hasExtra(MainActivity.EXTRA_STEPS)) {
            steps = new ArrayList<Baking.steps>();
            steps = intent.getParcelableArrayListExtra(MainActivity.EXTRA_STEPS);
            index_step = intent.getIntExtra(StepsFragment.INDEX,0);
        }
        else
        {
             //throw error
        }

        if(savedInstanceState == null) {
            // Create a new head BodyPartFragment
            if(view_type == details.VIEW_INGREDIENTS) {
                IngredientsFragment ingredientsFragment = new IngredientsFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                ingredientsFragment.setData(ingredients);

                fragmentManager.beginTransaction()
                        .add(R.id.info_container, ingredientsFragment)
                        .commit();
            }
            else if(view_type == details.VIEW_STEPS)
            {
                 stepFragment = new StepFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                stepFragment.setData(steps,index_step);

                fragmentManager.beginTransaction()
                        .add(R.id.info_container, stepFragment)
                        .commit();
            }
            else
            {
                //throw error
            }
        }


        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }


    //This method is very usefull to implement special when we using onclick
    //your fragment will get detached from instance so this methoi is usefull to reattach it
    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof StepFragment)
            stepFragment= (StepFragment) fragment;
    }


    @Override
    public void next(View view) {
        if(stepFragment != null) stepFragment.loadNext();
    }

    @Override
    public void previous(View view) {
        if(stepFragment != null) stepFragment.loadPrevious();
    }
}
