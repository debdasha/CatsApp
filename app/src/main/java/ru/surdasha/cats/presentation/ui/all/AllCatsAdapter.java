package ru.surdasha.cats.presentation.ui.all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.GlideApp;
import ru.surdasha.cats.presentation.models.CatUI;

public class AllCatsAdapter extends
        RecyclerView.Adapter<AllCatsAdapter.ViewHolder> {

    private static final String TAG = AllCatsAdapter.class.getSimpleName();
    private final static int SCROLL_THRESHOLD = 2;
    private Context context;
    @NonNull
    private List<CatUI> cats = new ArrayList<>();
    @NonNull
    private OnLikeClickListener onLikeClickListener;
    @NonNull
    private OnDownloadClickListener onDownloadClickListener;

    public AllCatsAdapter(Context context) {
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivCat)
        ImageView ivCat;
        @BindView(R.id.ibDownload)
        ImageButton ibDownload;
        @BindView(R.id.ibLike)
        ImageButton ibLike;
        @BindView(R.id.constraintLayout)
        ConstraintLayout constraintLayout;
        private ConstraintSet set = new ConstraintSet();

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Context context,final CatUI model,OnLikeClickListener onLikeClickListener,
                         OnDownloadClickListener onDownloadClickListener ) {
            ibDownload.setOnClickListener(view -> onDownloadClickListener.onDownloadClick(model));
            ibLike.setOnClickListener(view -> onLikeClickListener.onLikeClick(model));
            showWithGlide(context, model);
            setAspectRatio(model);
        }

        private void showWithGlide(Context context, CatUI model) {
            GlideApp.with(context)
                    .load(model.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(getCircularProgressDrawable(context))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                    .into(ivCat);
        }

        private CircularProgressDrawable getCircularProgressDrawable(Context context) {
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
            circularProgressDrawable.setStrokeWidth(3f);
            circularProgressDrawable.setCenterRadius(20f);
            circularProgressDrawable.start();
            return circularProgressDrawable;
        }

        private void setAspectRatio(CatUI model) {
            String ratio =String.format("%d:%d", model.getImageWidth(), model.getImageHeight());
            set.clone(constraintLayout);
            set.setDimensionRatio(ivCat.getId(), ratio);
            set.applyTo(constraintLayout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_cat, parent, false);
        ButterKnife.bind(this, view);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

    public interface OnLikeClickListener {
        void onLikeClick(CatUI catUI);
    }

    public interface OnDownloadClickListener {
        void onDownloadClick(CatUI catUI);
    }

}