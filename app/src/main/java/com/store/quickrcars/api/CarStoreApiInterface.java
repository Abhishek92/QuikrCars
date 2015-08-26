package com.store.quickrcars.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by hp pc on 11-07-2015.
 */
public interface CarStoreApiInterface {

    @GET("/api/cars?type=json&query=list_cars")
    void getCarsDetail(Callback<List<CarDetailModel>> gamesDetailModelCallback);
    @GET("/api/cars?type=json&query=api_hits")
    void getApiCounter(Callback<CarStoreApiHitModel> gameApiHitModelCallback);
}
