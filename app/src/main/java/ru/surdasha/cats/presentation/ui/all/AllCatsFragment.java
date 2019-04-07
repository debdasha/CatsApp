package ru.surdasha.cats.presentation.ui.all;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.FixedPreloadSizeProvider;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.misc.ViewUtils;
import ru.surdasha.cats.presentation.models.CatUI;
import ru.surdasha.cats.presentation.ui.BaseFragment;

public class AllCatsFragment extends BaseFragment implements AllCatsView, EasyPermissions.PermissionCallbacks {

    private static final String STATE_POSITION_OFFSET = "STATE_POSITION_OFFSET";
    private static final String STATE_POSITION_INDEX = "STATE_POSITION_INDEX";
    private static final int REQUEST_PERMISSION_CODE = 1;
    private static final String WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
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
    @BindView(R.id.swRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvLoading)
    TextView tvLoading;
    @BindView(R.id.ibRetry)
    Button ibRetry;
    @BindView(R.id.groupNext)
    Group groupNext;
    LinearLayoutManager layoutManager;
    Bundle savedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_cats, container, false);
        bindBaseUI(view);
        setUpAdapter();
        setUpOnScrollListener();
        setUpSwipeRefresh();
        setUpPreload();
        allCatsPresenter.getAllCats();
        allCatsPresenter.subscribeToNextCats();
        this.savedInstanceState = savedInstanceState;
        getActivity().registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        return view;
    }

    private void setUpSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> allCatsPresenter.refreshCats());
    }

    private void setUpAdapter() {
        allCatsAdapter = new AllCatsAdapter(getActivity());
        allCatsAdapter.setOnDownloadClickListener(catUI -> {
            allCatsPresenter.setTempImageDownloadCat(catUI);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkForPermission();
            } else {
                allCatsPresenter.downloadImage();
            }
        });
        allCatsAdapter.setOnLikeClickListener(catUI -> {
            allCatsPresenter.addToFavorite(catUI);
        });
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvCats.setLayoutManager(layoutManager);
        rvCats.setAdapter(allCatsAdapter);
    }

    private void setUpPreload() {
        final int imageWidthPixels = 1024;
        final int imageHeightPixels = 768;
        ListPreloader.PreloadSizeProvider sizeProvider =
                new FixedPreloadSizeProvider(imageWidthPixels, imageHeightPixels);
        ListPreloader.PreloadModelProvider modelProvider = new AllCatsPreloadModelProvider(getActivity(), allCatsAdapter.getItems());
        RecyclerViewPreloader<String> preloader =
                new RecyclerViewPreloader<String>(this.getActivity(), modelProvider, sizeProvider, 10);
        rvCats.addOnScrollListener(preloader);
    }

    private void setUpOnScrollListener() {
        rvCats.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                allCatsPresenter.onScrolled(
                        layoutManager.getItemCount(),
                        layoutManager.findLastVisibleItemPosition()
                );
            }
        });
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
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

    @Override
    public void onSuccessLoading(List<CatUI> cats) {
        setCatsImagesParams(cats);
        groupCats.setVisibility(View.VISIBLE);
        allCatsAdapter.refreshData(cats);
        if (savedInstanceState != null) {
            int index = savedInstanceState.getInt(STATE_POSITION_INDEX);
            int offset = savedInstanceState.getInt(STATE_POSITION_OFFSET);
            layoutManager.scrollToPositionWithOffset(index, offset);
        }

    }

    @Override
    public void onStartFirstLoading() {
        groupError.setVisibility(View.GONE);
        groupCats.setVisibility(View.GONE);
        groupEmpty.setVisibility(View.GONE);
        groupLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEndFirstLoading() {
        groupLoading.setVisibility(View.GONE);
    }

    @Override
    public void onErrorFirstLoading() {
        groupError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmptyList() {
        groupEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorAddToFavorites() {
        Toast.makeText(getActivity(), getString(R.string.cant_save_to_favorites), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartImageDownload() {
        Toast.makeText(getActivity(), getString(R.string.image_load_start), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessImageDownload() {
        Toast.makeText(getActivity(), getString(R.string.image_load_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorImageDownload() {
        Toast.makeText(getActivity(), getString(R.string.error_load_image), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onEndRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onErrorRefreshing() {
        Toast.makeText(getActivity(), getString(R.string.error_refresh_cats), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessNextLoading(List<CatUI> cats) {
        setCatsImagesParams(cats);
        allCatsAdapter.addData(cats);
    }

    @Override
    public void onStartNextLoading() {
        groupNext.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onEndNextLoading() {
        groupNext.setVisibility(View.GONE);
        swipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onErrorNextLoading() {
        Toast.makeText(getActivity(), getString(R.string.error_get_next_cats), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessAddToFavorites() {
        Toast.makeText(getActivity(), getString(R.string.success_saving_favorites), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ibRetry)
    public void onRetry() {
        allCatsPresenter.getAllCats();
    }

    private void setCatsImagesParams(List<CatUI> cats) {
        int screenWidth = ViewUtils.getScreenWidth(getActivity());
        for (CatUI catUI : cats) {
            catUI.setScreenImageWidth(screenWidth);
            catUI.setScreenImageHeight(ViewUtils.countAspectRatioHeight(screenWidth,
                    catUI.getImageHeight(), catUI.getImageWidth()));
        }
    }

    public void checkForPermission() {
        if (isPermissionGranted()) {
            allCatsPresenter.downloadImage();
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
            allCatsPresenter.downloadImage();
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
        if (rvCats != null) {
            int index = layoutManager.findFirstVisibleItemPosition();
            View topView = rvCats.getChildAt(0);
            int offset = topView != null ? topView.getTop() : 0;
            bundle.putInt(STATE_POSITION_INDEX, index);
            bundle.putInt(STATE_POSITION_OFFSET, offset);
        }
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
        allCatsPresenter.unsubscribe();
    }
}
