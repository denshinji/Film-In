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
import com.example.filmin.Adapter.Movie.MovieAdapter;
import com.example.filmin.Data.FILM.Film;
import com.example.filmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {


    private static final String URL_DATA = "https://api.themoviedb.org/3/movie/now_playing?api_key=54b7a429ded4390f260d02b214ba785f&language=en-US&page=1";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Film> filmItems;
    private ProgressBar pr;

    public MovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = view.findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        filmItems = new ArrayList<>();
        pr = view.findViewById(R.id.pr_movie);
        loadRecylerData();
        return view;
    }

    private void loadRecylerData() {

        pr.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pr.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                Film film = new Film(
                                        o.getString("overview"),
                                        o.getString("title"),
                                        o.getString("poster_path"),
                                        o.getString("release_date"),
                                        o.getString("backdrop_path"),
                                        o.getString("original_language"),
                                        o.getString("vote_count"),
                                        o.getString("popularity"),
                                        o.getInt("id"),
                                        o.getString("vote_average")
                                );
                                filmItems.add(film);
                            }
                            adapter = new MovieAdapter(filmItems, getActivity());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}
