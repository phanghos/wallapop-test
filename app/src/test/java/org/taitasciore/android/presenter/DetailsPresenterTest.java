package org.taitasciore.android.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.taitasciore.android.view.ComicsView;
import org.taitasciore.android.view.DetailsView;
import org.taitasciore.android.wallapoptest.BuildConfig;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by roberto on 24/03/17.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class DetailsPresenterTest {

    DetailsView mView;
    DetailsPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mView = Mockito.mock(DetailsView.class);
        mPresenter = Mockito.mock(DetailsPresenterImpl.class);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(mView);
        assertNotNull(mPresenter);
    }

    @Test
    public void shouldAttachView() throws Exception {
        mPresenter.onViewAttached(mView);
        verify(mPresenter).onViewAttached(mView);
    }

    @Test
    public void shouldDetachView() throws Exception {
        mPresenter.onViewDetached();
        verify(mPresenter).onViewDetached();
    }
}