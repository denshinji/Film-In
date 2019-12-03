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
import com.example.filmin.Data.TVSHOW.TVPopuler;
import com.example.filmin.Data.TVSHOW.TvShow;
import com.example.filmin.Detail.DetailShow;
import com.example.filmin.Detail.DetailShowPopuler;
import com.example.filmin.R;

import java.util.List;

public class TvPopulerAdapter extends RecyclerView.Adapter<TvPopulerAdapter.ListViewHolder> {

    private List<TVPopuler> tvPopulerItems;
    private Context context;

    public TvPopulerAdapter(List<TVPopuler> tvPopulerItems, Context context) {
        this.tvPopulerItems = tvPopulerItems;
        this.context = context;
    }


    @NonNull
    @Override
    public TvPopulerAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow, parent, false);
        return new TvPopulerAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvPopulerAdapter.ListViewHolder holder, int position) {
        final TVPopuler tvpopuler = tvPopulerItems.get(position);

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + tvpopuler.getImageurl())
                .into(holder.imgphotos);
        holder.juduls.setText(tvpopuler.getTitle());
        holder.descs.setText(tvpopuler.getDesc());
        holder.releases.setText(tvpopuler.getDaterelease());
        holder.rate.setText(tvpopuler.getRate());
    }


    @Override
    public int getItemCount() {
        return tvPopulerItems.size();
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
                            Intent intent = new Intent(context, DetailShowPopuler.class);
                            intent.putExtra(DetailShowPopuler.TV_POPULER_KEY, tvPopulerItems.get(getLayoutPosition()));
                            context.startActivity(intent);
                            pd.dismiss();
                        }
                    }).start();

                }
            });
        }
    }
}


