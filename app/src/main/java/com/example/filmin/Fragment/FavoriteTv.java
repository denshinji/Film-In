package com.example.filmin.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmin.Adapter.FavoriteAdapter.FavoriteTvAdapter;
import com.example.filmin.Data.Favorite.FavoriteTvData;
import com.example.filmin.Database.FavoriteDBhelperTv;
import com.example.filmin.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTv extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FavoriteTvData> favorites;
    private FavoriteDBhelperTv favoriteDBhelper;
    private ProgressBar progressBar;

    public FavoriteTv() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.list_view_favorite);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favorites = new ArrayList<>();
        adapter = new FavoriteTvAdapter(favorites, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressBar = view.findViewById(R.id.pr_fav);
        favoriteDBhelper = new FavoriteDBhelperTv(getActivity());
        getAllFavorite();

        return view;
    }

    private void getAllFavorite() {
        progressBar.setVisibility(View.VISIBLE);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                favorites.clear();
                favorites.addAll(favoriteDBhelper.getAllFavorite());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }


}
