package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.JokeTeller;
import com.example.jokedisplayer.DisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity implements AsyncResponse {
    InterstitialAd mInterstitialAd;
    private String joke;
    private ProgressBar mProgressBar;
    private View mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mFragment = findViewById(R.id.fragment);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showJoke();
            }
        });

        requestNewInterstitial();

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJokeFromAndroidLib(View view) {
        this.joke = new JokeTeller().getJoke();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            showJoke();
        }
    }

    public void tellJokeFromGCE(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        mFragment.setVisibility(View.GONE);
        EndPointAsyncTask endPointAsyncTask = new EndPointAsyncTask();
        endPointAsyncTask.delegate = this;
        endPointAsyncTask.execute();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            showJoke();
        }
    }

    @Override
    public void processFinish(String output) {
        this.joke = output;
        mProgressBar.setVisibility(View.GONE);
        mFragment.setVisibility(View.VISIBLE);
    }

    private void showJoke() {
        if (joke != null && !joke.isEmpty()) {
            Intent jokeIntent = new Intent(this, DisplayActivity.class);
            jokeIntent.putExtra("joke", joke);
            startActivity(jokeIntent);
        } else {
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
        }
    }
}
