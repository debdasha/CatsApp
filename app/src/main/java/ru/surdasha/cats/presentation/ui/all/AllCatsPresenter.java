package ru.surdasha.cats.presentation.ui.all;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import ru.surdasha.cats.common.AndroidUtils;
import ru.surdasha.cats.domain.usecases.AddCatUseCase;
import ru.surdasha.cats.domain.usecases.DownloadImageUseCase;
import ru.surdasha.cats.domain.usecases.GetAllCatsUseCase;
import ru.surdasha.cats.domain.usecases.GetNextCatsUseCase;
import ru.surdasha.cats.domain.usecases.RefreshCatsUseCase;
import ru.surdasha.cats.presentation.mappers.CatUIMapper;
import ru.surdasha.cats.presentation.misc.ViewUtils;
import ru.surdasha.cats.presentation.models.CatUI;

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
    CatUIMapper catUIMapper;
    @Inject
    AddCatUseCase addCatUseCase;
    @Inject
    ViewUtils viewUtils;
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
        getViewState().onStartFirstLoading();
        loading = true;
        Disposable disposable = getAllCatsUseCase.getAllCats()
                .flattenAsObservable(cats -> cats)
                .map(cat -> catUIMapper.domainToUI(cat))
                .toList()
                .toMaybe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cats -> {
                    loading = false;
                    getViewState().onEndFirstLoading();
                    if (cats.isEmpty()) {
                        getViewState().onEmptyList();
                    } else {
                        setCatsImagesParams(cats);
                        getViewState().onSuccessLoading(cats);
                    }
                }, throwable -> {
                    loading = false;
                    getViewState().onEndFirstLoading();
                    getViewState().onErrorFirstLoading();
                }, () -> {
                    loading = false;
                    getViewState().onEndFirstLoading();
                    getViewState().onEmptyList();
                });
        compositeDisposable.add(disposable);
    }

    public void refreshCats() {
        unsubscribe();
        getViewState().onStartRefreshing();
        loading = true;
        Disposable disposable = refreshCatsUseCase.getRefreshedCats()
                .flattenAsObservable(cats -> cats)
                .map(cat -> catUIMapper.domainToUI(cat))
                .toList()
                .toMaybe()
                .doOnComplete(() -> {
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cats -> {
                    getViewState().onEndRefreshing();
                    loading = false;
                    if (cats.isEmpty()) {
                        getViewState().onEmptyList();
                    } else {
                        setCatsImagesParams(cats);
                        getViewState().onSuccessLoading(cats);
                    }
                    subscribeToNextCats();
                }, throwable -> {
                    loading = false;
                    subscribeToNextCats();
                    getViewState().onEndRefreshing();
                    getViewState().onErrorRefreshing();
                }, () -> {
                    loading = false;
                    getViewState().onEndRefreshing();
                });
        compositeDisposable.add(disposable);
    }

    public void subscribeToNextCats() {
        Disposable disposable = scrollProcessor
                .onBackpressureDrop()
                .concatMap(page -> {
                    loading = true;
                    getViewState().onStartNextLoading();
                    return getNextCatsUseCase.getNextCats()
                            .subscribeOn(Schedulers.io())
                            .toFlowable();
                })
                .toObservable()
                .map(cat -> catUIMapper.domainToUI(cat))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cats -> {
                    setCatsImagesParams(cats);
                    getViewState().onEndNextLoading();
                    getViewState().onSuccessNextLoading(cats);
                    loading = false;
                }, throwable -> {
                    loading = false;
                    getViewState().onEndNextLoading();
                    getViewState().onErrorNextLoading();
                });
        compositeDisposable.add(disposable);
    }

    public synchronized void onScrolled(int count, int lastVisibleItemIndex) {
        if (!loading && count <= (lastVisibleItemIndex + SCROLL_THRESHOLD)) {
            loading = true;
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
                    tempImageDownloadCat = null;
                    getViewState().onErrorImageDownload();
                });
        compositeDisposable.add(disposable);
    }

    public void setTempImageDownloadCat(CatUI catUI){
        this.tempImageDownloadCat = catUI;
    }

    private void setCatsImagesParams(List<CatUI> cats) {
        int screenWidth = viewUtils.getScreenWidth();
        for (CatUI catUI : cats) {
            catUI.setScreenImageWidth(screenWidth);
            catUI.setScreenImageHeight(viewUtils.countAspectRatioHeight(screenWidth,
                    catUI.getImageHeight(), catUI.getImageWidth()));
        }
    }

    public boolean checkPermissionsRequired(){
        return androidUtils.checkRequiredPermission();
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
