package com.gojektestapp.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.gojektestapp.R;
import com.gojektestapp.net.model.Repository;

import java.util.List;

import javax.inject.Inject;


public class TrendingListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    @Inject
    Context context;
    List<Repository> data;

    @Inject
    public TrendingListAdapter() {

    }

    public void setData(List<Repository> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Repository repository = data.get(position);
        bindView(holder, position, repository);

        boolean expanded = repository.isExpanded();
        // Set the visibility based on state
        holder.getView(R.id.detailLayout).setVisibility(expanded ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            // Get the current state of the item

            // Change the state
            repository.setExpanded(!repository.isExpanded());
            // Notify the adapter that item has changed
            notifyItemChanged(position);
        });

    }

    private void bindView(BaseViewHolder holder, int position, Repository repository) {
        ((TextView) holder.getView(R.id.name)).setText(repository.getName());
        ((TextView) holder.getView(R.id.title)).setText(repository.getAuthor());
        ((TextView) holder.getView(R.id.detailText)).setText(repository.getDescription());
        if (!TextUtils.isEmpty(repository.getLanguage())) {
            holder.getView(R.id.lang).setVisibility(View.VISIBLE);
            ((TextView) holder.getView(R.id.lang)).setText(repository.getLanguage());
            setTextViewDrawableColor(holder.getView(R.id.lang), repository.getLanguageColor());
        } else {
            holder.getView(R.id.lang).setVisibility(View.GONE);
        }
        ((TextView) holder.getView(R.id.vote)).setText(String.valueOf(repository.getStars()));
        ((TextView) holder.getView(R.id.frok)).setText(String.valueOf(repository.getForks()));
        if (context != null)
            Glide.with(context).load(repository.getAvatar()).transition(DrawableTransitionOptions.withCrossFade()).apply(RequestOptions.circleCropTransform()).into(((ImageView) holder.getView(R.id.user_icon)));
    }

    private void setTextViewDrawableColor(TextView textView, String color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


}
