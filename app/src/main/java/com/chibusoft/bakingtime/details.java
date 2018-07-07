package com.chibusoft.bakingtime;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.telecom.Call.Details;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class details extends AppCompatActivity implements StepFragment.ClickListener, StepsAdapter.ListItemClickListener{
//    @BindView(R.id.steps_container)
//    FrameLayout mystep_container;
//    @BindView(R.id.steps_details)
//   FrameLayout mystep_detail;



    private List<Baking.ingredients> ingredients;

    private List<Baking.steps> steps;

    private StepsAdapter stepsAdapter;

    public static final String VIEW = "view";
    public static final String IS_PHONE = "phone";
    public static final String IS_PORT = "port";
    public static final String INDEX = "index";

    public static final int VIEW_INGREDIENTS = 1;
    public static final int VIEW_STEP = 2;
    public static final int VIEW_STEPS = 3;

    public static final String VIEW_MODE = "view_mode";
    public int view_mode;
    public static final int VIEW_SINGLE = 0;
    public static final int VIEW_DOUBLE = 1;

   // private TextView textImage;
    private String title;
    private int img_Resource;

    private StepFragment stepFragment;
    private StepsFragment stepsFragment;
    private IngredientsFragment ingredientsFragment;

    private int view_type;

    private boolean isPhone, isPort;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        ingredients = new ArrayList<Baking.ingredients>();
        steps = new ArrayList<Baking.steps>();
        isPhone = getResources().getBoolean(R.bool.is_phone);
        isPort = getResources().getBoolean(R.bool.is_port);
        index = 0;

        if(savedInstanceState != null)
        {
            ingredients = savedInstanceState.getParcelableArrayList(MainActivity.EXTRA_INGREDIENTS);
            steps = savedInstanceState.getParcelableArrayList(MainActivity.EXTRA_STEPS);
            title = savedInstanceState.getString(MainActivity.EXTRA_TITLE);
            img_Resource = savedInstanceState.getInt(MainActivity.EXTRA_IMG, 0);
            view_type = savedInstanceState.getInt(VIEW);
            isPhone = savedInstanceState.getBoolean(IS_PHONE);
            isPort = savedInstanceState.getBoolean(IS_PORT);
            index = savedInstanceState.getInt(INDEX);
            view_mode = savedInstanceState.getInt(VIEW_MODE);

            stepsAdapter = new StepsAdapter(steps,this);
        }
        else
        {
            Intent intent = getIntent();
            if(intent.hasExtra(MainActivity.EXTRA_STEPS)) {
                steps = intent.getParcelableArrayListExtra(MainActivity.EXTRA_STEPS);
                ingredients = intent.getParcelableArrayListExtra(MainActivity.EXTRA_INGREDIENTS);
                title = intent.getStringExtra(MainActivity.EXTRA_TITLE);
                img_Resource = intent.getIntExtra(MainActivity.EXTRA_IMG, 0);
                view_type = VIEW_STEPS;
            }

        }

        isPort = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? true : false;


//        if(!isPhone && isPort)
//        {
//            mystep_detail.setVisibility(View.GONE);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mystep_container.getLayoutParams();
//            params.width=params.MATCH_PARENT;
//            params.height=params.MATCH_PARENT;
//            mystep_container.setLayoutParams(params);
//            view_mode = VIEW_SINGLE;
//        }


            if(!isPhone)
            {
                // Create a new head BodyPartFragment
                if(savedInstanceState == null) {
                    stepsFragment = new StepsFragment();

                    stepFragment = new StepFragment();
                    stepsAdapter = new StepsAdapter(steps, this);

                    stepFragment = new StepFragment();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    stepsFragment.setData(steps, stepsAdapter, title, img_Resource);
                    stepFragment.setData(steps, index);

                    fragmentManager.beginTransaction()
                            .add(R.id.steps_container, stepsFragment)
                            .add(R.id.steps_details, stepFragment)
                            .commit();
                }
                view_mode = VIEW_DOUBLE;
                view_type = VIEW_STEP;
                stepsFragment.setData(steps, stepsAdapter,title,img_Resource);

            }
            else
            {
                if(view_type == VIEW_STEPS) {
                    // Create a new head BodyPartFragment
                    if(savedInstanceState == null) {
                        stepsFragment = new StepsFragment();
                        stepsAdapter = new StepsAdapter(steps,this);

                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .add(R.id.steps_container, stepsFragment)
                                .commit();
                    }

                    stepsFragment.setData(steps, stepsAdapter,title,img_Resource);
                }
                view_mode = VIEW_SINGLE;

            }




      //  Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
       // setSupportActionBar(myToolbar);

         ActionBar actionBar = this.getSupportActionBar();
         if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
       if(view_mode == VIEW_SINGLE) {
           if (view_type == VIEW_STEPS) {
               index = clickedItemIndex;
               if (stepFragment == null) stepFragment = new StepFragment();
               FragmentManager fragmentManager = getSupportFragmentManager();
               stepFragment.setData(steps, index);

               fragmentManager.beginTransaction()
                       .replace(R.id.steps_container, stepFragment)
                       .commit();

               view_type = VIEW_STEP;
           }
       }
        else //VIEW_DOUBLE
        {
            index = clickedItemIndex;
            if (view_type == VIEW_INGREDIENTS) {
                if (stepFragment == null) stepFragment = new StepFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                stepFragment.setData(steps, index);

                fragmentManager.beginTransaction()
                        .replace(R.id.steps_details, stepFragment)
                        .commit();
                view_type = VIEW_STEP;
            }
            else
            {
                stepFragment.setloadData(steps, index);
            }

        }
    }

    public void onClick_Ingredient(View v) {

        if(view_mode == VIEW_SINGLE) {
            if (view_type == VIEW_STEPS) {
                if (ingredientsFragment == null) ingredientsFragment = new IngredientsFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                ingredientsFragment.setData(ingredients);

                fragmentManager.beginTransaction()
                        .replace(R.id.steps_container, ingredientsFragment)
                        .commit();

                view_type = VIEW_INGREDIENTS;
            }
        }
       else //VIEW_DOUBLE
        {
            if (view_type == VIEW_STEP) {
                if (ingredientsFragment == null) ingredientsFragment = new IngredientsFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                ingredientsFragment.setData(ingredients);
                ingredientsFragment.setFocus();

                fragmentManager.beginTransaction()
                        .replace(R.id.steps_details, ingredientsFragment)
                        .commit();

                view_type = VIEW_INGREDIENTS;
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            if(view_mode == VIEW_SINGLE)
            {
                if(view_type == VIEW_STEP || view_type == VIEW_INGREDIENTS)
                {
                    if(stepsFragment == null) stepsFragment = new StepsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    stepsFragment.setData(steps, stepsAdapter,title,img_Resource);

                    fragmentManager.beginTransaction()
                            .replace(R.id.steps_container, stepsFragment)
                            .commit();

                    view_type = VIEW_STEPS;
                    return true;
                }
                else
                {
                    NavUtils.navigateUpFromSameTask(this);
                    return  true;
                }
            }
            else
            {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            if(view_mode == VIEW_SINGLE)
            {
                if(view_type == VIEW_STEP || view_type == VIEW_INGREDIENTS)
                {
                    if(stepsFragment == null) stepsFragment = new StepsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    stepsFragment.setData(steps, stepsAdapter,title,img_Resource);

                    fragmentManager.beginTransaction()
                            .replace(R.id.steps_container, stepsFragment)
                            .commit();

                        view_type = VIEW_STEPS;
                    return true;
                }
                else
                {
                    NavUtils.navigateUpFromSameTask(this);
                }
            }
            else
            {
                NavUtils.navigateUpFromSameTask(this);
            }


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
       outState.putParcelableArrayList(MainActivity.EXTRA_INGREDIENTS, (ArrayList) ingredients);
        outState.putParcelableArrayList(MainActivity.EXTRA_STEPS, (ArrayList) steps);
        outState.putString(MainActivity.EXTRA_TITLE,title);
        outState.putInt(MainActivity.EXTRA_IMG,img_Resource);
        outState.putInt(VIEW,view_type);
        outState.putBoolean(IS_PHONE,isPhone);
        outState.putBoolean(IS_PORT,isPort);
        outState.putInt(INDEX,index);
        outState.putInt(VIEW_MODE,view_mode);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof StepFragment) stepFragment = (StepFragment) fragment;
        if(fragment instanceof  StepsFragment) stepsFragment = (StepsFragment) fragment;
        if(fragment instanceof IngredientsFragment) ingredientsFragment = (IngredientsFragment) fragment;

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
