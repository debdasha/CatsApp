package com.debdasha.catsapp.di.components;

import com.debdasha.catsapp.di.ViewModelModule;
import com.debdasha.catsapp.di.modules.UIModule;
import com.debdasha.catsapp.di.scopes.PerActivity;
import com.debdasha.catsapp.presentation.ui.all.AllCatsFragment;
import com.debdasha.catsapp.presentation.ui.favorites.FavoriteCatsFragment;
import com.debdasha.catsapp.presentation.ui.main.MainActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {UIModule.class, ViewModelModule.class})
public interface UIComponent {
    void inject(MainActivity activity);

    void inject(FavoriteCatsFragment favoriteCatsFragment);

    void inject(AllCatsFragment allCatsFragment);
}
