package com.example.filmin.Adapter.TV;

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
import com.example.filmin.Data.TVSHOW.TvShow;
import com.example.filmin.Detail.DetailShow;
import com.example.filmin.R;

import java.util.List;

public class TvShowAdapapter extends RecyclerView.Adapter<TvShowAdapapter.ListViewHolder> {

    private List<TvShow> tvItems;
    private Context context;

    public TvShowAdapapter(List<TvShow> tvItems, Context context) {
        this.tvItems = tvItems;
        this.context = context;
    }


    @NonNull
    @Override
    public TvShowAdapapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final TvShow tvshow = tvItems.get(position);

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + tvshow.getImageurl())
                .into(holder.imgphotos);
        holder.juduls.setText(tvshow.getTitle());
        holder.descs.setText(tvshow.getDesc());
        holder.releases.setText(tvshow.getDaterelease());
        holder.rate.setText(tvshow.getRate());
    }


    @Override
    public int getItemCount() {
        return tvItems.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgphotos;
        Context context;
        RelativeLayout relativeLayout;
        TextView juduls, descs, releases,rate;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            rate = itemView.findViewById(R.id.item_rate);
            juduls = itemView.findViewById(R.id.item_judul);
            imgphotos = itemView.findViewById(R.id.item_photo);
            releases = itemView.findViewById(R.id.item_release);
            descs = itemView.findViewById(R.id.item_deksripsi);
            relativeLayout = itemView.findViewById(R.id.rv_show);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.show();
                    pd.setMessage(context.getString(R.string.loading));
                    new Thread(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(context, DetailShow.class);
                            intent.putExtra(DetailShow.TV_KEY, tvItems.get(getLayoutPosition()));
                            context.startActivity(intent);
                            pd.dismiss();
                        }
                    }).start();

                }
            });
        }
    }
}


