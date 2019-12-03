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
import com.example.filmin.Adapter.SearchTvAdapter;
import com.example.filmin.Data.SearchTv;
import com.example.filmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchTvFragment extends Fragment {


    private String URL_SEARCH_MOVIE = "https://api.themoviedb.org/3/search/tv?api_key=54b7a429ded4390f260d02b214ba785f&language=en-US&query=";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<SearchTv> searchestv;
    private SearchView edts;
    private String results;
    private ImageView newlay;
    private TextView emptviewtv;
    private ProgressBar progressBar;

    public SearchTvFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_tv, container, false);
        recyclerView = view.findViewById(R.id.list_searchTv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchestv = new ArrayList<>();
        progressBar = view.findViewById(R.id.pr_searchtv);
        newlay = view.findViewById(R.id.searchview);
        emptviewtv = view.findViewById(R.id.emptyview);
        edts = view.findViewById(R.id.searchtvs);
        edts.setQueryHint(getActivity().getString(R.string.attention));
        edts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerView.setVisibility(View.GONE);
                if (newText.isEmpty()) {
                    searchestv.clear();
                    recyclerView.setVisibility(View.GONE);
                    emptviewtv.setVisibility(View.GONE);
                    newlay.setVisibility(View.VISIBLE);
                }
                if (newText.length() > 0) {
                    newlay.setVisibility(View.GONE);
                    emptviewtv.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    newlay.setVisibility(View.VISIBLE);
                }
                if (newText.length() >= 2) {
                    if (searchestv.isEmpty()) {
                        emptviewtv.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        newlay.setVisibility(View.GONE);
                    } else {
                        emptviewtv.setVisibility(View.GONE);
                        newlay.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
                searchestv.clear();
                results = URL_SEARCH_MOVIE + newText;
                json();


                return true;
            }
        });

        return view;
    }

    public void json() {
        newlay.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, results,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("results");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                SearchTv search = new SearchTv(
                                        o.getString("overview"),
                                        o.getString("original_name"),
                                        o.getString("poster_path"),
                                        o.getString("first_air_date"),
                                        o.getString("backdrop_path"),
                                        o.getString("original_language"),
                                        o.getString("popularity"),
                                        o.getString("vote_count"),
                                        o.getString("vote_average")

                                );
                                searchestv.add(search);
                            }
                            adapter = new SearchTvAdapter(searchestv, getActivity());
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

