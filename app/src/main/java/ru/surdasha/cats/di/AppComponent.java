package ru.surdasha.cats.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.surdasha.cats.presentation.ui.all.AllCatsPresenter;
import ru.surdasha.cats.presentation.ui.favorites.FavoriteCatsPresenter;

@Singleton
@Component(modules = {ContextModule.class,
        DataModule.class, DomainModule.class,
        UIModule.class})
public interface AppComponent {
    void inject(AllCatsPresenter allCatsPresenter);
    void inject(FavoriteCatsPresenter favoriteCatsPresenter);
}
