package org.taitasciore.android.wallapoptest;

import android.os.Build;
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
public class MainActivityTest {

    MainActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(mActivity);
        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentByTag("comics_fragment");
        assertNotNull(fragment);
    }
}