package ru.surdasha.cats.presentation.ui.all;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.GlideApp;
import ru.surdasha.cats.presentation.models.CatUI;

public class AllCatsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ivCat)
    ImageView ivCat;
    @BindView(R.id.ibDownload)
    ImageButton ibDownload;
    @BindView(R.id.ibLike)
    ImageButton ibLike;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    private ConstraintSet set = new ConstraintSet();
    private int width;


    public AllCatsViewHolder(View itemView, ViewPreloadSizeProvider sizeProvider, int witdh) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.width = witdh;
//        sizeProvider.setView(ivCat);
    }

    public void bind(Context context, final CatUI model, AllCatsAdapter.OnLikeClickListener onLikeClickListener,
                     AllCatsAdapter.OnDownloadClickListener onDownloadClickListener ) {
        ibDownload.setOnClickListener(view -> onDownloadClickListener.onDownloadClick(model));
        ibLike.setOnClickListener(view -> onLikeClickListener.onLikeClick(model));
        ivCat.getLayoutParams().height = getScreenHeight(model);
        showWithGlide(context, model);
    }

    private void showWithGlide(Context context, CatUI model) {
        GlideApp.with(context)
                .load(model.getUrl())
                .placeholder(getCircularProgressDrawable(context))
                .override(width, getScreenHeight(model))
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

    private int getScreenHeight(CatUI catUI) {
        int imageHeight = catUI.getImageHeight() * width / catUI.getImageWidth();
        return imageHeight;
    }
}
