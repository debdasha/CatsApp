package ru.surdasha.cats.presentation.ui.favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.GlideApp;
import ru.surdasha.cats.presentation.misc.ViewUtils;
import ru.surdasha.cats.presentation.models.CatUI;

public class FavoriteCatsAdapter extends
        RecyclerView.Adapter<FavoriteCatsAdapter.ViewHolder> {

    private static final String TAG = FavoriteCatsAdapter.class.getSimpleName();

    private Context context;
    private List<CatUI> list = new ArrayList<>();
    private onDeleteCatListener onDeleteCatListener;

    public FavoriteCatsAdapter(Context context,
                               onDeleteCatListener onDeleteCatListener) {
        this.context = context;
        this.onDeleteCatListener = onDeleteCatListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivCat)
        ImageView ivCat;
        @BindView(R.id.ibDelete)
        ImageButton ibDelete;
        @BindView(R.id.constraintLayout)
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(Context context,final CatUI model, final onDeleteCatListener listener) {
            ibDelete.setOnClickListener(v -> listener.onDeleteCat(model));
            ivCat.getLayoutParams().height = model.getScreenImageHeight();
            showWithGlide(context, model);
        }

        private void showWithGlide(Context context, CatUI model) {
            GlideApp.with(context)
                    .load(model.getUrl())
                    .placeholder(ViewUtils.createCircularImageDrawable(context))
                    .override(model.getScreenImageWidth(), model.getScreenImageHeight())
                    .into(ivCat);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_favorite_cat, parent, false);
        ButterKnife.bind(this, view);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    public void refreshData(@NonNull List<CatUI> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    public void deleteData(CatUI catUI){
        list.remove(catUI);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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