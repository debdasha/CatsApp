package ru.surdasha.cats.presentation.ui;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.arellomobile.mvp.MvpActivity;

import ru.surdasha.cats.R;


public class BaseActivity extends MvpActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addFragment(Fragment fragment, boolean addToBack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.add( R.id.container, fragment);
        if (addToBack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void showFragment(Fragment fragment, boolean addToBack, int containerId) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(containerId, fragment);
        if (addToBack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void showFragment(BaseFragment fragment, boolean addToBack) {
        showFragment(fragment, addToBack, R.id.container);
    }

    public void showFragmentWithTag(Fragment fragment, boolean addToBack, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(R.id.container, fragment, tag);
        if (addToBack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }
}
