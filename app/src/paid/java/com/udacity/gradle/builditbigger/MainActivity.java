package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.JokeTeller;
import com.example.jokedisplayer.DisplayActivity;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    private String joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        showJoke();

    }

    public void tellJokeFromGCE(View view) {
        EndPointAsyncTask endPointAsyncTask = new EndPointAsyncTask();
        endPointAsyncTask.delegate = this;
        endPointAsyncTask.execute();
        showJoke();
    }

    @Override
    public void processFinish(String output) {
        this.joke = output;
    }

    private void showJoke() {
        Intent jokeIntent = new Intent(this, DisplayActivity.class);
        jokeIntent.putExtra("joke", joke);
        startActivity(jokeIntent);
    }
}