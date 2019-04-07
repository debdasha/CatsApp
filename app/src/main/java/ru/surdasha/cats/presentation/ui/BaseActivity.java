package ru.surdasha.cats.presentation.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.misc.MVPMoxyActivity;


public class BaseActivity extends MVPMoxyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addFragment(Fragment fragment, boolean addToBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.add( R.id.container, fragment);
        if (addToBack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void showFragment(BaseFragment fragment, boolean addToBack, int containerId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(R.id.container, fragment, tag);
        if (addToBack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }
}
