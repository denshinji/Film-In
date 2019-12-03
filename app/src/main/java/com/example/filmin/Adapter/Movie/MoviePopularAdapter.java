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
import com.example.filmin.Data.FILM.FilmPopular;
import com.example.filmin.Detail.DetailMoviePopular;
import com.example.filmin.R;

import java.util.List;

public class MoviePopularAdapter extends RecyclerView.Adapter<MoviePopularAdapter.ListViewHolder> {
    private List<FilmPopular> filmPopularsItems;
    private Context context;

    public MoviePopularAdapter(List<FilmPopular> filmPopularsItems, Context context) {
        this.filmPopularsItems = filmPopularsItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviePopularAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new MoviePopularAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePopularAdapter.ListViewHolder holder, int position) {
        final FilmPopular filmPopular = filmPopularsItems.get(position);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + filmPopular.getImageurl())
                .into(holder.imgphotos);
        holder.rate.setText(filmPopular.getRate());
        holder.juduls.setText(filmPopular.getTitle());
        holder.descs.setText(filmPopular.getDesc());
        holder.releases.setText(filmPopular.getDaterelease());
    }

    @Override
    public int getItemCount() {
        return filmPopularsItems.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgphotos;
        Context context;
        RelativeLayout relativeLayout;
        TextView juduls, descs, releases, rate;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            rate = itemView.findViewById(R.id.item_rate);
            juduls = itemView.findViewById(R.id.item_judul);
            imgphotos = itemView.findViewById(R.id.item_photo);
            releases = itemView.findViewById(R.id.item_release);
            descs = itemView.findViewById(R.id.item_deksripsi);
            relativeLayout = itemView.findViewById(R.id.rv_item);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.show();
                    pd.setMessage(context.getString(R.string.loading));
                    new Thread(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(context, DetailMoviePopular.class);
                            intent.putExtra(DetailMoviePopular.MOVIE_POPULAR_KEY, filmPopularsItems.get(getLayoutPosition()));
                            context.startActivity(intent);
                            pd.dismiss();
                        }
                    }).start();
                }

            });
        }
    }
}
