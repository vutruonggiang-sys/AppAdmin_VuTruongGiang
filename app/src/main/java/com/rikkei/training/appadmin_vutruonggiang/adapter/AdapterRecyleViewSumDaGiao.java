package com.rikkei.training.appadmin_vutruonggiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.modle.Food_Order;
import com.rikkei.training.appadmin_vutruonggiang.modle.ThongTinNguoiOrder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdapterRecyleViewSumDaGiao extends RecyclerView.Adapter<AdapterRecyleViewSumDaGiao.ViewHoder> {
    List<ThongTinNguoiOrder> thongTinNguoiOrderList;
    String email;
    List<Food_Order> food_orderList;
    Context context;
    AdapterRecyleViewGioHang adapterRecyleViewGioHang;
    int d=0;
    //String ma_delete="";
    FirebaseDatabase firebaseDatabase;
    //IThanhToan iThanhToan;
    String tongFood="";
    long tong=0;
    public AdapterRecyleViewSumDaGiao(List<ThongTinNguoiOrder> thongTinNguoiOrderList, String email, Context context) {
        this.thongTinNguoiOrderList = thongTinNguoiOrderList;
        this.email=email;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.fragment_item_dagiao,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        ThongTinNguoiOrder thongTinNguoiOrder=thongTinNguoiOrderList.get(position);
        boolean check=false;
        //ma_delete=ma_delete+key.getId();
        if(thongTinNguoiOrder==null) {
            holder.tv_sum_dagiao.setText(0+"");
            return;
        }
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        holder.dataDagiao.setLayoutManager(layoutManager);
        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        databaseReference.child("da_giao").child(email).child(thongTinNguoiOrder.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                food_orderList=new ArrayList<>();
                tong=0;
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    Food_Order food_order=data.getValue(Food_Order.class);
                    food_orderList.add(food_order);
                    tong=tong+food_order.getPrice()*food_order.getAmount();
                    tongFood=tongFood+food_order.getName()+" (X"+(food_order.getPrice()*food_order.getAmount())+") ";
                }
                adapterRecyleViewGioHang=new AdapterRecyleViewGioHang(food_orderList,context);
                holder.dataDagiao.setAdapter(adapterRecyleViewGioHang);
                holder.tv_sum_dagiao.setText(tong+"");
                holder.tvTotalPayable.setText(tong+20000-thongTinNguoiOrder.getGiaKhuyenMai()+" VND");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.but_ThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(d!=1){
                    Toast.makeText(context,"Quý Khách Vui Lòng Chọn Đơn Hàng Thanh Toán",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //iThanhToan.getKey_delete(key.getId());
                    Calendar calendar=Calendar.getInstance();
                    DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String time=dateFormat.format(calendar.getTime());
                    
                    databaseReference.child("thong_tin_nguoi_nhan_hang").child(email).child(thongTinNguoiOrder.getId()).removeValue();
                    databaseReference.child("da_giao").child(email).child(thongTinNguoiOrder.getId()).removeValue();
                }
            }
        });
        holder.tv_hienThiMaGiamGia.setText(thongTinNguoiOrder.getGiaKhuyenMai()+" VND");

    }
    public void release(){
        context=null;
    }

    @Override
    public int getItemCount() {
        if(thongTinNguoiOrderList==null)
            return 0;
        else
            return thongTinNguoiOrderList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tv_sum_dagiao;
        RecyclerView dataDagiao;
        TextView tv_hienThiMaGiamGia;
        Button but_ThanhToan;
        TextView tvTotalPayable;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tv_sum_dagiao=itemView.findViewById(R.id.tv_sum_money);
            dataDagiao=itemView.findViewById(R.id.data_sum_dagiao);
            tv_hienThiMaGiamGia=itemView.findViewById(R.id.tvMaGiamGia);
            but_ThanhToan=itemView.findViewById(R.id.but_order_cart);
            tvTotalPayable=itemView.findViewById(R.id.tvTotalPayable);
        }
    }
}
