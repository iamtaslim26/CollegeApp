package com.kgec.collegeadminapp.Admin.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kgec.collegeadminapp.Faculty.DeleteNoticeActivity;
import com.kgec.collegeadminapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeAdapterViewHolder>{

    private Context mContext;
    private List<Notice>list;

    public NoticeAdapter(Context mContext, List<Notice> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_item_layout,parent,false);
        return new NoticeAdapter.NoticeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapterViewHolder holder, int position) {

        final Notice item=list.get(position);
        holder.title_notice.setText(item.getTitle());
        Picasso.get().load(item.getImageUrl()).into(holder.imageView);

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setTitle("Are you want to delete?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Notice");
                        reference.child(item.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){

                                    Toast.makeText(mContext, "Deleted Successfully....", Toast.LENGTH_SHORT).show();
                                    mContext.startActivity(new Intent(mContext, DeleteNoticeActivity.class));

                                }
                                else {

                                    String e=task.getException().getMessage();
                                    Toast.makeText(mContext, "Failed....  "+e, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.cancel();
                    }
                });


                builder.show();


            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NoticeAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title_notice;
        Button delete_btn;

        public NoticeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.news_feed_show_image);
            title_notice=itemView.findViewById(R.id.news_feed_title_notice);
            delete_btn=itemView.findViewById(R.id.news_feed_delete_btn);
        }
    }
}
