package com.debdasha.catsapp.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.debdasha.catsapp.common.AndroidUtils;
import com.debdasha.catsapp.di.scopes.PerActivity;
import com.debdasha.catsapp.domain.usecases.AddCatUseCase;
import com.debdasha.catsapp.domain.usecases.DeleteCatUseCase;
import com.debdasha.catsapp.domain.usecases.DownloadImageUseCase;
import com.debdasha.catsapp.domain.usecases.GetAllCatsUseCase;
import com.debdasha.catsapp.domain.usecases.GetFavoriteCatsUseCase;
import com.debdasha.catsapp.domain.usecases.GetNextCatsUseCase;
import com.debdasha.catsapp.domain.usecases.RefreshCatsUseCase;
import com.debdasha.catsapp.presentation.mappers.CatUIMapper;
import com.debdasha.catsapp.presentation.ui.all.AllCatsViewModel;
import com.debdasha.catsapp.presentation.ui.favorites.FavoriteCatsViewModel;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ViewModelModule {

    @IntoMap
    @Provides
    @PerActivity
    @ViewModelKey(FavoriteCatsViewModel.class)
    ViewModel provideFavoriteCatsViewModel(CatUIMapper catUIMapper, DeleteCatUseCase deleteCatUseCase,
                                           GetFavoriteCatsUseCase getFavoriteCatsUseCase) {
        return new FavoriteCatsViewModel(catUIMapper, deleteCatUseCase, getFavoriteCatsUseCase);
    }

    @IntoMap
    @Provides
    @PerActivity
    @ViewModelKey(AllCatsViewModel.class)
    ViewModel provideAllCatsViewModel(GetAllCatsUseCase getAllCatsUseCase, RefreshCatsUseCase refreshCatsUseCase,
                                      GetNextCatsUseCase getNextCatsUseCase, DownloadImageUseCase downloadImageUseCase,
                                      AddCatUseCase addCatUseCase, CatUIMapper catUIMapper, AndroidUtils androidUtils) {
        return new AllCatsViewModel(getAllCatsUseCase, refreshCatsUseCase, getNextCatsUseCase, downloadImageUseCase,
                addCatUseCase, catUIMapper, androidUtils);
    }

    @Provides
    @PerActivity
    public ViewModelProvider.Factory bindViewModelFactory(Map<Class<? extends ViewModel>, ViewModel> creators) {
        return new ViewModelFactory(creators);
    }
}
