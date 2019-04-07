package ru.surdasha.cats.presentation.ui.favorites;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.surdasha.cats.presentation.models.CatUI;

public interface FavoriteCatsView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void onShowCats(List<CatUI> cats);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onShowLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onHideLoad();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onShowErrorLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onEmptyList();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onShowErrorDeleting();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onShowSuccessDeleting(CatUI catUI);
}
