package ru.surdasha.cats.presentation.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.misc.ViewUtils;
import ru.surdasha.cats.presentation.models.CatUI;
import ru.surdasha.cats.presentation.ui.BaseFragment;

public class FavoriteCatsFragment extends BaseFragment implements FavoriteCatsView {
    @BindView(R.id.rvCats)
    RecyclerView rvCats;
    FavoriteCatsAdapter favoriteCatsAdapter;
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
    LinearLayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_cats, container, false);
        bindBaseUI(view);
        favoriteCatsPresenter.getCats();
        setUpAdapter();
        return view;
    }

    private void setUpAdapter() {
        favoriteCatsAdapter = new FavoriteCatsAdapter(getActivity(), model -> favoriteCatsPresenter.deleteFromFavorite(model));
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvCats.setLayoutManager(layoutManager);
        rvCats.setAdapter(favoriteCatsAdapter);
    }

    @Override
    public void onShowCats(List<CatUI> cats) {
        setCatsImagesParams(cats);
        groupCats.setVisibility(View.VISIBLE);
        favoriteCatsAdapter.refreshData(cats);
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
    public void onShowErrorLoading() {
        groupError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmptyList() {
        groupEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowErrorDeleting() {
        Toast.makeText(getActivity(), "Не удалось удалить котика", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowSuccessDeleting(CatUI catUI) {
        favoriteCatsAdapter.deleteData(catUI);
        Toast.makeText(getActivity(), "Удалили котика", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ibRetry)
    public void onRetry() {
        favoriteCatsPresenter.getCats();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        favoriteCatsPresenter.unsubscribe();
    }

    private void setCatsImagesParams(List<CatUI> cats) {
        int screenWidth = ViewUtils.getScreenWidth(getActivity());
        for (CatUI catUI : cats) {
            catUI.setScreenImageWidth(screenWidth);
            catUI.setScreenImageHeight(ViewUtils.countAspectRatioHeight(screenWidth,
                    catUI.getImageHeight(), catUI.getImageWidth()));
        }
    }
}
