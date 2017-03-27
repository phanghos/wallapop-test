package org.taitasciore.android.wallapoptest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by roberto on 26/03/17.
 */

public class DetailsActivity extends SwipeBackActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setupToolbar();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        if (savedInstanceState == null) {
            int comicId = getIntent().getIntExtra(DetailsFragment.PARAM_COMIC_ID, 0);

            if (comicId != 0) {
                DetailsFragment f = DetailsFragment.newInstance(comicId);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.content, f, DetailsFragment.TAG).commit();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Sets toolbar as action bar, sets title, and sets back arrow as action bar indicator
     */
    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Details");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    }

    /**
     * When the action bar's indicator (a back arrow in this case) is clicked on, a back press
     * will be simulated, thus closing the activity
     * @param item Item
     * @return True if event was handled. False otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
