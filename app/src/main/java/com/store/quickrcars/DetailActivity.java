package com.store.quickrcars;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.store.quickrcars.api.CarDetailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class DetailActivity extends AppCompatActivity implements
        ShareActionProvider.OnShareTargetSelectedListener {

    private CarDetailModel carDetailModel;
    private TextView mCarName;
    private TextView mRating;
    private TextView mColor;
    private TextView mCC;
    private TextView mAbs;
    private TextView mType;
    private TextView mMilage;
    private TextView mDescription;
    private WebView mChartView;
    private ImageView mCarImage;
    private final String PIE_CHART_URL = "file:///android_asset/PieChart.html";
    private Toolbar mToolBar;
    private List<CarDetailModel.City> demographicList;
    private JSONObject mJsonData;
    private JSONArray mJsonArray;
    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mCarName = (TextView) findViewById(R.id.carName);
        mRating = (TextView) findViewById(R.id.rating);
        mColor = (TextView) findViewById(R.id.color);
        mCC = (TextView) findViewById(R.id.cc);
        mAbs = (TextView) findViewById(R.id.abs);
        mMilage = (TextView) findViewById(R.id.milage);
        mType = (TextView) findViewById(R.id.type);
        mDescription = (TextView) findViewById(R.id.description);
        mCarImage = (ImageView) findViewById(R.id.carImg);
        mChartView = (WebView)findViewById(R.id.pieChart);
        mToolBar = (Toolbar)findViewById(R.id.toolbar);
        carDetailModel = EventBus.getDefault().removeStickyEvent(CarDetailModel.class);

        if(carDetailModel != null)
            setValues();

        setUpToolBar();
    }

    private void setUpToolBar()
    {
        mToolBar.setTitle(getString(R.string.car_detail));
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setValues()
    {
        mCarName.setText(carDetailModel.getName());
        mRating.setText("Rating: "+carDetailModel.getRating());
        mCC.setText("CC: "+carDetailModel.getEngineCc());
        mAbs.setText("ABS: "+carDetailModel.getAbsExist());
        mType.setText("Type: "+carDetailModel.getType());
        mMilage.setText("Mileage: "+carDetailModel.getMileage());
        mColor.setBackgroundColor(Color.parseColor(carDetailModel.getColor()));
        mDescription.setText("Description: " + carDetailModel.getDescription());
        Picasso.with(this).load(carDetailModel.getImage()).into(mCarImage);
        demographicList = new ArrayList<>(carDetailModel.getCities());
        
        if(demographicList.size() != 0)
            createJson();

        loadChart();
    }

    private void loadChart()
    {
        WebSettings settings = mChartView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        mChartView.addJavascriptInterface(new JsonString(), "jsonString");
        mChartView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cm) {
                Log.d("MyApplication", cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId());
                return true;
            }
        });
        mChartView.setWebViewClient(new ChartWebViewClient(DetailActivity.this));
        mChartView.loadUrl(PIE_CHART_URL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = (MenuItem) menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(getDefaultShareIntent(carDetailModel.getName(), carDetailModel.getLink()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open_browser) {
            openCarLink(carDetailModel.getLink());
            return true;
        }
        if (id == R.id.action_send_sms) {
            sendPlayStoreLinkThroughSms(carDetailModel.getName(), carDetailModel.getLink());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createJson() {
        try {
            mJsonArray = new JSONArray();
            for (int i = 0; i < demographicList.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("city", demographicList.get(i).getCity());
                object.put("users", demographicList.get(i).getUsers());
                mJsonArray.put(object);
            }
            mJsonData = new JSONObject();
            mJsonData.put("data", mJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** Returns a share intent */
    private Intent getDefaultShareIntent(String name,String link){
        String text = "Hey check out this cool car,"+name+" here is the link: \n"+link;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        return intent;
    }

    private void openCarLink(String link)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    private void sendPlayStoreLinkThroughSms(String name, String link)
    {
        String sms_text = "Hey check out this cool car,"+name+" here is the link: \n"+link;
        Uri uri = Uri.parse("smsto:" + "");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", sms_text);
        startActivity(intent);
    }

    @Override
    public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
        return false;
    }

    private class JsonString {
        @JavascriptInterface
        public String getJsonString() {
            return mJsonData.toString();
        }
    }
}
