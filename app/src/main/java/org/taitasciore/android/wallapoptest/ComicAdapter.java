package org.taitasciore.android.wallapoptest;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.marvelmodel.Thumbnail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roberto on 21/03/17.
 */

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {

    private List<Comic> mComics;
    private OnItemClickListener mListener;

    public ComicAdapter() {
        mComics = new ArrayList<>();
    }

    interface OnItemClickListener {

        void onItemClicked(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comic_row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comic comic = mComics.get(position);
        holder.title.setText(comic.getTitle());
        Thumbnail thumbnail = comic.getThumbnail();
        String thumbnailPath = thumbnail.getPath() + "." + thumbnail.getExtension();
        holder.img.setImageURI(Uri.parse(thumbnailPath));
    }

    @Override
    public int getItemCount() {
        return mComics.size();
    }

    public Comic get(int position) {
        return mComics.get(position);
    }

    public void add(Comic comic) {
        mComics.add(comic);
        notifyItemInserted(getItemCount());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img) SimpleDraweeView img;
        @BindView(R.id.tvTitle) TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) mListener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }
}
