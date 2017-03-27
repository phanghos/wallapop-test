package org.taitasciore.android.wallapoptest;

import android.support.v4.app.Fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by roberto on 23/03/17.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class ComicsActivityTest {

    ComicsActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.setupActivity(ComicsActivity.class);
    }

    @Test
    public void activityShouldNotBeNull() throws Exception {
        assertNotNull(mActivity);
    }

    @Test
    public void fragmentShouldNotBeNull() throws Exception {
        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentByTag(ComicsFragment.TAG);
        assertNotNull(fragment);
    }

    @Test
    public void actionBarShouldNotBeNull() throws Exception {
        assertNotNull(mActivity.getSupportActionBar());
    }

    @Test
    public void actionBarShouldShowCorrectTitle() throws Exception {
        assertEquals("Comics", mActivity.getSupportActionBar().getTitle());
    }
}