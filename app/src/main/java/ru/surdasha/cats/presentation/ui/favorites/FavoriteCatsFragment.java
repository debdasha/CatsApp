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
import ru.surdasha.cats.presentation.models.CatUI;
import ru.surdasha.cats.presentation.ui.BaseFragment;
import ru.surdasha.cats.presentation.ui.main.MainActivity;

public class FavoriteCatsFragment extends BaseFragment implements FavoriteCatsView {
    public static final String TAG = FavoriteCatsFragment.class.getSimpleName();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_cats, container, false);
        bindBaseUI(view);
        setUpAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).getUIComponent().inject(favoriteCatsPresenter);
        favoriteCatsPresenter.getCats();
    }

    private void setUpAdapter() {
        favoriteCatsAdapter = new FavoriteCatsAdapter(getActivity(), model -> favoriteCatsPresenter.deleteFromFavorite(model));
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvCats.setLayoutManager(layoutManager);
        rvCats.setAdapter(favoriteCatsAdapter);
    }

    @Override
    public void onShowCats(List<CatUI> cats) {
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
        Toast.makeText(getActivity(), getString(R.string.error_delete_cat), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowSuccessDeleting(CatUI catUI) {
        favoriteCatsAdapter.deleteData(catUI);
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
}
