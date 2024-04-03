package com.debdasha.catsapp.presentation.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.debdasha.catsapp.CatApp;
import com.debdasha.catsapp.R;
import com.debdasha.catsapp.databinding.ActivityMainBinding;
import com.debdasha.catsapp.di.components.UIComponent;
import com.debdasha.catsapp.di.modules.UIModule;
import com.debdasha.catsapp.presentation.ui.BaseActivity;
import com.debdasha.catsapp.presentation.ui.all.AllCatsFragment;
import com.debdasha.catsapp.presentation.ui.favorites.FavoriteCatsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {

    private UIComponent mControllerComponent;
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.navigation_home) {
                navigateAllCats();
                return true;
            } else if (item.getItemId() == R.id.navigation_favorites) {
                navigateFavorites();
                return true;
            } else {
                return false;
            }
        }
    };

    public UIComponent getUIComponent() {
        return mControllerComponent;
    }

    private void navigateAllCats() {
        Fragment fragment = getFragmentByTag(AllCatsFragment.TAG);
        if (fragment == null) {
            AllCatsFragment baseFragment = new AllCatsFragment();
            getUIComponent().inject(baseFragment);
            showFragmentWithTag(baseFragment, AllCatsFragment.TAG, true);
        } else {
            showFragmentWithTag(fragment, AllCatsFragment.TAG, false);
        }
    }

    private void navigateFavorites() {
        Fragment fragment = getFragmentByTag(FavoriteCatsFragment.TAG);
        if (fragment == null) {
            FavoriteCatsFragment baseFragment = new FavoriteCatsFragment();
            getUIComponent().inject(baseFragment);
            showFragmentWithTag(baseFragment, FavoriteCatsFragment.TAG, true);
        } else {
            showFragmentWithTag(fragment, FavoriteCatsFragment.TAG, false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.debdasha.catsapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getUiComponent().inject(this);
        if (savedInstanceState == null) {
            AllCatsFragment baseFragment = new AllCatsFragment();
            getUIComponent().inject(baseFragment);
            showFragmentWithTag(baseFragment, AllCatsFragment.class.getSimpleName(), true);
        }
    }

    private UIComponent getUiComponent() {
        if (mControllerComponent == null) {
            mControllerComponent = CatApp.getAppComponent()
                    .addUIComponent(new UIModule(this));
        }

        return mControllerComponent;
    }

    @Override
    protected void onDestroy() {
        mControllerComponent = null;
        super.onDestroy();
    }

}
