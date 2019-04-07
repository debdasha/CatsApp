package ru.surdasha.cats.presentation.ui.all;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import ru.surdasha.cats.presentation.GlideApp;
import ru.surdasha.cats.presentation.models.CatUI;

public class AllCatsPreloadModelProvider implements ListPreloader.PreloadModelProvider<CatUI> {
    private List<CatUI> catUIS;
    private Activity context;

    public AllCatsPreloadModelProvider(Activity context, List<CatUI> catUIS){
        this.catUIS = catUIS;
        this.context = context;
    }

    @Override
    @NonNull
    public List<CatUI> getPreloadItems(int position) {
        String url = catUIS.get(position).getUrl();
        if (TextUtils.isEmpty(url)) {
            return Collections.emptyList();
        }
        return Collections.singletonList(catUIS.get(position));
    }

    @Override
    @Nullable
    public RequestBuilder<?> getPreloadRequestBuilder(CatUI model) {
        return
                GlideApp.with(context)
                        .load(model.getUrl())
                        .placeholder(createCircularImageDrawable(context))
                        .override(model.getScreenImageWidth(), model.getScreenImageHeight());
    }


    public CircularProgressDrawable createCircularImageDrawable(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(20f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }
}
