package ru.surdasha.cats.di.components;

import dagger.Subcomponent;
import ru.surdasha.cats.di.modules.UIModule;
import ru.surdasha.cats.di.scopes.PerActivity;
import ru.surdasha.cats.presentation.ui.all.AllCatsPresenter;
import ru.surdasha.cats.presentation.ui.favorites.FavoriteCatsPresenter;
import ru.surdasha.cats.presentation.ui.main.MainActivity;

@PerActivity
@Subcomponent(modules = {UIModule.class})
public interface UIComponent {

    void inject(MainActivity activity);
    void inject(AllCatsPresenter allCatsPresenter);
    void inject(FavoriteCatsPresenter favoriteCatsPresenter);
}
