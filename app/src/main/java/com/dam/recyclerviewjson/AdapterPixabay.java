package com.dam.recyclerviewjson;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdapterPixabay extends RecyclerView.Adapter<AdapterPixabay.MyViewHolder> {
    private Context context;
    private ArrayList<ModelPixabay> pixabayArrayList;

    public AdapterPixabay(Context context, ArrayList<ModelPixabay> pixabayArrayList) {
        this.context = context;
        this.pixabayArrayList = pixabayArrayList;
    }

    // La gestion du click
    public OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // 4 Remplissage du DataBinding

        ModelPixabay currentItem = pixabayArrayList.get(position);

        String auteur = currentItem.getAuteur();
        int nbLikes = currentItem.getNbLikes();

        holder.auteur.setText(auteur);

        holder.nbLikes.setText(Integer.toString(nbLikes));

        // Gestion de l'image
        String imgUrl = currentItem.getImageUrl();

        /* Utilisation de Glide pour la gestion des images */
        Context context = holder.image.getContext();


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);

        // methode normale
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return pixabayArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView auteur, nbLikes;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            auteur = itemView.findViewById(R.id.tvAuteur);
            nbLikes = itemView.findViewById(R.id.tvNbLikes);
            image = itemView.findViewById(R.id.ivImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



}
