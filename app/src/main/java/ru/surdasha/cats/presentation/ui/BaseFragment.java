package ru.surdasha.cats.presentation.ui;

import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.surdasha.cats.R;
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

    protected boolean haveBackStep() {
        return getFragmentManager().getBackStackEntryCount() > 0;
    }

    protected void backStep() {
        if (getActivity() != null) {
            getActivity().getFragmentManager().popBackStackImmediate();
        }
    }

    public void showFragment(BaseFragment fragment, boolean addToBack) {
        ((BaseActivity) getActivity()).showFragment(fragment, addToBack, R.id.container);
    }

    public void showFragment(BaseFragment fragment, boolean addToBack, int containerId) {
        ((BaseActivity) getActivity()).showFragment(fragment, addToBack, containerId);
    }

    public void showFragmentWithTag(BaseFragment fragment, boolean addToBack, String tag) {
        ((BaseActivity) getActivity()).showFragmentWithTag(fragment, addToBack, tag);
    }
}
