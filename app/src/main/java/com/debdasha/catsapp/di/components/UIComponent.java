package com.debdasha.catsapp.di.components;

import dagger.Subcomponent;
import com.debdasha.catsapp.di.modules.UIModule;
import com.debdasha.catsapp.di.scopes.PerActivity;
import com.debdasha.catsapp.presentation.ui.all.AllCatsPresenter;
import com.debdasha.catsapp.presentation.ui.favorites.FavoriteCatsPresenter;
import com.debdasha.catsapp.presentation.ui.main.MainActivity;

@PerActivity
@Subcomponent(modules = {UIModule.class})
public interface UIComponent {

    void inject(MainActivity activity);
    void inject(AllCatsPresenter allCatsPresenter);
    void inject(FavoriteCatsPresenter favoriteCatsPresenter);
}
