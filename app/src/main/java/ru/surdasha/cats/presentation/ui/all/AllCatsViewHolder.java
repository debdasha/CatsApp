package ru.surdasha.cats.presentation.ui.all;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.GlideApp;
import ru.surdasha.cats.presentation.misc.ViewUtils;
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


    public AllCatsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Activity context, final CatUI model, AllCatsAdapter.OnLikeClickListener onLikeClickListener,
                     AllCatsAdapter.OnDownloadClickListener onDownloadClickListener ) {
        RxView.clicks(ibDownload)
                .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(unit -> onDownloadClickListener.onDownloadClick(model));
        RxView.clicks(ibLike)
                .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(unit -> onLikeClickListener.onLikeClick(model));
        ivCat.getLayoutParams().height = model.getScreenImageHeight();
        showWithGlide(context, model);
    }

    private void showWithGlide(Activity context, CatUI model) {
        GlideApp.with(context)
                .load(model.getUrl())
                .placeholder(ViewUtils.createCircularImageDrawable(context))
                .override(model.getScreenImageWidth(), model.getScreenImageHeight())
                .into(ivCat);
    }
}
