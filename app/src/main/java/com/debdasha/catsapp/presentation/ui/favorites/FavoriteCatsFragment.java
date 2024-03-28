package com.debdasha.catsapp.presentation.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moxy.presenter.InjectPresenter;

import com.debdasha.catsapp.R;
import com.debdasha.catsapp.databinding.FragmentFavoriteCatsBinding;
import com.debdasha.catsapp.presentation.models.CatUI;
import com.debdasha.catsapp.presentation.ui.BaseFragment;
import com.debdasha.catsapp.presentation.ui.main.MainActivity;

public class FavoriteCatsFragment extends BaseFragment implements FavoriteCatsView {
    public static final String TAG = FavoriteCatsFragment.class.getSimpleName();
    FavoriteCatsAdapter favoriteCatsAdapter;
    @InjectPresenter
    FavoriteCatsPresenter favoriteCatsPresenter;
    LinearLayoutManager layoutManager;

    FragmentFavoriteCatsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteCatsBinding.inflate(inflater, container, false);
        bindBaseUI(binding.getRoot());
        setUpAdapter();
        binding.ibRetry.setOnClickListener(view1 -> favoriteCatsPresenter.getCats());
        return binding.getRoot();
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
        binding.rvCats.setLayoutManager(layoutManager);
        binding.rvCats.setAdapter(favoriteCatsAdapter);
    }

    @Override
    public void onShowCats(List<CatUI> cats) {
        binding.groupCats.setVisibility(View.VISIBLE);
        favoriteCatsAdapter.refreshData(cats);
    }

    @Override
    public void onShowLoading() {
        binding.groupError.setVisibility(View.GONE);
        binding.groupCats.setVisibility(View.GONE);
        binding.groupEmpty.setVisibility(View.GONE);
        binding.groupLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoad() {
        binding.groupLoading.setVisibility(View.GONE);
    }

    @Override
    public void onShowErrorLoading() {
        binding.groupError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmptyList() {
        binding.groupEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowErrorDeleting() {
        Toast.makeText(getActivity(), getString(R.string.error_delete_cat), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowSuccessDeleting(CatUI catUI) {
        favoriteCatsAdapter.deleteData(catUI);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        favoriteCatsPresenter.unsubscribe();
    }
}
