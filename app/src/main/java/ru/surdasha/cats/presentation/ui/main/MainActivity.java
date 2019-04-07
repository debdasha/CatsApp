package ru.surdasha.cats.presentation.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.ui.BaseActivity;
import ru.surdasha.cats.presentation.ui.all.AllCatsFragment;
import ru.surdasha.cats.presentation.ui.favorites.FavoriteCatsFragment;

public class MainActivity extends BaseActivity {
    @BindView(R.id.navigation)
    protected BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(new AllCatsFragment(), false);
                    return true;
                case R.id.navigation_favorites:
                    showFragment(new FavoriteCatsFragment(), false);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            showFragment(new AllCatsFragment(), false);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
