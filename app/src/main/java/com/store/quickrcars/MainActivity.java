package com.store.quickrcars;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.store.quickrcars.api.CarDetailModel;
import com.store.quickrcars.api.CarStoreApiClient;
import com.store.quickrcars.api.CarStoreApiHitModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private GridView mListView;
    private CarListAdapter mAdapter;
    private TextView mApiCount;
    private TextView mCarCount;
    private List<CarDetailModel> carList;
    private int TOTAL_CAR_COUNT = 0;
    private ProgressBar mProgressBar;

    private Comparator<CarDetailModel> ratingComparator = new Comparator<CarDetailModel>() {
        @Override
        public int compare(CarDetailModel lhs, CarDetailModel rhs) {
            return Float.valueOf(rhs.getRating()).compareTo(Float.valueOf(lhs.getRating()));
        }
    };

    private Comparator<CarDetailModel> priceComparator = new Comparator<CarDetailModel>() {
        @Override
        public int compare(CarDetailModel lhs, CarDetailModel rhs) {
            return Float.valueOf(rhs.getPrice()).compareTo(Float.valueOf(lhs.getPrice()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (GridView) findViewById(R.id.list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mApiCount = (TextView) findViewById(R.id.apiCount);
        mCarCount = (TextView) findViewById(R.id.carCount);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mToolbar.setTitle(getString(R.string.car));
        mToolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(mToolbar);

        getCarsList();
        getNoOfApiHits();

        carList = new ArrayList<>();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CarDetailModel model = (CarDetailModel) adapterView.getItemAtPosition(i);
                EventBus bus = EventBus.getDefault();
                bus.postSticky(model);
                startActivity(new Intent(MainActivity.this, DetailActivity.class));
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        SearchManager searchManager = (SearchManager)this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Car by name");

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(mAdapter != null)
                    mAdapter.getFilter().filter(newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                if(mAdapter != null)
                    mAdapter.getFilter().filter(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_price) {
            sortList(priceComparator);
            return true;
        }

        if (id == R.id.action_sort_rating) {
            sortList(ratingComparator);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getCarsList()
    {
        if(Util.isNetworkConnected(this)) {
            mProgressBar.setVisibility(View.VISIBLE);
            CarStoreApiClient.getCarStoreApi().getCarsDetail(new Callback<List<CarDetailModel>>() {
                @Override
                public void success(List<CarDetailModel> carDetailModels, Response response) {
                    if (response.getStatus() == 200) {
                        mProgressBar.setVisibility(View.GONE);
                        carList = new ArrayList<CarDetailModel>(carDetailModels);
                        mAdapter = new CarListAdapter(MainActivity.this, carList);
                        mListView.setAdapter(mAdapter);
                        TOTAL_CAR_COUNT = carDetailModels.size();
                        mCarCount.setText("Car Count: " + TOTAL_CAR_COUNT);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        else
            Toast.makeText(this,getString(R.string.check_internet),Toast.LENGTH_LONG).show();
    }

    private void getNoOfApiHits()
    {
        if(Util.isNetworkConnected(this)) {
            CarStoreApiClient.getCarStoreApi().getApiCounter(new Callback<CarStoreApiHitModel>() {
                @Override
                public void success(CarStoreApiHitModel carStoreApiHitModel, Response response) {
                    if (response.getStatus() == 200)
                        mApiCount.setText("Api Count: " + carStoreApiHitModel.getApiHits());
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }


    private void sortList(Comparator<CarDetailModel> comparator)
    {
        Collections.sort(carList, comparator);
        mAdapter = new CarListAdapter(this, carList);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
