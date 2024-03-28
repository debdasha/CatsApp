package com.debdasha.catsapp.presentation.ui.all;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.debdasha.catsapp.R;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import com.debdasha.catsapp.presentation.GlideApp;
import com.debdasha.catsapp.presentation.models.CatUI;

public class AllCatsViewHolder extends RecyclerView.ViewHolder {

    ImageView ivCat = itemView.findViewById(R.id.ivCat);
    AppCompatImageButton ibDownload = itemView.findViewById(R.id.ibDownload);
    AppCompatImageButton ibLike = itemView.findViewById(R.id.ibLike);

    public AllCatsViewHolder(View itemView) {
        super(itemView);

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
                .placeholder(createCircularImageDrawable(context))
                .override(model.getScreenImageWidth(), model.getScreenImageHeight())
                .into(ivCat);
    }

    public CircularProgressDrawable createCircularImageDrawable(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(20f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }
}
