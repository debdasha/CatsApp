package com.debdasha.catsapp.presentation.ui.all;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debdasha.catsapp.common.AndroidUtils;
import com.debdasha.catsapp.domain.usecases.AddCatUseCase;
import com.debdasha.catsapp.domain.usecases.DownloadImageUseCase;
import com.debdasha.catsapp.domain.usecases.GetAllCatsUseCase;
import com.debdasha.catsapp.domain.usecases.GetNextCatsUseCase;
import com.debdasha.catsapp.domain.usecases.RefreshCatsUseCase;
import com.debdasha.catsapp.presentation.mappers.CatUIMapper;
import com.debdasha.catsapp.presentation.models.CatUI;
import com.debdasha.catsapp.presentation.models.State;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class AllCatsViewModel extends ViewModel {
    private final static int SCROLL_THRESHOLD = 2;
    private final CompositeDisposable actionsDisposable = new CompositeDisposable();
    private final CompositeDisposable gettingCatsDisposable = new CompositeDisposable();
    private final GetAllCatsUseCase getAllCatsUseCase;
    private final RefreshCatsUseCase refreshCatsUseCase;
    private final GetNextCatsUseCase getNextCatsUseCase;
    private final DownloadImageUseCase downloadImageUseCase;
    private final AddCatUseCase addCatUseCase;
    private final CatUIMapper catUIMapper;
    private final AndroidUtils androidUtils;
    private final MutableLiveData<List<CatUI>> _allCatsList = new MutableLiveData<>();
    private final LiveData<List<CatUI>> allCatsList = _allCatsList;
    private final MutableLiveData<List<CatUI>> _nextCatsList = new MutableLiveData<>();
    private final LiveData<List<CatUI>> nextCatsList = _nextCatsList;
    private final MutableLiveData<State> _catAddingState = new MutableLiveData<>();
    private final LiveData<State> catAddingState = _catAddingState;
    private final MutableLiveData<State> _catsLoadingAllState = new MutableLiveData<>();
    private final LiveData<State> catsLoadingAllState = _catsLoadingAllState;
    private final MutableLiveData<State> _catsLoadingNextState = new MutableLiveData<>();
    private final LiveData<State> catsLoadingNextState = _catsLoadingNextState;
    private final MutableLiveData<State> _catsRefreshingState = new MutableLiveData<>();
    private final LiveData<State> catsRefreshingState = _catsRefreshingState;
    private final MutableLiveData<State> _catsDownloadImageState = new MutableLiveData<>();
    private final LiveData<State> catsDownloadImageState = _catsDownloadImageState;
    private final PublishProcessor<Integer> scrollProcessor = PublishProcessor.create();
    private CatUI tempImageDownloadCat;

    @Inject
    public AllCatsViewModel(GetAllCatsUseCase getAllCatsUseCase, RefreshCatsUseCase refreshCatsUseCase,
                            GetNextCatsUseCase getNextCatsUseCase, DownloadImageUseCase downloadImageUseCase,
                            AddCatUseCase addCatUseCase, CatUIMapper catUIMapper, AndroidUtils androidUtils) {
        this.getAllCatsUseCase = getAllCatsUseCase;
        this.refreshCatsUseCase = refreshCatsUseCase;
        this.getNextCatsUseCase = getNextCatsUseCase;
        this.downloadImageUseCase = downloadImageUseCase;
        this.addCatUseCase = addCatUseCase;
        this.catUIMapper = catUIMapper;
        this.androidUtils = androidUtils;
    }

    public LiveData<List<CatUI>> getAllCatsList() {
        return allCatsList;
    }

    public LiveData<State> getCatAddingState() {
        return catAddingState;
    }

    public LiveData<State> getCatsLoadingAllState() {
        return catsLoadingAllState;
    }

    public LiveData<State> getCatsLoadingNextState() {
        return catsLoadingNextState;
    }

    public LiveData<State> getCatsRefreshingState() {
        return catsRefreshingState;
    }

    public LiveData<State> getCatsDownloadImageState() {
        return catsDownloadImageState;
    }

    public void loadAllCats() {
        unsubscribe();
        Disposable disposable = getAllCatsUseCase.getAllCats()
                .map(catUIMapper::domainToUI)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> {
                    _catsDownloadImageState.setValue(new State().loading());
                })
                .subscribe(cats -> {
                    _catsLoadingAllState.setValue(new State().success());
                    _allCatsList.setValue(cats);
                    subscribeToNextCats();
                }, throwable -> {
                    _catsLoadingAllState.setValue(new State().error());
                    subscribeToNextCats();
                }, () -> {
                    _catsLoadingAllState.setValue(new State().success());
                    subscribeToNextCats();
                });
        gettingCatsDisposable.add(disposable);
    }

    public void refreshCats() {
        unsubscribe();
        Disposable disposable = refreshCatsUseCase.getRefreshedCats()
                .map(catUIMapper::domainToUI)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> {
                    _catsRefreshingState.setValue(new State().loading());
                })
                .subscribe(cats -> {
                    _allCatsList.setValue(cats);
                    _catsRefreshingState.setValue(new State().success());
                    subscribeToNextCats();
                }, throwable -> {
                    _catsRefreshingState.setValue(new State().error());
                    subscribeToNextCats();
                });
        gettingCatsDisposable.add(disposable);
    }

    public void subscribeToNextCats() {
        unsubscribe();
        Disposable disposable = scrollProcessor
                .onBackpressureDrop()
                .concatMap(page -> {
                    _catsLoadingNextState.setValue(new State().loading());
                    return getNextCatsUseCase.getNextCats()
                            .subscribeOn(Schedulers.io())
                            .toFlowable()
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnTerminate(() -> {

                            });
                })
                .toObservable()
                .map(catUIMapper::domainToUI)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cats -> {
                    _allCatsList.setValue(cats);
                    _catsLoadingNextState.setValue(new State().success());
                    subscribeToNextCats();
                }, throwable -> {
                    _catsLoadingNextState.setValue(new State().error());
                    subscribeToNextCats();
                });
        gettingCatsDisposable.add(disposable);
    }

    public void addToFavorite(CatUI catUI) {
        _catAddingState.setValue(new State().loading());
        Disposable disposable = addCatUseCase.addCat(catUIMapper.uiToDomain(catUI))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    _catAddingState.setValue(new State().success());
                }, throwable -> {
                    _catAddingState.setValue(new State().error());
                });
        actionsDisposable.add(disposable);
    }

    public void downloadImage() {
        _catsDownloadImageState.setValue(new State().loading());
        Disposable disposable = downloadImageUseCase.downloadImage(catUIMapper.uiToDomain(tempImageDownloadCat))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(downloadId -> {
                    tempImageDownloadCat.setTempDownloadId(downloadId);
                    _catsDownloadImageState.setValue(new State().success());
                }, throwable -> {
                    _catsDownloadImageState.setValue(new State().error());
                });
        actionsDisposable.add(disposable);
    }

    public void onScrolled(int count, int lastVisibleItemIndex) {
        if (count <= (lastVisibleItemIndex + SCROLL_THRESHOLD)) {
            scrollProcessor.onNext(count);
        }
    }

    public void setTempImageDownloadCat(CatUI catUI) {
        this.tempImageDownloadCat = catUI;
    }

    public boolean checkPermissionsRequired() {
        return androidUtils.checkStoragePermissionRequired();
    }

    public void unsubscribe() {
        gettingCatsDisposable.clear();
    }

    protected void onCleared() {
        super.onCleared();
        actionsDisposable.clear();
    }

    public LiveData<List<CatUI>> getNextCatsList() {
        return nextCatsList;
    }
}
