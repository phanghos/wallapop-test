package org.taitasciore.android.wallapoptest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.presenter.ComicsPresenter;
import org.taitasciore.android.presenter.ComicsPresenterImpl;
import org.taitasciore.android.view.ComicsView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by roberto on 21/03/17.
 */

/**
 * A retained fragment is used to better handle configuration changes, in particular,
 * screen orientation changes that would cause the app to crash if, for instance,
 * the background task which sends requests to the API were in process
 * when the screen were rotated
 */
public class ComicsFragment extends Fragment implements ComicsView, ComicAdapter.OnItemClickListener {

    public static final String TAG = "comics_fragment";

    @BindView(R.id.list) RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutMngr;
    private ComicAdapter mAdapter;

    @BindView(R.id.loader) AVLoadingIndicatorView mLoader;
    @BindView(R.id.btnRetry) Button mBtnRetry;

    /**
     * Hides retry button and tries to send the request again if the retry button is clicked on
     */
    @OnClick(R.id.btnRetry) void onRetry() {
        hideRetryButton();
        mPresenter.getComics();
    }

    // Presenter for this view
    private ComicsPresenter mPresenter;

    /**
     * Sets this fragment as a retained with the call to setRetainInstance(true)
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comics, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();

        if (savedInstanceState == null && mPresenter.getSavedComics() == null) {
            mPresenter.getComics();
        } else if (mPresenter.getSavedComics() != null) {
            showComics(mPresenter.getSavedComics());
        } else if (mPresenter.isRequestInProcess()) {
            showProgress();
        } else {
            showRetryButton();
        }
    }

    /**
     * Bind current view to the presenter if the instance is non-null.
     * Instantiates the presenter and attaches the current view otherwise
     * @param context Current context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mPresenter != null) mPresenter.onViewAttached(this);
        else mPresenter = new ComicsPresenterImpl(this);
    }

    /**
     * Calls onDestroyed() on the presenter when the fragment is being destroyed to make sure
     * all references are disposed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroyed();
        mPresenter = null;
    }

    /**
     * Shows loading animation
     */
    @Override
    public void showProgress() {
        mLoader.setVisibility(View.VISIBLE);
    }

    /**
     * Hides loading animation
     */
    @Override
    public void hideProgress() {
        mLoader.setVisibility(View.GONE);
    }

    /**
     * Shows a {@link Toast} with a message if something went wrong with a request
     */
    @Override
    public void showError() {
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a {@link Toast} with a message if there is no network connection
     */
    @Override
    public void showNetworkError() {
        Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows retry button
     */
    @Override
    public void showRetryButton() {
        mBtnRetry.setVisibility(View.VISIBLE);
    }

    /**
     * Hides retry button
     */
    @Override
    public void hideRetryButton() {
        mBtnRetry.setVisibility(View.GONE);
    }

    /**
     * Adds list of comics returned by the API to the {@link ComicAdapter}
     * @param comics List of comics returned by the API
     */
    @Override
    public void showComics(List<Comic> comics) {
        for (Comic comic : comics) mAdapter.add(comic);
    }

    /**
     * Method invoked by the {@link ComicAdapter} when a row is clicked on
     * @param position Position of the row clicked in order to get the comic in that position,
     * and then its ID to be sent to the {@link DetailsActivity} as an extra in the
     * {@link Intent}
     */
    @Override
    public void onItemClicked(int position) {
        Comic comic = mAdapter.get(position);
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        i.putExtra(DetailsFragment.PARAM_COMIC_ID, comic.getId());
        startActivity(i);
    }

    /**
     * Sets up {@link RecyclerView} by setting its {@link android.support.v7.widget.RecyclerView.LayoutManager},
     * its adapter, and the {@link ComicAdapter}'s implementation of the
     * {@link org.taitasciore.android.wallapoptest.ComicAdapter.OnItemClickListener} interface
     * for communication between {@link ComicAdapter} and {@link ComicsFragment}
     */
    private void setupRecyclerView() {
        mLayoutMngr = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutMngr);
        mAdapter = new ComicAdapter();
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
