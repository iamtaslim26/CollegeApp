package com.kgec.collegeadminapp.Users;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kgec.collegeadminapp.Admin.model.AddTeacher;
import com.kgec.collegeadminapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowTeachersAdapter extends RecyclerView.Adapter<ShowTeachersAdapter.TeachersViewHolder> {

    private Context mContext;
    private List<AddTeacher>list;

    public ShowTeachersAdapter(Context mContext, List<AddTeacher> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public TeachersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.show_faculty_item,viewGroup,false);
        return new ShowTeachersAdapter.TeachersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeachersViewHolder holder, int position) {

        final AddTeacher item=list.get(position);

        holder.email.setText(item.getEmail());
        holder.name.setText(item.getName());
        holder.post.setText(item.getPost());
        Picasso.get().load(item.getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,ShowTeachersDetailsActivity.class);
                intent.putExtra("email",item.getEmail());
                intent.putExtra("name",item.getName());
                intent.putExtra("post",item.getPost());
                intent.putExtra("image",item.getImageUrl());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TeachersViewHolder extends RecyclerView.ViewHolder {

        TextView name,email,post;
        CircleImageView imageView;

        public TeachersViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.show_teachers_name_faculty);
            email=itemView.findViewById(R.id.show_teachers_email_faculty);
            post=itemView.findViewById(R.id.show_teachers_post_faculty);
            imageView=itemView.findViewById(R.id.teachers_dp_faculty);
        }
    }
}
