package com.example.filmin.Adapter.Movie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filmin.Data.FILM.Film;
import com.example.filmin.Detail.DetailMovie;
import com.example.filmin.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder> {
    private List<Film> filmItems;
    private Context context;

    public MovieAdapter(List<Film> filmItems, Context context) {
        this.filmItems = filmItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ListViewHolder holder, int position) {
        final Film film = filmItems.get(position);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + film.getImageurl())
                .into(holder.imgphotos);
        holder.juduls.setText(film.getTitle());
        holder.descs.setText(film.getDesc());
        holder.rates.setText(film.getRate());
        holder.releases.setText(film.getDaterelease());

    }

    @Override
    public int getItemCount() {
        return filmItems.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgphotos;
        Context context;
        RelativeLayout relativeLayout;
        TextView juduls, descs, releases, rates;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            juduls = itemView.findViewById(R.id.item_judul);
            imgphotos = itemView.findViewById(R.id.item_photo);
            releases = itemView.findViewById(R.id.item_release);
            descs = itemView.findViewById(R.id.item_deksripsi);
            rates = itemView.findViewById(R.id.item_rate);
            relativeLayout = itemView.findViewById(R.id.rv_item);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.show();
                    pd.setMessage(context.getString(R.string.loading));
                    new Thread(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(context, DetailMovie.class);
                            intent.putExtra(DetailMovie.MOVIE_KEY, filmItems.get(getLayoutPosition()));
                            context.startActivity(intent);
                            pd.dismiss();
                        }
                    }).start();
                }

            });
        }
    }


}
