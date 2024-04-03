package com.debdasha.catsapp.presentation.ui.all;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.debdasha.catsapp.R;
import com.debdasha.catsapp.databinding.FragmentAllCatsBinding;
import com.debdasha.catsapp.presentation.models.CatUI;
import com.debdasha.catsapp.presentation.ui.BaseFragment;
import com.debdasha.catsapp.presentation.ui.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class AllCatsFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    public static final String TAG = AllCatsFragment.class.getSimpleName();

    private static final String STATE_POSITION_OFFSET = "STATE_POSITION_OFFSET";
    private static final String STATE_POSITION_INDEX = "STATE_POSITION_INDEX";
    private static final int REQUEST_PERMISSION_CODE = 1;
    private static final String WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    AllCatsAdapter allCatsAdapter;
    private final BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            for (CatUI catUI : allCatsAdapter.getItems()) {
                if (catUI.getTempDownloadId() != 0 && catUI.getTempDownloadId() == id) {
                    onSuccessImageDownload();
                }
            }
        }
    };
    LinearLayoutManager layoutManager;
    Bundle savedInstanceState;
    FragmentAllCatsBinding binding;
    private AllCatsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        initViewModel();
        subscribeToDataStates();
        subscribeToData();
        subscribeToDownloadManager();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getUIComponent().inject(this);
        viewModel.loadAllCats();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllCatsBinding.inflate(inflater, container, false);
        setUpAdapter();
        setUpOnScrollListener();
        setUpSwipeRefresh();
        setUpPreload();
        binding.ibRetry.setOnClickListener(view -> viewModel.loadAllCats());
        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this, viewModelFactory).get(AllCatsViewModel.class);
    }

    private void subscribeToData() {
        viewModel.getAllCatsList().observe(this, catUIS -> {
            if (catUIS.isEmpty()) {
                onEmptyList();
            } else {
                onSuccessLoading(catUIS);
            }
        });
        viewModel.getNextCatsList().observe(this, catUIS -> {
            if (!catUIS.isEmpty())
                onSuccessNextLoading(catUIS);
        });
    }

    private void subscribeToDataStates() {
        viewModel.getCatsLoadingAllState().observe(this, state -> {
            if (state.isLoading()) {
                onStartFirstLoading();
            } else {
                onEndFirstLoading();
                if (state.isError()) {
                    onErrorFirstLoading();
                }
            }
        });
        viewModel.getCatAddingState().observe(this, state -> {
            if (state.isError()) {
                onErrorAddToFavorites();
            } else if (state.isSuccess()) {
                onSuccessAddToFavorites();
            }
        });
        viewModel.getCatsRefreshingState().observe(this, state -> {
            if (state.isLoading()) {
                onStartRefreshing();
            } else {
                onEndRefreshing();
                if (state.isError()) {
                    onErrorRefreshing();
                }
            }
        });
        viewModel.getCatsDownloadImageState().observe(this, state -> {
            if (state.isSuccess()) {
                onStartImageDownload();
            } else {
                if (state.isError()) {
                    onErrorImageDownload();
                }
            }
        });
        viewModel.getCatsLoadingNextState().observe(this, state -> {
            if (state.isLoading()) {
                onStartNextLoading();
            } else {
                onEndNextLoading();
                if (state.isError()) {
                    onErrorNextLoading();
                }
            }
        });
    }

    private void setUpSwipeRefresh() {
        binding.swRefresh.setOnRefreshListener(() -> viewModel.refreshCats());
    }

    private void setUpAdapter() {
        allCatsAdapter = new AllCatsAdapter(getActivity());
        allCatsAdapter.setOnDownloadClickListener(catUI -> {
            onDownloadClick(catUI);
        });
        allCatsAdapter.setOnLikeClickListener(catUI -> {
            viewModel.addToFavorite(catUI);
        });
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.rvCats.setLayoutManager(layoutManager);
        binding.rvCats.setAdapter(allCatsAdapter);
    }

    private void setUpPreload() {
        final int maxPreloadCount = 10;
        ListPreloader.PreloadSizeProvider sizeProvider =
                new AllCatsPreloadSizeProvider();
        ListPreloader.PreloadModelProvider modelProvider = new AllCatsPreloadModelProvider(getActivity(), allCatsAdapter.getItems());
        RecyclerViewPreloader<String> preloader =
                new RecyclerViewPreloader<String>(this.getActivity(), modelProvider, sizeProvider, maxPreloadCount);
        binding.rvCats.addOnScrollListener(preloader);
    }

    private void setUpOnScrollListener() {
        binding.rvCats.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                viewModel.onScrolled(
                        layoutManager.getItemCount(),
                        layoutManager.findLastVisibleItemPosition()
                );
            }
        });
    }

    private void subscribeToDownloadManager() {
        ContextCompat.registerReceiver(getActivity(),
                onDownloadComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                ContextCompat.RECEIVER_EXPORTED
        );
    }

    private void onDownloadClick(CatUI catUI) {
        //save cat to variable due to permissions check
        viewModel.setTempImageDownloadCat(catUI);
        if (viewModel.checkPermissionsRequired()) {
            checkForPermission();
        } else {
            viewModel.downloadImage();
        }
    }

    public void onSuccessLoading(List<CatUI> cats) {
        binding.groupCats.setVisibility(View.VISIBLE);
        allCatsAdapter.refreshData(cats);
        if (savedInstanceState != null) {
            int index = savedInstanceState.getInt(STATE_POSITION_INDEX);
            int offset = savedInstanceState.getInt(STATE_POSITION_OFFSET);
            layoutManager.scrollToPositionWithOffset(index, offset);
        }

    }

    public void onStartFirstLoading() {
        binding.groupError.setVisibility(View.GONE);
        binding.groupCats.setVisibility(View.GONE);
        binding.groupEmpty.setVisibility(View.GONE);
        binding.groupLoading.setVisibility(View.VISIBLE);
    }

    public void onEndFirstLoading() {
        binding.groupLoading.setVisibility(View.GONE);
    }

    public void onErrorFirstLoading() {
        binding.groupError.setVisibility(View.VISIBLE);
    }

    public void onEmptyList() {
        binding.groupEmpty.setVisibility(View.VISIBLE);
    }

    public void onErrorAddToFavorites() {
        Toast.makeText(getActivity(), getString(R.string.cant_save_to_favorites), Toast.LENGTH_SHORT).show();
    }

    public void onStartImageDownload() {
        Toast.makeText(getActivity(), getString(R.string.image_load_start), Toast.LENGTH_SHORT).show();
    }

    public void onSuccessImageDownload() {
        Toast.makeText(getActivity(), getString(R.string.image_load_success), Toast.LENGTH_SHORT).show();
    }

    public void onErrorImageDownload() {
        Toast.makeText(getActivity(), getString(R.string.error_load_image), Toast.LENGTH_SHORT).show();
    }

    public void onStartRefreshing() {
        binding.swRefresh.setRefreshing(true);
    }

    public void onEndRefreshing() {
        binding.swRefresh.setRefreshing(false);
    }

    public void onErrorRefreshing() {
        Toast.makeText(getActivity(), getString(R.string.error_refresh_cats), Toast.LENGTH_SHORT).show();
    }

    public void onSuccessNextLoading(List<CatUI> cats) {
        allCatsAdapter.addData(cats);
    }

    public void onStartNextLoading() {
        binding.groupNext.setVisibility(View.VISIBLE);
        binding.swRefresh.setEnabled(false);
    }

    public void onEndNextLoading() {
        binding.groupNext.setVisibility(View.GONE);
        binding.swRefresh.setEnabled(true);
    }

    public void onErrorNextLoading() {
        Toast.makeText(getActivity(), getString(R.string.error_get_next_cats), Toast.LENGTH_SHORT).show();
    }

    public void onSuccessAddToFavorites() {
        Toast.makeText(getActivity(), getString(R.string.success_saving_favorites), Toast.LENGTH_SHORT).show();
    }

    public void checkForPermission() {
        if (isPermissionGranted()) {
            viewModel.downloadImage();
        } else {
            requestRuntimePermission();
        }
    }

    private boolean isPermissionGranted() {
        return EasyPermissions.hasPermissions(this.getActivity(), WRITE_PERMISSION);
    }

    private void requestRuntimePermission() {
        EasyPermissions.requestPermissions(this, getString(R.string.give_permission_load),
                REQUEST_PERMISSION_CODE, WRITE_PERMISSION);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (isPermissionGranted()) {
            viewModel.downloadImage();
        } else {
            requestRuntimePermission();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        int index = layoutManager.findFirstVisibleItemPosition();
        View topView = binding.rvCats.getChildAt(0);
        int offset = topView != null ? topView.getTop() : 0;
        bundle.putInt(STATE_POSITION_INDEX, index);
        bundle.putInt(STATE_POSITION_OFFSET, offset);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(onDownloadComplete);
        binding = null;
    }
}
