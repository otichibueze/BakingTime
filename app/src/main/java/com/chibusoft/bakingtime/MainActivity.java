package com.chibusoft.bakingtime;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.ProgressBar;
import com.chibusoft.bakingtime.Data.Ingredients_Contract;
import com.chibusoft.bakingtime.Data.RecipeProvider.Ingredients;
import com.chibusoft.bakingtime.Data.RecipeProvider.Recipes;
import com.chibusoft.bakingtime.Data.RecipeProvider.Steps;
import com.chibusoft.bakingtime.Data.Recipe_Contract;
import com.chibusoft.bakingtime.Data.Steps_Contract;
import com.chibusoft.bakingtime.Network.GetDataService;
import com.chibusoft.bakingtime.Network.RetrofitClientInstance;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements BakingAdapter.ListItemClickListener{

    @BindView(R.id.rv_baking)
    RecyclerView mBaking_RV;

    @BindView(R.id.progress_spinner)
    ProgressBar mProgress;

    //private RecyclerView mBaking_RV;
    private BakingAdapter bakingAdapter;
    private List<Baking> BakingList;

    public static final String EXTRA_STEPS = "Steps";
    public static final String EXTRA_INGREDIENTS = "ingredients";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_IMG = "image_resource";

    public static final int GRID_WIDTH = 600;

    private SharedPreferences pref;
    private boolean saved_Content = false;

    // The Idling Resource which will be null in production.
    @Nullable
    private CountingIdlingResource mIdlingResource= new CountingIdlingResource("Loading_Data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



         pref = this.getSharedPreferences(getString(R.string.preference), MODE_PRIVATE);

        saved_Content = pref.getBoolean(getString(R.string.contentSaved), false);


        if(savedInstanceState == null || !savedInstanceState.containsKey("Bake")) {
            loadData();
        }
        else {
            mProgress.setVisibility(View.VISIBLE);
            BakingList = savedInstanceState.getParcelableArrayList("Bake");
            generateDataList(BakingList);
        }

        // Get the IdlingResource instance
        getIdlingResource();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("Bake", (ArrayList)BakingList);
        super.onSaveInstanceState(outState);
    }



    public void loadData()
    {
        mIdlingResource.increment();

        mProgress.setVisibility(View.VISIBLE);
          /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Baking>> call = service.getData();
        call.enqueue(new Callback<List<Baking>>() {
            @Override
            public void onResponse(Call<List<Baking>> call, Response<List<Baking>> response) {
                generateDataList(response.body());

                mIdlingResource.decrement();
            }

            @Override
            public void onFailure(Call<List<Baking>> call, Throwable t) {
                mProgress.setVisibility(View.GONE);

                mIdlingResource.decrement();

                ReloadOption();
            }
        });
    }


    private void generateDataList(List<Baking> baking) {

        mProgress.setVisibility(View.GONE);
        BakingList = baking;
        if(!saved_Content) saveToDataBase(); //save the data to database
        bakingAdapter = new BakingAdapter(baking,this);

        boolean isPhone = getResources().getBoolean(R.bool.is_phone);
        boolean isPort = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? true : false;

        if (isPhone && isPort) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mBaking_RV.setLayoutManager(layoutManager);
       } else  {
            GridAutofitLayoutManager gridLayoutManager = new GridAutofitLayoutManager(this, GRID_WIDTH);
            mBaking_RV.setLayoutManager(gridLayoutManager);
        }

        mBaking_RV.setHasFixedSize(true);
        mBaking_RV.setAdapter(bakingAdapter);

        //Here we load data when it doesnt load
        mBaking_RV.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1))
                {
                    if(BakingList == null || BakingList.size() == 0)
                    {
                        loadData();
                    }
                }
            }
        });

    }

    @OnClick(R.id.progress_spinner)
    public void submit() {
        loadData();
    }


    @Override
    public void onListItemClick(int index)
    {
        //get image resource
        int imageResource = 0;
        if(index == 0) imageResource = R.drawable.nutella;
        else if(index == 1) imageResource = R.drawable.brownies;
        else if(index == 2) imageResource = R.drawable.yellowcake;
        else if(index == 3) imageResource = R.drawable.cheesecake;
        else imageResource = R.drawable.cheesecake;


       //use this to open intent for next page
        Intent intent = new Intent(this , details.class);
        intent.putExtra(EXTRA_TITLE,  BakingList.get(index).getName());
        intent.putExtra(EXTRA_IMG, imageResource);
        intent.putParcelableArrayListExtra(EXTRA_STEPS,  BakingList.get(index).getMyStepsArr());
        intent.putParcelableArrayListExtra(EXTRA_INGREDIENTS,  BakingList.get(index).getMyIngredientsArr());
        startActivity(intent);
    }


    private void saveToDataBase()
    {
        if(BakingList.size() > 0) {
            ContentValues[] contentValues = new ContentValues[BakingList.size()];

            //This takes in a put string that identifies the kind of data being put and the data
            for (int i = 0; i < BakingList.size(); i++) {
                contentValues[i] = new ContentValues();
                contentValues[i].put(Recipe_Contract.COLUMN_RECIPE_ID, BakingList.get(i).getId());
                contentValues[i].put(Recipe_Contract.COLUMN_NAME, BakingList.get(i).getName());


                Baking.steps[] steps = BakingList.get(i).getMySteps();
                ContentValues[] cv_steps = new ContentValues[steps.length];

                //This takes in a put string that identifies the kind of data being put and the data
                for (int k = 0; k < steps.length ; k++) {
                    cv_steps[k] = new ContentValues();
                    cv_steps[k].put(Steps_Contract.COLUMN_RECIPE_ID, BakingList.get(i).getId());
                    cv_steps[k].put(Steps_Contract.COLUMN_SHORT_DESCRIPTION, steps[k].shortDescription);
                   // if(steps[k].videoURL.length() == 0)steps[k].videoURL = "empty";
                    cv_steps[k].put(Steps_Contract.COLUMN_VIDEO_URL, steps[k].videoURL);
                    //if(steps[k].thumbnailURL.length() == 0)steps[k].thumbnailURL = "empty";
                    cv_steps[k].put(Steps_Contract.COLUMN_THUMBNAIL_URL, steps[k].thumbnailURL);
                }

                //int steps_rows = getContentResolver().bulkInsert(Steps.CONTENT_URI, cv_steps);

                Baking.ingredients[] ingredients = BakingList.get(i).getMyIngredients();
                ContentValues[] cv_ingredients = new ContentValues[ingredients.length];

                //This takes in a put string that identifies the kind of data being put and the data
                for (int j = 0; j < ingredients.length ; j++) {
                    cv_ingredients[j] = new ContentValues();
                    cv_ingredients[j].put(Ingredients_Contract.COLUMN_RECIPE_ID, BakingList.get(i).getId());
                    cv_ingredients[j].put(Ingredients_Contract.COLUMN_QUANTITY, ingredients[j].quantity);
                    cv_ingredients[j].put(Ingredients_Contract.COLUMN_MEASURE, ingredients[j].measure);
                    cv_ingredients[j].put(Ingredients_Contract.COLUMN_INGREDIENT, ingredients[j].ingredient);
                }

               // int ingredient_rows = getContentResolver().bulkInsert(Ingredients.CONTENT_URI, cv_ingredients);
            }

           // int recipe_rows = getContentResolver().bulkInsert(Recipes.CONTENT_URI, contentValues);

            Editor editor = pref.edit();
            saved_Content = true;
            editor.putBoolean(getString(R.string.contentSaved), saved_Content);
            editor.commit();

        }

    }


    //Method isNetworkAvailable
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    private void ReloadOption() {
        if (!isNetworkAvailable()) {
            // Create an Alert Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Set the Alert Dialog Message
            builder.setMessage("Internet Connection Required")
                    .setCancelable(false)
                    .setPositiveButton("Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    loadData();
                                    // Restart the Activity
                                    //Intent intent = getIntent();
                                    // finish();
                                    //startActivity(intent);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @VisibleForTesting
    @NonNull
    public CountingIdlingResource getIdlingResource() {
        return mIdlingResource;
    }

}
