package com.store.quickrcars.api;

import retrofit.RestAdapter;

/**
 * Created by hp pc on 11-07-2015.
 */
public class CarStoreApiClient {

    private final static String API_URL = "http://quikr.0x10.info/";
    private static CarStoreApiInterface storeApiInterface;

    private static RestAdapter getRestAdapter()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter;
    }

    public static CarStoreApiInterface getCarStoreApi() {
        if (storeApiInterface == null)
            storeApiInterface = getRestAdapter().create(CarStoreApiInterface.class);

        return storeApiInterface;
    }
}
