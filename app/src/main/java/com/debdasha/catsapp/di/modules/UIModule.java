package com.debdasha.catsapp.di.modules;

import android.app.Activity;
import android.content.Context;

import javax.inject.Named;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import com.debdasha.catsapp.common.Utils;
import com.debdasha.catsapp.di.scopes.PerActivity;
import com.debdasha.catsapp.presentation.mappers.CatUIMapper;
import com.debdasha.catsapp.presentation.misc.ViewUtils;

@Module
public class UIModule {
    private Activity activity;

    public UIModule(android.app.Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    @Named("ActivityContext")
    Context provideContext() {
        return activity;
    }

    @Provides
    @PerActivity
    android.app.Activity provideActivity() {
        return activity;
    }

    @Provides
    @PerActivity
    ViewUtils provideViewUtils(){
        return new ViewUtils(activity);
    }

    @NonNull
    @PerActivity
    @Provides
    CatUIMapper provideCatUIMapper(ViewUtils viewUtils, Utils utils) {
        return new CatUIMapper(viewUtils, utils);
    }

}
