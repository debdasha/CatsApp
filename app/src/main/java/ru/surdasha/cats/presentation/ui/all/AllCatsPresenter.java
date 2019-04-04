package ru.surdasha.cats.presentation.ui.all;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import ru.surdasha.cats.CatApp;
import ru.surdasha.cats.domain.usecases.AddCatUseCase;
import ru.surdasha.cats.domain.usecases.GetCatsUseCase;
import ru.surdasha.cats.presentation.mappers.CatUIMapper;
import ru.surdasha.cats.presentation.models.CatUI;

@InjectViewState
public class AllCatsPresenter extends MvpPresenter<AllCatsView> {
    @NonNull
    private final CompositeDisposable compositeDisposable;
    @Inject
    GetCatsUseCase getCatsUseCase;
    @Inject
    CatUIMapper catUIMapper;
    @Inject
    AddCatUseCase addCatUseCase;
    private PublishProcessor<Integer> scrollProcessor = PublishProcessor.create();

    private final int DEFAULT_PAGE = 1;
    private int pageNumber = DEFAULT_PAGE;
    private final static int SCROLL_THRESHOLD = 2;

    private volatile boolean loading;

    public AllCatsPresenter() {
        CatApp.getAppComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
    }

    public void getFirstCats() {
        getViewState().onStartFirstLoading();
        loading = true;
        Disposable disposable = getCatsUseCase.getCats(DEFAULT_PAGE)
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
        Disposable disposable = getCatsUseCase.getCats(DEFAULT_PAGE)
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
                    pageNumber = 1;
                    loading = false;
                    getViewState().onSuccessLoading(cats);
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
                    return getCatsUseCase.getCats(page)
                            .subscribeOn(Schedulers.io())
                            .toFlowable();
                })
                .toObservable()
                .map(cat -> catUIMapper.domainToUI(cat))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cats -> {
                    getViewState().onEndNextLoading();
                    getViewState().onSuccessNextLoading(cats);
                    loading = false;
                }, throwable -> {
                    getViewState().onEndNextLoading();
                    getViewState().onErrorNextLoading();
                });
        compositeDisposable.add(disposable);
    }

    public synchronized void onScrolled(int count, int lastVisibleItemIndex) {
        if (!loading && count <= (lastVisibleItemIndex + SCROLL_THRESHOLD)) {
            pageNumber++;
            loading = true;
            scrollProcessor.onNext(pageNumber);
        }
    }

    public void addToFavorite(CatUI catUI) {
        addCatUseCase.addCat(catUIMapper.uiToDomain(catUI))
                .subscribe(() -> {

                }, throwable -> {
                    getViewState().onErrorAddToFavorites();
                });
    }

    public void downloadImage(CatUI catUI) {

    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
