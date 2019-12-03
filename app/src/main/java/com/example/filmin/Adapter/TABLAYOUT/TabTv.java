package com.example.filmin.Adapter.TABLAYOUT;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.filmin.Fragment.TvFragment;
import com.example.filmin.Fragment.TvPopular;
import com.example.filmin.R;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabTv extends Fragment {

    private SliderLayout sll;

    public TabTv() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_tv, container, false);

        sll = view.findViewById(R.id.dashboardtv);
        sll.setIndicatorAnimation(IndicatorAnimations.FILL);
        sll.setScrollTimeInSec(1);

        setSliderView();

        TabLayout tabLayout = view.findViewById(R.id.tab_tv);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new TvFragment();
                        break;
                    case 1:
                        fragment = new TvPopular();
                        break;
                }
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fram_tab_tv, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TvFragment tvFragment = new TvFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fram_tab_tv, tvFragment);
        transaction.addToBackStack(null);
        transaction.commit();


        return view;
    }

    private void setSliderView() {
        for (int i = 0; i < 3; i++) {
            DefaultSliderView ds = new DefaultSliderView(getContext());
            switch (i) {
                case 0:
                    ds.setImageUrl("https://images-na.ssl-images-amazon.com/images/I/719scldmNsL._SX425_.jpg");
                    ds.setDescription("ARROW");
                    break;
                case 1:
                    ds.setImageUrl("https://roi-radio.com/wp-content/uploads/2014/03/The-Flash-TV-Poster-Easter-Eggs-570x294.jpg");
                    ds.setDescription("THE FLASH");
                    break;
                case 2:
                    ds.setImageUrl("https://cdn-static.denofgeek.com/sites/denofgeek/files/styles/main_wide/public/3/48/rick_and_morty_header.jpg?itok=iubJDClA");
                    ds.setDescription("HUSTLERS");

                    break;
            }
            ds.setImageScaleType(ImageView.ScaleType.FIT_XY);
            sll.addSliderView(ds);
        }
    }

}
