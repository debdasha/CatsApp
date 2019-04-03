package ru.surdasha.cats.presentation.ui.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.arellomobile.mvp.MvpActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.surdasha.cats.R;
import ru.surdasha.cats.presentation.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.message)
    protected TextView mTextMessage;
    @BindView(R.id.navigation)
    protected BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_favorites:
                    mTextMessage.setText(R.string.title_dashboard);
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
    }

}
