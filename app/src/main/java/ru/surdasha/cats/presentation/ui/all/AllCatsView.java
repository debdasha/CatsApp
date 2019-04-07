package ru.surdasha.cats.presentation.ui.all;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.surdasha.cats.presentation.models.CatUI;

public interface AllCatsView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onSuccessLoading(List<CatUI> cats);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onStartFirstLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onEndFirstLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onErrorFirstLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onEmptyList();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onStartRefreshing();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onEndRefreshing();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onErrorRefreshing();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onSuccessNextLoading(List<CatUI> cats);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onStartNextLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onEndNextLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onErrorNextLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onSuccessAddToFavorites();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onErrorAddToFavorites();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onStartImageDownload();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onSuccessImageDownload();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onErrorImageDownload();
}
