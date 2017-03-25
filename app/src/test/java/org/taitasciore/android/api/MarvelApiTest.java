package org.taitasciore.android.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.taitasciore.android.wallapoptest.BuildConfig;

import static org.junit.Assert.*;

/**
 * Created by roberto on 24/03/17.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class MarvelApiTest {

    MarvelApiImpl mApi;

    @Before
    public void setUp() throws Exception {
        mApi = Mockito.mock(MarvelApiImpl.class);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(mApi);
    }
}