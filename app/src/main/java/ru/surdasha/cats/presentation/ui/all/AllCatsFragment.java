package ru.surdasha.cats.presentation.ui.all;

import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.List;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.models.CatUI;
import ru.surdasha.cats.presentation.ui.BaseFragment;

public class AllCatsFragment extends BaseFragment implements AllCatsView {

    private static final String STATE_POSITION_OFFSET = "STATE_POSITION_OFFSET";
    private static final String STATE_POSITION_INDEX = "STATE_POSITION_INDEX";
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
    public void onCreate( Bundle savedInstanceState){
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
        return view;
    }

    private void setUpSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> allCatsPresenter.refreshCats());
    }

    private void setUpAdapter() {
        allCatsAdapter = new AllCatsAdapter(getActivity(), getScreenWidth());
        allCatsAdapter.setOnDownloadClickListener(catUI -> {
            allCatsPresenter.downloadImage(catUI);
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
        ListPreloader.PreloadModelProvider modelProvider = new AllCatsPreloadModelProvider(getActivity(), allCatsAdapter.getItems(), getScreenWidth());
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

    private int getScreenWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }

    @Override
    public void onSuccessLoading(List<CatUI> cats) {
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
        Toast.makeText(getActivity(), "Не удалось сохранить кота в избранное", Toast.LENGTH_SHORT);
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
        Toast.makeText(getActivity(), "Не удалоcь обновить котов", Toast.LENGTH_SHORT);
    }

    @Override
    public void onSuccessNextLoading(List<CatUI> cats) {
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
        Toast.makeText(getActivity(), "Не удалоcь обновить котов", Toast.LENGTH_SHORT);
    }

    @Override
    public void onSuccessAddToFavorites() {
        Toast.makeText(getActivity(), "Картинка успешно сохранена в избранное", Toast.LENGTH_SHORT);
    }

    @OnClick(R.id.ibRetry)
    public void onRetry() {
        allCatsPresenter.getAllCats();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
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
    public void onDestroyView() {
        super.onDestroyView();
        allCatsPresenter.unsubscribe();
    }
}
