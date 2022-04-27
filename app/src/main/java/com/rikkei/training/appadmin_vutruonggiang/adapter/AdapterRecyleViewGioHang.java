package com.rikkei.training.appadmin_vutruonggiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.modle.Food_Order;;import java.util.List;

public class AdapterRecyleViewGioHang extends RecyclerView.Adapter<AdapterRecyleViewGioHang.ViewHoder> {
    List<Food_Order> food_orderList;
    Context context;

    public AdapterRecyleViewGioHang(List<Food_Order> food_orderList, Context context) {
        this.food_orderList = food_orderList;
        notifyDataSetChanged();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_giohang, parent, false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        Food_Order food_order = food_orderList.get(position);
        if (food_order == null) {
            return;
        }
        holder.tvName.setText(food_order.getName());
        holder.tvPrice.setText(food_order.getPrice() + "");
        holder.tvAmount.setText(food_order.getAmount() + "");
        Glide.with(context).load(food_order.getUrl()).into(holder.image);
    }

    public void release() {
        context = null;
    }

    @Override
    public int getItemCount() {
        if (food_orderList == null) return 0;
        else
            return food_orderList.size();
    }


    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvAmount;
        ImageView image;

        public ViewHoder(@NonNull View itemView) {

            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_food_cart);
            tvAmount = itemView.findViewById(R.id.tv_number_food_cart);
            tvPrice = itemView.findViewById(R.id.tv_price_food_cart);
            image = itemView.findViewById(R.id.image_do_an_cart);
        }
    }
}
