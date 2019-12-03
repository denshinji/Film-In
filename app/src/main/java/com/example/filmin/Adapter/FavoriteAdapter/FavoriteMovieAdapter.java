package com.example.filmin.Adapter.FavoriteAdapter;

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
import com.example.filmin.Data.Favorite.FavoriteMovieData;
import com.example.filmin.Detail.DetailFavoriteMovie;
import com.example.filmin.R;

import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ListViewHolder> {
    private List<FavoriteMovieData> favorites;
    private Context context;

    public FavoriteMovieAdapter(List<FavoriteMovieData> favorites, Context context) {
        this.favorites = favorites;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FavoriteMovieAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.ListViewHolder holder, int position) {
        final FavoriteMovieData favorite = favorites.get(position);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + favorite.getImageurl())
                .into(holder.imgphotos);
        holder.juduls.setText(favorite.getTitle());
        holder.descs.setText(favorite.getDesc());
        holder.rate.setText(favorite.getRate());
        holder.releases.setText(favorite.getDaterelease());
    }

    @Override
    public int getItemCount() {
        return favorites.size();
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
                            Intent intent = new Intent(context, DetailFavoriteMovie.class);
                            intent.putExtra(DetailFavoriteMovie.FAVORITE_MOVIE_KEY, favorites.get(getLayoutPosition()));
                            context.startActivity(intent);
                            pd.dismiss();
                        }
                    }).start();
                }

            });


        }
    }
}

