package com.example.filmin.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmin.Adapter.Movie.MoviePopularAdapter;
import com.example.filmin.Data.FILM.FilmPopular;
import com.example.filmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviePopular extends Fragment {


    private static final String URL_DATA = "https://api.themoviedb.org/3/movie/popular?api_key=54b7a429ded4390f260d02b214ba785f&language=en-US&page=1";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FilmPopular> filmPopularsItems;
    private ProgressBar progressBar;

    public MoviePopular() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_popular, container, false);

        recyclerView = view.findViewById(R.id.list_view_popular);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        filmPopularsItems = new ArrayList<>();
        progressBar = view.findViewById(R.id.pr_movie_fav);


        loadRecylerData();
        return view;
    }

    private void loadRecylerData() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("results");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                FilmPopular filmPopular = new FilmPopular(
                                        o.getString("overview"),
                                        o.getString("title"),
                                        o.getString("poster_path"),
                                        o.getString("release_date"),
                                        o.getString("backdrop_path"),
                                        o.getString("original_language"),
                                        o.getString("vote_count"),
                                        o.getString("popularity"),
                                        o.getString("vote_average")

                                );
                                filmPopularsItems.add(filmPopular);
                            }
                            adapter = new MoviePopularAdapter(filmPopularsItems, getActivity());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
