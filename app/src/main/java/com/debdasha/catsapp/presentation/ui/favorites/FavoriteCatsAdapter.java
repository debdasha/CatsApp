package com.debdasha.catsapp.presentation.ui.favorites;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.debdasha.catsapp.R;
import com.debdasha.catsapp.presentation.models.CatUI;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCatsAdapter extends
        RecyclerView.Adapter<FavoriteCatsViewHolder> {

    private static final String TAG = FavoriteCatsAdapter.class.getSimpleName();

    private Activity context;
    private List<CatUI> list = new ArrayList<>();
    private onDeleteCatListener onDeleteCatListener;

    public FavoriteCatsAdapter(Activity context,
                               onDeleteCatListener onDeleteCatListener) {
        this.context = context;
        this.onDeleteCatListener = onDeleteCatListener;
    }


    @Override
    public FavoriteCatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_favorite_cat, parent, false);

        FavoriteCatsViewHolder viewHolder = new FavoriteCatsViewHolder(view);

        return viewHolder;
    }

    public void refreshData(@NonNull List<CatUI> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    public void deleteData(CatUI catUI) {
        list.remove(catUI);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(FavoriteCatsViewHolder holder, int position) {
        CatUI item = list.get(position);
        holder.bind(context, item, onDeleteCatListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onDeleteCatListener {
        void onDeleteCat(CatUI model);
    }

}