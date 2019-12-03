package com.example.filmin.Adapter.TABLAYOUT;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.filmin.Fragment.MovieFragment;
import com.example.filmin.Fragment.MoviePopular;
import com.example.filmin.R;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabMovie extends Fragment {

    SliderLayout sl;

    public TabMovie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_movie, container, false);

        sl = view.findViewById(R.id.dashboard);
        sl.setIndicatorAnimation(IndicatorAnimations.FILL);
        sl.setScrollTimeInSec(1);
        setSliderView();
        TabLayout tabLayout = view.findViewById(R.id.tab_movie);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new MovieFragment();
                        break;
                    case 1:
                        fragment = new MoviePopular();
                        break;
                }
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fram_tab_movie, fragment);
                ft.setCustomAnimations(R.anim.enter_left_to_right, R.anim.nav_default_exit_anim);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        MovieFragment movieFragment = new MovieFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fram_tab_movie, movieFragment);
        transaction.addToBackStack(null);
        transaction.commit();


        return view;
    }

    private void setSliderView() {
        for (int i = 0; i < 3; i++) {
            DefaultSliderView ds = new DefaultSliderView(getContext());
            switch (i) {
                case 0:
                    ds.setImageUrl("http://movieden.net/wp-content/uploads/2019/08/disney-frozen-2.jpeg");
                    ds.setDescription("FROZEN 2");
                    break;
                case 1:
                    ds.setImageUrl("https://cartelescine.files.wordpress.com/2019/04/jokerbanner.jpg?w=593");
                    ds.setDescription("JOKER");
                    break;
                case 2:
                    ds.setImageUrl("https://www.geek.com/wp-content/uploads/2019/09/hustlers-top.png");
                    ds.setDescription("HUSTLERS");

                    break;
            }
            ds.setImageScaleType(ImageView.ScaleType.FIT_XY);
            sl.addSliderView(ds);
        }
    }

}
