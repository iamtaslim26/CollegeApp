package com.kgec.collegeadminapp.Users;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kgec.collegeadminapp.R;

import java.util.List;

public class EbooksAdapter extends RecyclerView.Adapter<EbooksAdapter.EbooksViewHolder> {

    private Context mContext;
    private List<Ebooks>list;

    public EbooksAdapter(Context mContext, List<Ebooks> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public EbooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.pdf_item_layout,parent,false);
        return new EbooksAdapter.EbooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbooksViewHolder holder, int position) {

        final Ebooks item=list.get(position);

        holder.title.setText(item.getPdfTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,PdfViewerActivity.class);
                intent.putExtra("pdfUrl",item.getPdfUrl());
                mContext.startActivity(intent);
            }
        });

        holder.download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.getPdfUrl()));
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class EbooksViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView download_btn;

        public EbooksViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title_pdf);
            download_btn=itemView.findViewById(R.id.download_pdf_btn);
        }
    }

}
