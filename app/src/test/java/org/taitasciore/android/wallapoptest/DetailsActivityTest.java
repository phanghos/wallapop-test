package org.taitasciore.android.wallapoptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by roberto on 26/03/17.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class DetailsActivityTest {

    ActivityController mController;
    DetailsActivity mActivity;

    @Test
    public void shouldNotBeNull() throws Exception {
        mActivity = Robolectric.setupActivity(DetailsActivity.class);
        assertNotNull(mActivity);
    }

    @Test
    public void fragmentShouldNotBeNull() {
        Intent i = new Intent();
        i.putExtra(DetailsFragment.PARAM_COMIC_ID, 1);
        mController = Robolectric.buildActivity(DetailsActivity.class, i);
        mActivity = (DetailsActivity) mController.create(null).visible().get();
        Fragment f = mActivity.getSupportFragmentManager().findFragmentByTag(DetailsFragment.TAG);
        assertNotNull(f);
    }

    @Test
    public void fragmentShouldBeNull() {
        mActivity = Robolectric.setupActivity(DetailsActivity.class);
        Fragment f = mActivity.getSupportFragmentManager().findFragmentByTag(DetailsFragment.TAG);
        assertNull(f);
        mController = Robolectric.buildActivity(DetailsActivity.class);
        mActivity = (DetailsActivity) mController.create(new Bundle()).visible().get();
        f = mActivity.getSupportFragmentManager().findFragmentByTag(DetailsFragment.TAG);
        assertNull(f);
    }

    @Test
    public void actionBarShouldNotBeNull() throws Exception {
        mActivity = Robolectric.setupActivity(DetailsActivity.class);
        assertNotNull(mActivity.getSupportActionBar());
    }

    @Test
    public void actionBarShouldShowCorrectTitle() throws Exception {
        mActivity = Robolectric.setupActivity(DetailsActivity.class);
        assertEquals("Details", mActivity.getSupportActionBar().getTitle());
    }
}