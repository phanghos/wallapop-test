package org.taitasciore.android.wallapoptest;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.wang.avi.AVLoadingIndicatorView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

/**
 * Created by roberto on 24/03/17.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class ComicsFragmentTest {

    ComicsActivity mActivity;
    ComicsFragment mFragment;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.setupActivity(ComicsActivity.class);
        mFragment = new ComicsFragment();
        mActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.content, mFragment, "comics_fragment").commit();
    }

    @Test
    public void fragmentShouldNotBeNull() throws Exception {
        assertNotNull(mFragment);
    }

    @Test
    public void viewShouldNotBeNull() throws Exception {
        assertNotNull(mFragment.getView());
    }

    @Test
    public void shouldShowLoader() throws Exception {
        mFragment.showProgress();
        View v = mFragment.getView();
        AVLoadingIndicatorView loader = (AVLoadingIndicatorView)
                v.findViewById(R.id.loader);
        assertEquals(View.VISIBLE, loader.getVisibility());
    }

    @Test
    public void shouldHideLoader() throws Exception {
        mFragment.hideProgress();
        View v = mFragment.getView();
        AVLoadingIndicatorView loader = (AVLoadingIndicatorView)
                v.findViewById(R.id.loader);
        assertEquals(View.GONE, loader.getVisibility());
    }

    @Test
    public void shouldShowRetryButton() throws Exception {
        mFragment.showRetryButton();
        View v = mFragment.getView();
        Button btn = (Button) v.findViewById(R.id.btnRetry);
        assertEquals(View.VISIBLE, btn.getVisibility());
    }

    @Test
    public void shouldHideRetryButton() throws Exception {
        mFragment.hideRetryButton();
        View v = mFragment.getView();
        Button btn = (Button) v.findViewById(R.id.btnRetry);
        assertEquals(View.GONE, btn.getVisibility());
    }

    @Test
    public void shouldHideRetryButtonWhenClicked() throws Exception {
        View v = mFragment.getView();
        Button btn = (Button) v.findViewById(R.id.btnRetry);
        btn.performClick();
        assertEquals(View.GONE, btn.getVisibility());
    }

    @Test
    public void shoulShowToastWhenError() throws Exception {
        mFragment.showError();
        assertThat(ShadowToast.getTextOfLatestToast(),
                equalTo(mActivity.getString(R.string.error)));
    }

    @Test
    public void shoulShowToastWhenNetworkError() throws Exception {
        mFragment.showNetworkError();
        assertThat(ShadowToast.getTextOfLatestToast(),
                equalTo(mActivity.getString(R.string.network_error)));
    }

    @Test
    public void shouldRecreateFragment() throws Exception {
        assertNotNull(mFragment);
        mActivity.recreate();
        Fragment newFragment = mActivity.getSupportFragmentManager()
                .findFragmentByTag("comics_fragment");
        assertNotNull(newFragment);
        assertSame(mFragment, newFragment);
    }
}