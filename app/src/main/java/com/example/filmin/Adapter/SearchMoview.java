package com.example.filmin.Adapter;

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
import com.example.filmin.Data.SearchMovie;
import com.example.filmin.Detail.DetailSearhMovie;
import com.example.filmin.R;

import java.util.ArrayList;
import java.util.List;

public class SearchMoview extends RecyclerView.Adapter<SearchMoview.ListViewHolder> {

    private List<SearchMovie> searches;
    private Context context;
    private ArrayList<SearchMovie> arraylist;

    public SearchMoview(List<SearchMovie> searches, Context context) {
        this.searches = searches;
        this.context = context;
    }


    @NonNull
    @Override
    public SearchMoview.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow, parent, false);
        return new SearchMoview.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMoview.ListViewHolder holder, int position) {
        final SearchMovie searchs = searches.get(position);

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + searchs.getImageurl())
                .into(holder.imgphotos);
        holder.juduls.setText(searchs.getTitle());
        holder.descs.setText(searchs.getDesc());
        holder.releases.setText(searchs.getDaterelease());
        holder.rate.setText(searchs.getRate());
    }


    @Override
    public int getItemCount() {
        return searches.size();
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
            relativeLayout = itemView.findViewById(R.id.rv_show);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.show();
                    pd.setMessage(context.getString(R.string.loading));
                    new Thread(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(context, DetailSearhMovie.class);
                            intent.putExtra(DetailSearhMovie.SEARCH_KEY, searches.get(getLayoutPosition()));
                            context.startActivity(intent);
                            pd.dismiss();
                        }
                    }).start();

                }
            });


        }
    }

}
