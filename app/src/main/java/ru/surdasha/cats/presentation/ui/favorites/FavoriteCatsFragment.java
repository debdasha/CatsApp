package ru.surdasha.cats.presentation.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.models.CatUI;
import ru.surdasha.cats.presentation.ui.BaseFragment;
import ru.surdasha.cats.presentation.ui.all.AllCatsAdapter;
import ru.surdasha.cats.presentation.ui.all.AllCatsPresenter;

public class FavoriteCatsFragment extends BaseFragment implements FavoriteCatsView {
    @BindView(R.id.rvCats)
    RecyclerView rvCats;
    @InjectPresenter
    AllCatsPresenter allCatsPresenter;
    AllCatsAdapter allCatsAdapter;
    @BindView(R.id.groupCats)
    Group groupCats;
    @BindView(R.id.groupEmpty)
    Group groupEmpty;
    @BindView(R.id.groupLoading)
    Group groupLoading;
    @BindView(R.id.groupError)
    Group groupError;
    @InjectPresenter
    FavoriteCatsPresenter favoriteCatsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_cats, container, false);
        bindBaseUI(view);
        return view;
    }

    @Override
    public void onShowCats(List<CatUI> cats) {
        groupCats.setVisibility(View.VISIBLE);
        allCatsAdapter.refreshData(cats);
    }

    @Override
    public void onShowLoading() {
        groupError.setVisibility(View.GONE);
        groupCats.setVisibility(View.GONE);
        groupEmpty.setVisibility(View.GONE);
        groupLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoad() {
        groupLoading.setVisibility(View.GONE);
    }

    @Override
    public void onShowError() {
        groupError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmptyList() {
        groupEmpty.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ibRetry)
    public void onRetry(){
        allCatsPresenter.getCats();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        allCatsPresenter.unsubscribe();
    }
}
