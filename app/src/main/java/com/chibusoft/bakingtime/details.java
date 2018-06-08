package com.chibusoft.bakingtime;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class details extends AppCompatActivity {

    private List<Baking.ingredients> ingredients;

    private List<Baking.steps> steps;

    public static final String VIEW = "view";

    //public static final String INGREDIENTS = "ingredients";
   // public static final String STEPS = "steps";

    public static final int VIEW_INGREDIENTS = 1;

    public static final int VIEW_STEPS = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ingredients = new ArrayList<Baking.ingredients>();
        steps = new ArrayList<Baking.steps>();

        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.EXTRA_STEPS)) {
            steps = intent.getParcelableArrayListExtra(MainActivity.EXTRA_STEPS);
            ingredients = intent.getParcelableArrayListExtra(MainActivity.EXTRA_INGREDIENTS);
        }



        if(savedInstanceState == null) {
            // Create a new head BodyPartFragment
            StepsFragment stepsFragment = new StepsFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            stepsFragment.setData(steps);

            fragmentManager.beginTransaction()
                    .add(R.id.steps_container, stepsFragment)
                    .commit();
        }

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }


    public void onClick_Ingredient(View v) {
        Intent intent = new Intent(this , info.class);
        intent.putParcelableArrayListExtra(MainActivity.EXTRA_INGREDIENTS,  (ArrayList) ingredients);
        intent.putExtra(VIEW, VIEW_INGREDIENTS);
        startActivity(intent);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
       outState.putParcelableArrayList(MainActivity.EXTRA_INGREDIENTS, (ArrayList) ingredients);
        outState.putParcelableArrayList(MainActivity.EXTRA_STEPS, (ArrayList) steps);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null || savedInstanceState.containsKey(MainActivity.EXTRA_INGREDIENTS)) {
          ingredients = savedInstanceState.getParcelableArrayList(MainActivity.EXTRA_INGREDIENTS);
          steps = savedInstanceState.getParcelableArrayList(MainActivity.EXTRA_STEPS);
        }

    }
}
