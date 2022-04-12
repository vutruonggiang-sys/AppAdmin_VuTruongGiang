package com.rikkei.training.appadmin_vutruonggiang.adapter;

import android.graphics.Color;
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
import com.rikkei.training.appadmin_vutruonggiang.modle.NhaHang;
import com.rikkei.training.appadmin_vutruonggiang.ui.FragmentEditRestaurant;
import com.rikkei.training.appadmin_vutruonggiang.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterRestaurant extends RecyclerView.Adapter<AdapterRestaurant.ViewHolder> {
    List<NhaHang> nhaHangList;
    MainActivity mainActivity;
    List<String> idResList;

    public AdapterRestaurant(List<NhaHang> nhaHangList, MainActivity context,List<String> idResList) {
        this.nhaHangList = nhaHangList;
        this.mainActivity = context;
        this.idResList=idResList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_res,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NhaHang nhaHang=nhaHangList.get(position);
        holder.tvAddress.setText(nhaHang.getAddress());
        holder.tvName.setText(nhaHang.getName());
        holder.tvOpen_Close.setText(nhaHang.getOpen() + "-" + nhaHang.getClose());
        Glide.with(mainActivity).load(nhaHang.getUrl()).into(holder.imgRes);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String gioHienTai = simpleDateFormat.format(calendar.getTime());
        try {
            Date hienTai = simpleDateFormat.parse(gioHienTai);
            Date mo = simpleDateFormat.parse(nhaHang.getOpen());
            Date dong = simpleDateFormat.parse(nhaHang.getClose());
            if (hienTai.after(mo) && hienTai.before(dong)) {
                holder.tvStatusOpenClose.setText("Đang Mở Cửa");
            } else {
                holder.tvStatusOpenClose.setText("Đóng Cửa");
                holder.tvStatusOpenClose.setTextColor(Color.BLACK);
            }
        } catch (Exception e) {

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentEditRestaurant fragment=new FragmentEditRestaurant();
                Bundle bundle=new Bundle();
                bundle.putString("url", nhaHang.getUrl());
                bundle.putString("nameRes", nhaHang.getName());
                bundle.putString("addressRes", nhaHang.getAddress());
                bundle.putString("open", nhaHang.getOpen());
                bundle.putString("close", nhaHang.getClose());
                bundle.putString("idRes",nhaHang.getId());
                bundle.putStringArrayList("listIDRes", (ArrayList<String>) idResList);
                fragment.setArguments(bundle);
                mainActivity.getFragment(fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (nhaHangList == null)
            return 0;
        else
            return nhaHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvOpen_Close, tvStatusOpenClose;
        ImageView imgRes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress=itemView.findViewById(R.id.tvAddressNhaHang);
            tvName=itemView.findViewById(R.id.tvnameNhaHang);
            tvOpen_Close=itemView.findViewById(R.id.tv_Detail_OC);
            tvStatusOpenClose=itemView.findViewById(R.id.tv_Detail_mo_dong);
            imgRes=itemView.findViewById(R.id.imAnhNhaHang);
        }
    }
}
