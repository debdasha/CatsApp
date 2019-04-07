package ru.surdasha.cats.presentation.ui;

import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.surdasha.cats.presentation.misc.MVPMoxyFragment;


public class BaseFragment extends MVPMoxyFragment {
    private Unbinder unbinder;

    public void bindBaseUI(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
