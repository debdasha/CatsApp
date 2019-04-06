package ru.surdasha.cats.presentation.ui.favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.models.CatUI;

public class FavoriteCatsAdapter extends
        RecyclerView.Adapter<FavoriteCatsAdapter.ViewHolder> {

    private static final String TAG = FavoriteCatsAdapter.class.getSimpleName();

    private Context context;
    private List<CatUI> list;
    private OnItemClickListener onItemClickListener;

    public FavoriteCatsAdapter(Context context, List<CatUI> list,
                               OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final CatUI model,
                         final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());

                }
            });
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


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CatUI item = list.get(position);

        //Todo: Setup viewholder for item 
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}