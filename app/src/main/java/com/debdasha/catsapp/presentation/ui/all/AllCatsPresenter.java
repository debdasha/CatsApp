package com.debdasha.catsapp.presentation.ui.all;

import android.os.Build;

import javax.inject.Inject;

import androidx.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

import com.debdasha.catsapp.common.AndroidUtils;
import com.debdasha.catsapp.domain.usecases.AddCatUseCase;
import com.debdasha.catsapp.domain.usecases.DownloadImageUseCase;
import com.debdasha.catsapp.domain.usecases.GetAllCatsUseCase;
import com.debdasha.catsapp.domain.usecases.GetNextCatsUseCase;
import com.debdasha.catsapp.domain.usecases.RefreshCatsUseCase;
import com.debdasha.catsapp.presentation.mappers.CatUIMapper;
import com.debdasha.catsapp.presentation.models.CatUI;

@InjectViewState
public class AllCatsPresenter extends MvpPresenter<AllCatsView> {
    @NonNull
    private final CompositeDisposable compositeDisposable;
    @Inject
    GetAllCatsUseCase getAllCatsUseCase;
    @Inject
    RefreshCatsUseCase refreshCatsUseCase;
    @Inject
    GetNextCatsUseCase getNextCatsUseCase;
    @Inject
    DownloadImageUseCase downloadImageUseCase;
    @Inject
    AddCatUseCase addCatUseCase;
    @Inject
    CatUIMapper catUIMapper;
    @Inject
    AndroidUtils androidUtils;
    private PublishProcessor<Integer> scrollProcessor = PublishProcessor.create();
    private final static int SCROLL_THRESHOLD = 2;
    private volatile boolean loading;
    private CatUI tempImageDownloadCat;

    public AllCatsPresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    public void getAllCats() {
        Disposable disposable = getAllCatsUseCase.getAllCats()
                .map(cats -> catUIMapper.domainToUI(cats))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> {
                    getViewState().onStartFirstLoading();
                    setCurrentLoadingState(true);
                })
                .doOnTerminate(() -> {
                    setCurrentLoadingState(false);
                    getViewState().onEndFirstLoading();
                })
                .subscribe(cats -> {
                    if (cats.isEmpty()) {
                        getViewState().onEmptyList();
                    } else {
                        getViewState().onSuccessLoading(cats);
                    }
                }, throwable -> {
                    getViewState().onErrorFirstLoading();
                }, () -> {
                    getViewState().onEmptyList();
                });
        compositeDisposable.add(disposable);
    }

    public void refreshCats() {
        unsubscribe();
        Disposable disposable = refreshCatsUseCase.getRefreshedCats()
                .map(cats -> catUIMapper.domainToUI(cats))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> {
                    setCurrentLoadingState(true);
                    getViewState().onStartRefreshing();
                })
                .doOnTerminate(() -> {
                    setCurrentLoadingState(false);
                    getViewState().onEndRefreshing();
                })
                .subscribe(cats -> {
                    if (cats.isEmpty()) {
                        getViewState().onEmptyList();
                    } else {
                        getViewState().onSuccessLoading(cats);
                    }
                    subscribeToNextCats();
                }, throwable -> {
                    subscribeToNextCats();
                    getViewState().onErrorRefreshing();
                });
        compositeDisposable.add(disposable);
    }

    public void subscribeToNextCats() {
        Disposable disposable = scrollProcessor
                .onBackpressureDrop()
                .concatMap(page -> {
                    setCurrentLoadingState(true);
                    getViewState().onStartNextLoading();
                    return getNextCatsUseCase.getNextCats()
                            .subscribeOn(Schedulers.io())
                            .toFlowable()
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnTerminate(() -> {
                                getViewState().onEndNextLoading();
                                setCurrentLoadingState(false);
                            });
                })
                .toObservable()
                .map(cat -> catUIMapper.domainToUI(cat))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cats -> {
                    getViewState().onSuccessNextLoading(cats);
                }, throwable -> {
                    getViewState().onErrorNextLoading();
                });
        compositeDisposable.add(disposable);
    }

    public synchronized void onScrolled(int count, int lastVisibleItemIndex) {
        if (!loading && count <= (lastVisibleItemIndex + SCROLL_THRESHOLD)) {
            setCurrentLoadingState(true);
            scrollProcessor.onNext(count);
        }
    }

    public void addToFavorite(CatUI catUI) {
        Disposable disposable = addCatUseCase.addCat(catUIMapper.uiToDomain(catUI))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    getViewState().onSuccessAddToFavorites();
                }, throwable -> {
                    getViewState().onErrorAddToFavorites();
                });
        compositeDisposable.add(disposable);
    }

    public void downloadImage() {
        Disposable disposable = downloadImageUseCase.downloadImage(catUIMapper.uiToDomain(tempImageDownloadCat))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(downloadId -> {
                    tempImageDownloadCat.setTempDownloadId(downloadId);
                    getViewState().onStartImageDownload();
                }, throwable -> {
                    getViewState().onErrorImageDownload();
                });
        compositeDisposable.add(disposable);
    }

    public void setTempImageDownloadCat(CatUI catUI) {
        this.tempImageDownloadCat = catUI;
    }

    public boolean checkPermissionsRequired() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            return androidUtils.checkRequiredPermission();
        } else {
            // для версий старше пермишен для записи не существует и если его пытаться проверить, возвращается false
            return false;
        }
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void setCurrentLoadingState(boolean b) {
        loading = b;
    }
}
