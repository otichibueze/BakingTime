package com.chibusoft.bakingtime;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.AppBarLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chibusoft.bakingtime.IdlingResource.SimpleIdlingResource;
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

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
        mProgress.setVisibility(View.VISIBLE);
          /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Baking>> call = service.getData();
        call.enqueue(new Callback<List<Baking>>() {
            @Override
            public void onResponse(Call<List<Baking>> call, Response<List<Baking>> response) {
                generateDataList(response.body());

                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }
            }

            @Override
            public void onFailure(Call<List<Baking>> call, Throwable t) {
                mProgress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,
                        "Something went wrong...Please check your network connection!", Toast.LENGTH_SHORT).show();
                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }
            }
        });
    }


    private void generateDataList(List<Baking> baking) {

        mProgress.setVisibility(View.GONE);
        BakingList = baking;
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

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

}
