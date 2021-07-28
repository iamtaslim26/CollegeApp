package com.kgec.collegeadminapp.Users;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kgec.collegeadminapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NoticeUsersAdapter extends RecyclerView.Adapter<NoticeUsersAdapter.NoticeViewHolder> {

    private Context mContext;
    private List<NoticeUsers>list;


    public NoticeUsersAdapter(Context mContext,List<NoticeUsers>list){

        this.mContext=mContext;
        this.list=list;
    }


    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item_layout,parent,false);
        return  new NoticeUsersAdapter.NoticeViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {


        final NoticeUsers item=list.get(position);
        holder.title_notice.setText(item.getTitle());
        holder.time.setText("Time: "+item.getTime());
        holder.date.setText("Date: "+item.getDate());
        Picasso.get().load(item.getImageUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,FullImageActivity.class);
                intent.putExtra("image",item.getImageUrl());
                mContext.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title_notice,date,time;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.user_notice_show_image);
            title_notice=itemView.findViewById(R.id.user_notice_title);
            date=itemView.findViewById(R.id.user_notice_date);
            time=itemView.findViewById(R.id.user_notice_time);
        }
    }
}
