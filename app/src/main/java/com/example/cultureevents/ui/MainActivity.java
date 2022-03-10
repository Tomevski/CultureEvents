package com.example.cultureevents.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.cultureevents.ui.news.NewsFeedFragment;
import com.example.cultureevents.R;
import com.example.cultureevents.ui.profile.UserFragment;
import com.example.cultureevents.ui.calendar.CalendarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * LAUNCHER Activity.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationView();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsFeedFragment()).commit();
    }

    private void setNavigationView() {
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    int id = menuItem.getItemId();
                    if (id == R.id.home) {
                        selectedFragment = new NewsFeedFragment();
                    }
                    if (id == R.id.calendar) {
                        selectedFragment = new CalendarFragment();
                    }
                    if (id == R.id.profile) {
                        selectedFragment = new UserFragment();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }

            };
}