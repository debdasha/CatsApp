package com.debdasha.catsapp.presentation.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.debdasha.catsapp.R;
import com.debdasha.catsapp.databinding.FragmentFavoriteCatsBinding;
import com.debdasha.catsapp.presentation.models.CatUI;
import com.debdasha.catsapp.presentation.ui.BaseFragment;

import java.util.List;

import javax.inject.Inject;

public class FavoriteCatsFragment extends BaseFragment {
    public static final String TAG = FavoriteCatsFragment.class.getSimpleName();
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    FragmentFavoriteCatsBinding binding;
    private FavoriteCatsAdapter favoriteCatsAdapter;
    private FavoriteCatsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        subscribeToDataStates();
        subscribeToData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.loadFavoriteCats();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteCatsBinding.inflate(inflater, container, false);
        setUpAdapter();
        binding.ibRetry.setOnClickListener(view1 -> viewModel.loadFavoriteCats());
        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this, viewModelFactory).get(FavoriteCatsViewModel.class);
    }

    private void subscribeToData() {
        viewModel.getCatsList().observe(this, catUIS -> {
            if (catUIS.isEmpty()) {
                onEmptyList();
            } else {
                onShowCats(catUIS);
            }
        });
        viewModel.getDeleteCat().observe(this, this::onShowSuccessDeleting);
    }

    private void subscribeToDataStates() {
        viewModel.getCatsDeletingState().observe(this, state -> {
            if (state.isError()) {
                onShowErrorDeleting();
            }
        });
        viewModel.getCatsLoadingState().observe(this, state -> {
            if (state.isLoading()) {
                onShowLoading();
            } else {
                onHideLoadingCats();
                if (state.isError()) {
                    onShowErrorLoading();
                }
            }
        });
    }

    private void setUpAdapter() {
        favoriteCatsAdapter = new FavoriteCatsAdapter(getActivity(), model -> viewModel.deleteFavoriteCat(model));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.rvCats.setLayoutManager(layoutManager);
        binding.rvCats.setAdapter(favoriteCatsAdapter);
    }

    public void onShowCats(List<CatUI> cats) {
        binding.groupCats.setVisibility(View.VISIBLE);
        favoriteCatsAdapter.refreshData(cats);
    }

    public void onShowLoading() {
        binding.groupError.setVisibility(View.GONE);
        binding.groupCats.setVisibility(View.GONE);
        binding.groupEmpty.setVisibility(View.GONE);
        binding.groupLoading.setVisibility(View.VISIBLE);
    }

    public void onHideLoadingCats() {
        binding.groupLoading.setVisibility(View.GONE);
    }

    public void onShowErrorLoading() {
        binding.groupError.setVisibility(View.VISIBLE);
    }

    public void onEmptyList() {
        binding.groupEmpty.setVisibility(View.VISIBLE);
    }

    public void onShowErrorDeleting() {
        Toast.makeText(getActivity(), getString(R.string.error_delete_cat), Toast.LENGTH_SHORT).show();
    }

    public void onShowSuccessDeleting(CatUI catUI) {
        favoriteCatsAdapter.deleteData(catUI);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
