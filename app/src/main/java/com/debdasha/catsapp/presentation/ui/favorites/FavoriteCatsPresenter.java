package com.debdasha.catsapp.presentation.ui.favorites;

import javax.inject.Inject;

import androidx.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

import com.debdasha.catsapp.domain.usecases.DeleteCatUseCase;
import com.debdasha.catsapp.domain.usecases.GetFavoriteCatsUseCase;
import com.debdasha.catsapp.presentation.mappers.CatUIMapper;
import com.debdasha.catsapp.presentation.models.CatUI;

@InjectViewState
public class FavoriteCatsPresenter extends MvpPresenter<FavoriteCatsView> {
    @NonNull
    private final CompositeDisposable compositeDisposable;
    @Inject
    GetFavoriteCatsUseCase getFavoriteCatsUseCase;
    @Inject
    DeleteCatUseCase deleteCatUseCase;
    @Inject
    CatUIMapper catUIMapper;

    public FavoriteCatsPresenter() {
        compositeDisposable = new CompositeDisposable();

    }

    public void getCats() {
        getViewState().onShowLoading();
        Disposable disposable = getFavoriteCatsUseCase.getFavoriteCats()
                .map(cats -> catUIMapper.domainToUI(cats))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cats -> {
                    getViewState().onHideLoad();
                    if (cats.isEmpty()) {
                        getViewState().onEmptyList();
                    } else {
                        getViewState().onShowCats(cats);
                    }
                }, throwable -> {
                    getViewState().onHideLoad();
                    getViewState().onShowErrorLoading();
                }, () -> {
                    getViewState().onHideLoad();
                    getViewState().onEmptyList();
                });
        compositeDisposable.add(disposable);
    }

    public void deleteFromFavorite(CatUI catUI) {
        compositeDisposable.add(deleteCatUseCase.deleteCat(catUIMapper.uiToDomain(catUI))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    getViewState().onShowSuccessDeleting(catUI);
                }, throwable -> {
                    getViewState().onShowErrorDeleting();
                }));
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
