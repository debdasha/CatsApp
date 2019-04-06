package ru.surdasha.cats.presentation.ui.favorites;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.surdasha.cats.presentation.models.CatUI;

public interface FavoriteCatsView extends MvpView {
    @StateStrategyType(AddToEndStrategy.class)
    void onShowCats(List<CatUI> cats);

    @StateStrategyType(AddToEndStrategy.class)
    void onShowLoading();

    @StateStrategyType(AddToEndStrategy.class)
    void onHideLoad();

    @StateStrategyType(AddToEndStrategy.class)
    void onShowErrorLoading();

    @StateStrategyType(AddToEndStrategy.class)
    void onEmptyList();

    @StateStrategyType(AddToEndStrategy.class)
    void onShowErrorDeleting();

    @StateStrategyType(AddToEndStrategy.class)
    void onShowSuccessDeleting(CatUI catUI);
}
