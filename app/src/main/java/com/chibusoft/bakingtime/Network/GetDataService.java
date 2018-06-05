package com.chibusoft.bakingtime.Network;

import com.chibusoft.bakingtime.Baking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by EBELE PC on 6/4/2018.
 */

public interface GetDataService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Baking>> getData();
}
