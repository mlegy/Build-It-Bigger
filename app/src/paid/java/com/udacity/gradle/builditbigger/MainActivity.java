package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jokedisplayer.DisplayActivity;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    private String joke;
    private ProgressBar mProgressBar;
    private View mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mFragment = findViewById(R.id.fragment);

        mFragment.setVisibility(View.VISIBLE);
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

    public void tellJoke(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        mFragment.setVisibility(View.GONE);
        EndPointAsyncTask endPointAsyncTask = new EndPointAsyncTask();
        endPointAsyncTask.delegate = this;
        endPointAsyncTask.execute();
    }

    @Override
    public void processFinish(String output) {
        this.joke = output;
        showJoke();
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
