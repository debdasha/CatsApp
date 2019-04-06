package ru.surdasha.cats.presentation.ui.all;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.surdasha.cats.domain.models.Cat;
import ru.surdasha.cats.presentation.models.CatUI;

public interface AllCatsView extends MvpView {

    @StateStrategyType(AddToEndStrategy.class)
    void onSuccessLoading(List<CatUI> cats);

    @StateStrategyType(AddToEndStrategy.class)
    void onStartFirstLoading();

    @StateStrategyType(AddToEndStrategy.class)
    void onEndFirstLoading();

    @StateStrategyType(AddToEndStrategy.class)
    void onErrorFirstLoading();

    @StateStrategyType(AddToEndStrategy.class)
    void onEmptyList();

    @StateStrategyType(AddToEndStrategy.class)
    void onStartRefreshing();

    @StateStrategyType(AddToEndStrategy.class)
    void onEndRefreshing();

    @StateStrategyType(AddToEndStrategy.class)
    void onErrorRefreshing();

    @StateStrategyType(AddToEndStrategy.class)
    void onSuccessNextLoading(List<CatUI> cats);

    @StateStrategyType(AddToEndStrategy.class)
    void onStartNextLoading();

    @StateStrategyType(AddToEndStrategy.class)
    void onEndNextLoading();

    @StateStrategyType(AddToEndStrategy.class)
    void onErrorNextLoading();

    @StateStrategyType(AddToEndStrategy.class)
    void onSuccessAddToFavorites();

    @StateStrategyType(AddToEndStrategy.class)
    void onErrorAddToFavorites();

    @StateStrategyType(AddToEndStrategy.class)
    void onStartImageDownload();

    @StateStrategyType(AddToEndStrategy.class)
    void onSuccessImageDownload();

    @StateStrategyType(AddToEndStrategy.class)
    void onErrorImageDownload();
}
