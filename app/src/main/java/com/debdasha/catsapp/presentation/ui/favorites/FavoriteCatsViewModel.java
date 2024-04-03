package com.debdasha.catsapp.presentation.ui.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debdasha.catsapp.domain.usecases.DeleteCatUseCase;
import com.debdasha.catsapp.domain.usecases.GetFavoriteCatsUseCase;
import com.debdasha.catsapp.presentation.mappers.CatUIMapper;
import com.debdasha.catsapp.presentation.models.CatUI;
import com.debdasha.catsapp.presentation.models.State;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteCatsViewModel extends ViewModel {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final GetFavoriteCatsUseCase getFavoriteCatsUseCase;
    private final DeleteCatUseCase deleteCatUseCase;
    private final CatUIMapper catUIMapper;
    private final MutableLiveData<List<CatUI>> _catsList = new MutableLiveData<>();
    private final LiveData<List<CatUI>> catsList = _catsList;
    private final MutableLiveData<CatUI> _deletedCat = new MutableLiveData<>();
    private final LiveData<CatUI> deletedCat = _deletedCat;
    private final MutableLiveData<State> _catsDeletingState = new MutableLiveData<>();
    private final LiveData<State> catsDeletingState = _catsDeletingState;
    private final MutableLiveData<State> _catsLoadingState = new MutableLiveData<>();
    private final LiveData<State> catsLoadingState = _catsLoadingState;

    @Inject
    public FavoriteCatsViewModel(CatUIMapper catUIMapper, DeleteCatUseCase deleteCatUseCase, GetFavoriteCatsUseCase getFavoriteCatsUseCase) {
        this.catUIMapper = catUIMapper;
        this.deleteCatUseCase = deleteCatUseCase;
        this.getFavoriteCatsUseCase = getFavoriteCatsUseCase;
    }

    public LiveData<State> getCatsLoadingState() {
        return catsLoadingState;
    }

    public LiveData<State> getCatsDeletingState() {
        return catsDeletingState;
    }

    public LiveData<List<CatUI>> getCatsList() {
        return catsList;
    }

    public LiveData<CatUI> getDeleteCat() {
        return deletedCat;
    }

    public void loadFavoriteCats() {
        _catsLoadingState.setValue(new State().loading());
        Disposable disposable = getFavoriteCatsUseCase.getFavoriteCats()
                .map(catUIMapper::domainToUI)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cats -> {
                    _catsLoadingState.setValue(new State().success());
                    _catsList.setValue(cats);
                }, throwable -> {
                    _catsLoadingState.setValue(new State().error());
                }, () -> {
                    _catsLoadingState.setValue(new State().success());
                });
        compositeDisposable.add(disposable);
    }

    public void deleteFavoriteCat(CatUI catUI) {
        _catsDeletingState.setValue(new State().loading());
        compositeDisposable.add(deleteCatUseCase.deleteCat(catUIMapper.uiToDomain(catUI))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    _catsDeletingState.setValue(new State().success());
                    _deletedCat.setValue(catUI);
                }, throwable -> {
                    _catsDeletingState.setValue(new State().error());
                }));
    }

    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

}
