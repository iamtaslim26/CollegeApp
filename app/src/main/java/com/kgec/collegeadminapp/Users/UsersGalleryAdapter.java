package com.kgec.collegeadminapp.Users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kgec.collegeadminapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersGalleryAdapter extends RecyclerView.Adapter<UsersGalleryAdapter.GalleryViewHolder> {

    private Context mContext;
    private List<UsersGallery>list;

    public UsersGalleryAdapter(Context mContext, List<UsersGallery> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item,parent,false);
        return new UsersGalleryAdapter.GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {

        final UsersGallery item=list.get(position);

        Picasso.get().load(item.getImageUrl()).into(holder.imageView);
       // Glide.with(mContext).load(list.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public GalleryViewHolder(@NonNull View itemView) {

            super(itemView);

            imageView=itemView.findViewById(R.id.image_gallery);
        }


    }
}
