package com.example.filmin;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.filmin.Adapter.TABLAYOUT.TabFavorite;
import com.example.filmin.Adapter.TABLAYOUT.TabMovie;
import com.example.filmin.Adapter.TABLAYOUT.TabSearch;
import com.example.filmin.Adapter.TABLAYOUT.TabTv;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private String title = "Page Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        TabMovie tabMovie = new TabMovie();
        Toolbar toolbars = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbars);
        TextView tittle = toolbars.findViewById(R.id.tittletoolbar);
        tittle.setText(R.string.app_name);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, tabMovie);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_languange, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.setting) {
            Intent mIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_movie_menu:
                            selectedFragment = new TabMovie();
                            title = getApplicationContext().getString(R.string.movie);
                            setActionBarTitle(title);
                            break;
                        case R.id.navigation_tvshow_menu:
                            selectedFragment = new TabTv();
                            title = getApplicationContext().getString(R.string.tv_show);
                            setActionBarTitle(title);
                            break;
                        case R.id.navigation_favorite_menu:
                            selectedFragment = new TabFavorite();
                            title = getApplicationContext().getString(R.string.favorite);
                            setActionBarTitle(title);
                            break;
                        case R.id.navigation_search_menu:
                            selectedFragment = new TabSearch();
                            title = getApplicationContext().getString(R.string.search);
                            setActionBarTitle(title);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                    return true;
                }
            };

}