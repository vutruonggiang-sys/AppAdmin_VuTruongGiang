package com.rikkei.training.appadmin_vutruonggiang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.modle.TotalRevenue;

import java.util.List;

public class AdapterRevenue extends RecyclerView.Adapter<AdapterRevenue.ViewHolder> {
    List<TotalRevenue> totalRevenueList;

    public AdapterRevenue(List<TotalRevenue> totalRevenueList) {
        this.totalRevenueList = totalRevenueList;
    }

    @NonNull
    @Override
    public AdapterRevenue.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrevenue, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRevenue.ViewHolder holder, int position) {
        TotalRevenue totalRevenue=totalRevenueList.get(position);
        holder.tvRevenueFood.setText(totalRevenue.getTotal()+"");
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        try {
            databaseReference.child("food").child(totalRevenue.getId()).child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    holder.tvNameFood.setText(snapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        if (totalRevenueList == null)
            return 0;
        else
            return totalRevenueList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameFood;
        TextView tvRevenueFood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameFood=itemView.findViewById(R.id.tvNameFood);
            tvRevenueFood=itemView.findViewById(R.id.tvRevenueFood);
        }
    }
}
