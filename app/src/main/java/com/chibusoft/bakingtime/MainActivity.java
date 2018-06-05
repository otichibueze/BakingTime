package com.chibusoft.bakingtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chibusoft.bakingtime.Network.GetDataService;
import com.chibusoft.bakingtime.Network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BakingAdapter.ListItemClickListener{

    private RecyclerView mBaking_RV;
    private BakingAdapter bakingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();



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
        mBaking_RV = (RecyclerView) findViewById(R.id.rv_baking);
        bakingAdapter = new BakingAdapter(this,baking,this);

        boolean isPhone = getResources().getBoolean(R.bool.is_phone);

        if (isPhone) {
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
    public void onListItemClick(int clickedItemIndex)
    {
       //use this to open intent for next page
    }
}
