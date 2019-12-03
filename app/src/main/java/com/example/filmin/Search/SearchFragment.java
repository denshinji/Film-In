package com.example.filmin.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmin.Adapter.SearchMoview;
import com.example.filmin.Data.SearchMovie;
import com.example.filmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {


    private String URL_SEARCH_TV = "https://api.themoviedb.org/3/search/movie?api_key=54b7a429ded4390f260d02b214ba785f&language=en-US&query=";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<SearchMovie> searches;
    private SearchView edt;
    private ImageView newinput;
    private String result;
    private TextView emptyview;
    private ProgressBar progressBar;

    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
        recyclerView = view.findViewById(R.id.list_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searches = new ArrayList<>();
        progressBar = view.findViewById(R.id.pr_search);
        newinput = view.findViewById(R.id.searchview);
        emptyview = view.findViewById(R.id.emptyview);
        edt = view.findViewById(R.id.search);
        edt.setQueryHint(getActivity().getString(R.string.attention));
        edt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerView.setVisibility(View.GONE);
                if (newText.isEmpty()) {
                    searches.clear();
                    recyclerView.setVisibility(View.GONE);
                    emptyview.setVisibility(View.GONE);
                    newinput.setVisibility(View.VISIBLE);
                }
                if (newText.length() > 0) {
                    newinput.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    newinput.setVisibility(View.VISIBLE);
                }
                if (newText.length() >= 2) {
                    if (searches.isEmpty()) {
                        emptyview.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        newinput.setVisibility(View.GONE);
                    } else {
                        emptyview.setVisibility(View.GONE);
                        newinput.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
                searches.clear();
                result = URL_SEARCH_TV + newText;
                json();


                return true;
            }
        });

        return view;
    }

    public void json() {
        newinput.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, result,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("results");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                SearchMovie search = new SearchMovie(
                                        o.getString("overview"),
                                        o.getString("title"),
                                        o.getString("poster_path"),
                                        o.getString("release_date"),
                                        o.getString("backdrop_path"),
                                        o.getString("original_language"),
                                        o.getString("popularity"),
                                        o.getString("vote_count"),
                                        o.getInt("id"),
                                        o.getString("vote_average")
                                );
                                searches.add(search);
                            }
                            adapter = new SearchMoview(searches, getActivity());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }


}

