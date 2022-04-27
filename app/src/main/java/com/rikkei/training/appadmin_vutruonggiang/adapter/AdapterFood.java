package com.rikkei.training.appadmin_vutruonggiang.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.modle.Food;
import com.rikkei.training.appadmin_vutruonggiang.ui.FragmentEditFood;
import com.rikkei.training.appadmin_vutruonggiang.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder> {
    List<Food> foodList;
    MainActivity mainActivity;
    List<String> idFoodList;
    String idRes;

    public AdapterFood(List<Food> foodList, MainActivity context,List<String> idFoodList, String idRes) {
        this.foodList = foodList;
        this.mainActivity = context;
        this.idFoodList=idFoodList;
        this.idRes=idRes;
    }

    @NonNull
    @Override
    public AdapterFood.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFood.ViewHolder holder, int position) {
        Food food=foodList.get(position);
        holder.tvStart.setText(food.getReview()+"");
        holder.tvPrice.setText(food.getPrice()+"");
        holder.tvNameFood.setText(food.getName());
        Glide.with(mainActivity).load(food.getUrl()).into(holder.imgUrl);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentEditFood fragment=new FragmentEditFood();
                Bundle bundle=new Bundle();
                bundle.putString("url", food.getUrl());
                bundle.putString("nameFood", food.getName());
                bundle.putString("detailFood", food.getDetail());
                bundle.putString("id", food.getId());
                bundle.putString("type", food.getType());
                bundle.putString("idRes",food.getIdNhaHang());
                bundle.putFloat("price",food.getPrice());
                bundle.putString("IdRes",idRes);
                bundle.putStringArrayList("listIDFood", (ArrayList<String>) idFoodList);
                fragment.setArguments(bundle);
                mainActivity.getFragment(fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (foodList == null)
            return 0;
        else
            return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameFood;
        TextView tvPrice;
        TextView tvStart;
        ImageView imgUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameFood = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStart = itemView.findViewById(R.id.tvReview);
            imgUrl=itemView.findViewById(R.id.imgAnh);
        }
    }
}
