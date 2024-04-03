package com.debdasha.catsapp.presentation.ui.favorites;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.debdasha.catsapp.R;
import com.debdasha.catsapp.presentation.GlideApp;
import com.debdasha.catsapp.presentation.models.CatUI;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class FavoriteCatsViewHolder extends RecyclerView.ViewHolder {

    ImageView ivCat = itemView.findViewById(R.id.ivCat);
    AppCompatImageButton ibDelete = itemView.findViewById(R.id.ibDelete);

    public FavoriteCatsViewHolder(View itemView) {
        super(itemView);

    }

    public void bind(Activity context, final CatUI model, final FavoriteCatsAdapter.onDeleteCatListener listener) {
        RxView.clicks(ibDelete)
                .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(unit -> listener.onDeleteCat(model));
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