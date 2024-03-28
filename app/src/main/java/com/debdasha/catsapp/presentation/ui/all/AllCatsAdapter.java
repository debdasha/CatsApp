package com.debdasha.catsapp.presentation.ui.all;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.debdasha.catsapp.R;
import com.debdasha.catsapp.presentation.models.CatUI;

public class AllCatsAdapter extends
        RecyclerView.Adapter<AllCatsViewHolder> {

    private static final String TAG = AllCatsAdapter.class.getSimpleName();
    private Activity context;
    @NonNull
    private List<CatUI> cats = new ArrayList<>();
    @NonNull
    private OnLikeClickListener onLikeClickListener;
    @NonNull
    private OnDownloadClickListener onDownloadClickListener;

    public AllCatsAdapter(Activity context) {
        this.context = context;
    }


    @Override
    public AllCatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_cat, parent, false);

        AllCatsViewHolder allCatsViewHolder = new AllCatsViewHolder(view);

        return allCatsViewHolder;
    }


    @Override
    public void onBindViewHolder(AllCatsViewHolder holder, int position) {
        CatUI item = cats.get(position);
        holder.bind(context,item, onLikeClickListener, onDownloadClickListener);
    }


    @Override
    public int getItemCount() {
        return cats.size();
    }

    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        this.onLikeClickListener = onLikeClickListener;
    }

    public void setOnDownloadClickListener(OnDownloadClickListener onDownloadClickListener) {
        this.onDownloadClickListener = onDownloadClickListener;
    }


    public void refreshData(@NonNull List<CatUI> newData) {
        cats.clear();
        cats.addAll(newData);
        notifyDataSetChanged();
    }

    public void addData(@NonNull List<CatUI> newData) {
        cats.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    public List<CatUI> getItems() {
        return cats;
    }

    public interface OnLikeClickListener {
        void onLikeClick(CatUI catUI);
    }

    public interface OnDownloadClickListener {
        void onDownloadClick(CatUI catUI);
    }

}