package com.chibusoft.bakingtime;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chibusoft.bakingtime.Baking.steps;
import com.chibusoft.bakingtime.BakingAdapter.ListItemClickListener;
import com.chibusoft.bakingtime.Network.GetDataService;
import com.chibusoft.bakingtime.Network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BakingAdapter.ListItemClickListener{

    private RecyclerView mBaking_RV;
    private BakingAdapter bakingAdapter;
    private List<Baking> BakingList;

    public static final String EXTRA_STEPS = "Steps";
    public static final String EXTRA_INGREDIENTS = "ingredients";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();



    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.containsKey("Bake")) {
            loadData();
        }
        else {
            BakingList = savedInstanceState.getParcelableArrayList("Bake");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("Bake", (ArrayList)BakingList);
        super.onSaveInstanceState(outState);
    }



    public void loadData()
    {
          /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Baking>> call = service.getData();
        call.enqueue(new Callback<List<Baking>>() {
            @Override
            public void onResponse(Call<List<Baking>> call, Response<List<Baking>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Baking>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void generateDataList(List<Baking> baking) {

        BakingList = baking;
        mBaking_RV = (RecyclerView) findViewById(R.id.rv_baking);
        bakingAdapter = new BakingAdapter(baking,this);

        boolean isPhone = getResources().getBoolean(R.bool.is_phone);
        boolean isPort = getResources().getBoolean(R.bool.is_port);

        if (isPhone && isPort) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mBaking_RV.setLayoutManager(layoutManager);
       } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            mBaking_RV.setLayoutManager(gridLayoutManager);
        }

        mBaking_RV.setHasFixedSize(true);
        mBaking_RV.setAdapter(bakingAdapter);

    }

    @Override
    public void onListItemClick(int index)
    {
        List<Baking.steps> my = BakingList.get(index).getMyStepsArr();
       //use this to open intent for next page
        Intent intent = new Intent(this , details.class);
        intent.putParcelableArrayListExtra(EXTRA_STEPS,  BakingList.get(index).getMyStepsArr());
        intent.putParcelableArrayListExtra(EXTRA_INGREDIENTS,  BakingList.get(index).getMyIngredientsArr());
        startActivity(intent);
    }
}
