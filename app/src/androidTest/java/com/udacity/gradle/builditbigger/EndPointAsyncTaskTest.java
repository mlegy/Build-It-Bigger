package com.udacity.gradle.builditbigger;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by ahmad on 1/20/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EndPointAsyncTaskTest implements AsyncResponse {
    private CountDownLatch signal;
    private String joke;

    @Test
    public void testGetJokeAsyncTask() throws Throwable {
        signal = new CountDownLatch(1);
        EndPointAsyncTask endPointAsyncTask = new EndPointAsyncTask();
        endPointAsyncTask.delegate = this;
        endPointAsyncTask.execute();
        signal.await(10, TimeUnit.SECONDS);
        assertNotNull("joke is null", joke);
        assertFalse("joke is empty", joke.isEmpty());
    }

    @Override
    public void processFinish(String output) {
        this.joke = output;
        signal.countDown();
    }
}
