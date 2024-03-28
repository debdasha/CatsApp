package com.debdasha.catsapp.presentation.ui.favorites;

import java.util.List;

import com.debdasha.catsapp.presentation.models.CatUI;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

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
