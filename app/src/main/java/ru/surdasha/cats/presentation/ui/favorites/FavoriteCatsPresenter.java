package ru.surdasha.cats.presentation.ui.favorites;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.surdasha.cats.domain.usecases.DeleteCatUseCase;
import ru.surdasha.cats.domain.usecases.GetFavoriteCatsUseCase;
import ru.surdasha.cats.presentation.mappers.CatUIMapper;
import ru.surdasha.cats.presentation.misc.ViewUtils;
import ru.surdasha.cats.presentation.models.CatUI;

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
    @Inject
    ViewUtils viewUtils;

    public FavoriteCatsPresenter() {
        compositeDisposable = new CompositeDisposable();

    }

    public void getCats(){
        getViewState().onShowLoading();
        Disposable disposable = getFavoriteCatsUseCase.getFavoriteCats()
                .flattenAsObservable(cats -> cats)
                .map(cat -> catUIMapper.domainToUI(cat))
                .toList()
                .toMaybe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cats -> {
                    getViewState().onHideLoad();
                    if (cats.isEmpty()){
                        getViewState().onEmptyList();
                    }else{
                        setCatsImagesParams(cats);
                        getViewState().onShowCats(cats);
                    }
                },throwable -> {
                    getViewState().onHideLoad();
                    getViewState().onShowErrorLoading();
                }, () -> {
                    getViewState().onHideLoad();
                    getViewState().onEmptyList();
                });
        compositeDisposable.add(disposable);
    }

    public void deleteFromFavorite(CatUI catUI){
        deleteCatUseCase.deleteCat(catUIMapper.uiToDomain(catUI))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    getViewState().onShowSuccessDeleting(catUI);
                },throwable -> {
                    getViewState().onShowErrorDeleting();
                });
    }

    private void setCatsImagesParams(List<CatUI> cats) {
        int screenWidth = viewUtils.getScreenWidth();
        for (CatUI catUI : cats) {
            catUI.setScreenImageWidth(screenWidth);
            catUI.setScreenImageHeight(viewUtils.countAspectRatioHeight(screenWidth,
                    catUI.getImageHeight(), catUI.getImageWidth()));
        }
    }

    public void unsubscribe(){
        compositeDisposable.clear();
    }
}
