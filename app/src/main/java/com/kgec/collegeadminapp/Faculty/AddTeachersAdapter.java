package com.kgec.collegeadminapp.Faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kgec.collegeadminapp.Admin.model.AddTeacher;
import com.kgec.collegeadminapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddTeachersAdapter extends RecyclerView.Adapter<AddTeachersAdapter.TeachersViewHolder> {

    private Context mContext;
    private List<AddTeacher>list;
    private String category;


    public AddTeachersAdapter(Context mContext, List<AddTeacher> list,String category) {
        this.mContext = mContext;
        this.list = list;
        this.category=category;
    }

    @NonNull
    @Override
    public TeachersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_item,parent,false);
        return new AddTeachersAdapter.TeachersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeachersViewHolder holder, int position) {

        final AddTeacher item=list.get(position);

        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());

        try {

            Picasso.get().load(item.getImageUrl()).placeholder(R.drawable.common_google_signin_btn_icon_dark_normal).into(holder.imageView);

        } catch (Exception e) {

            e.printStackTrace();
        }

        holder.update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, UpdateTeacherActivity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("post",item.getPost());
                intent.putExtra("imageview",item.getImageUrl());
                intent.putExtra("key",item.getKey());
                intent.putExtra("category",category);

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
        Button update_btn;

        public TeachersViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.show_teachers_name);
            email=itemView.findViewById(R.id.show_teachers_email);
            post=itemView.findViewById(R.id.show_teachers_post);
            imageView=itemView.findViewById(R.id.teachers_dp);
            update_btn=itemView.findViewById(R.id.update_teachers_button);
        }
    }
}
