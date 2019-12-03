package com.example.filmin.Adapter.TABLAYOUT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.filmin.Fragment.FavoriteMovie;
import com.example.filmin.Fragment.FavoriteTv;
import com.example.filmin.R;
import com.google.android.material.tabs.TabLayout;

public class TabFavorite extends Fragment {


    public TabFavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_favorite, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tab_favorite);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FavoriteMovie();
                        break;
                    case 1:
                        fragment = new FavoriteTv();
                        break;
                }
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fram_tab_favorite, fragment);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FavoriteMovie favoriteMovie = new FavoriteMovie();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fram_tab_favorite, favoriteMovie);
        transaction.addToBackStack(null);
        transaction.commit();


        return view;
    }
}

