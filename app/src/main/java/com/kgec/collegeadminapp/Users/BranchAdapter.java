package com.kgec.collegeadminapp.Users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.kgec.collegeadminapp.R;

import java.util.List;

public class BranchAdapter extends PagerAdapter {

    private Context mContext;
    private List<BranchModel>list;

    public BranchAdapter(Context mContext, List<BranchModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {


        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view1= LayoutInflater.from(mContext).inflate(R.layout.branch_item_layout, container,false);

        ImageView brImg;
        TextView br_title,br_desc;

        brImg=view1.findViewById(R.id.branch_item_img);
        br_desc=view1.findViewById(R.id.branch_item_description);
        br_title=view1.findViewById(R.id.branch_item_name);

        brImg.setImageResource(list.get(position).getImg());
        br_title.setText(list.get(position).getTitle());
        br_desc.setText(list.get(position).getDescription());

        container.addView(view1,0);;

        return view1;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }
}
