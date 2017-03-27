package org.taitasciore.android.wallapoptest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wang.avi.AVLoadingIndicatorView;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.marvelmodel.Image;
import org.taitasciore.android.marvelmodel.Price;
import org.taitasciore.android.presenter.DetailsPresenter;
import org.taitasciore.android.presenter.DetailsPresenterImpl;
import org.taitasciore.android.view.DetailsView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by roberto on 21/03/17.
 */

public class DetailsFragment extends Fragment implements DetailsView {

    public static final String TAG = "details_fragment";
    public static final String PARAM_COMIC_ID = "comic_id";

    private int mComicId;

    @BindView(R.id.img) SimpleDraweeView mImg;
    @BindView(R.id.tvTitle) TextView mTvTitle;
    @BindView(R.id.tvPrice) TextView mTvPrice;
    @BindView(R.id.tvDescription) TextView mTvDescription;

    @BindView(R.id.loader) AVLoadingIndicatorView mLoader;
    @BindView(R.id.btnRetry) Button mBtnRetry;
    @OnClick(R.id.btnRetry) void onRetry() {
        hideRetryButton();
        mPresenter.getComicById(mComicId);
    }

    private LinearLayout mHeader;

    private DetailsPresenter mPresenter;

    public static DetailsFragment newInstance(int comicId) {
        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM_COMIC_ID, comicId);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, v);
        mHeader = (LinearLayout) v.findViewById(R.id.lyHeader);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mComicId = getArguments().getInt(PARAM_COMIC_ID);

        if (savedInstanceState == null && mPresenter.getSavedComic() == null) {
            mPresenter.getComicById(mComicId);
        } else if (mPresenter.getSavedComic() != null) {
            showComicInfo(mPresenter.getSavedComic());
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
        else mPresenter = new DetailsPresenterImpl(this);
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
     * Shows comic's title, print price, description and random image
     * @param comic Comic whose information will be shown
     */
    @Override
    public void showComicInfo(Comic comic) {
        mTvTitle.setText(comic.getTitle());
        setPrice(comic);
        mTvDescription.setText(comic.getDescription());
        Image randomImg = getRandomImage(comic.getImages());
        String imgPath = randomImg.getPath() + "." + randomImg.getExtension();
        mImg.setImageURI(Uri.parse(imgPath));
        mHeader.setVisibility(View.VISIBLE);
    }

    private void setPrice(Comic comic) {
        List<Price> prices = comic.getPrices();
        Log.i("size", prices.size()+"");
        if (prices != null && !prices.isEmpty() && prices.get(0).getPrice() > 0) {
            mTvPrice.setText("$" + prices.get(0).getPrice()+"");
        }
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
     * Returns random image from the {@link Comic}'s image list
     * @param list {@link Comic}'s image list
     * @return Random {@link Image}
     */
    private Image getRandomImage(List<Image> list) {
        Random r = new Random();
        return list.get(r.nextInt(list.size()));
    }
}
