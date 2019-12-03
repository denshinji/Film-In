package com.example.favmov;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ListViewHolder> {
    private ArrayList<FavoriteData> listMovie = new ArrayList<>();
    private Context context;


    public FavoriteAdapter(Activity activity) {
        this.context = activity;
    }

    private ArrayList<FavoriteData> getdata() {
        return listMovie;
    }

    void setListFav(List<FavoriteData> listMovies) {
        listMovie.clear();
        listMovie.addAll(listMovies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FavoriteAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ListViewHolder holder, int position) {
        String poster = "https://image.tmdb.org/t/p/w342/" + getdata().get(position).getImageurl();
        Glide.with(holder.itemView.getContext())
                .load(poster)
                .into(holder.imgphotos);
        holder.juduls.setText(getdata().get(position).getTitle());
        holder.descs.setText(getdata().get(position).getDesc());
        holder.releases.setText(getdata().get(position).getDaterelease());
    }

    @Override
    public int getItemCount() {
        return getdata().size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgphotos;
        Context context;
        TextView juduls, descs, releases;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            juduls = itemView.findViewById(R.id.item_judul);
            imgphotos = itemView.findViewById(R.id.item_photo);
            releases = itemView.findViewById(R.id.item_release);
            descs = itemView.findViewById(R.id.item_deksripsi);


        }
    }
}

