package com.rikkei.training.appadmin_vutruonggiang.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.adapter.AdapterRevenue;
import com.rikkei.training.appadmin_vutruonggiang.modle.RevenueByTime;
import com.rikkei.training.appadmin_vutruonggiang.modle.TotalRevenue;

import java.util.ArrayList;
import java.util.List;

public class FragmentRevenue extends Fragment {
    private View view;
    MainActivity mainActivity;
    ImageView imgButBack;
    TextView tvLunch;
    TextView tvTonight;
    TextView tvNothing;
    RecyclerView rcvDataRevenue;
    String idRes="";
    EditText edIdRes;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference();
    List<RevenueByTime> revenueByTimeList=new ArrayList<>();
    List<TotalRevenue> totalRevenueList=new ArrayList<>();
    AdapterRevenue adapterRevenue;

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        FragmentRevenue fragment = new FragmentRevenue();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_revenue,container,false);
        Bundle bundle=getArguments();
        idRes=idRes+bundle.getString("IdRes","");
        init();
        if(idRes.equals("admin")){
            getDataAdmin();
        }else{
            getData();
        }
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mainActivity,RecyclerView.VERTICAL,false);
        rcvDataRevenue.setLayoutManager(layoutManager);
        databaseReference.child("TotalRevenueTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    RevenueByTime revenueByTime=data.getValue(RevenueByTime.class);
                    revenueByTimeList.add(revenueByTime);
                    if(revenueByTime.getId().equals(idRes)){
                        tvLunch.setText(revenueByTime.getLunch()+"");
                        tvTonight.setText(revenueByTime.getTonight()+"");
                        tvNothing.setText(revenueByTime.getNothing()+"");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void getData() {
        databaseReference.child("doanhthu").child(idRes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    TotalRevenue totalRevenue=data.getValue(TotalRevenue.class);
                    totalRevenueList.add(totalRevenue);
                }
                adapterRevenue=new AdapterRevenue(totalRevenueList);
                rcvDataRevenue.setAdapter(adapterRevenue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataAdmin() {
        String idNh=edIdRes.getText().toString().trim();
        if(!idNh.equals("")){
            try {
                databaseReference.child("doanhthu").child(idNh).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                        for(DataSnapshot data:dataSnapshotIterable){
                            TotalRevenue totalRevenue=data.getValue(TotalRevenue.class);
                            totalRevenueList.add(totalRevenue);
                        }
                        adapterRevenue=new AdapterRevenue(totalRevenueList);
                        rcvDataRevenue.setAdapter(adapterRevenue);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }catch (Exception e){

            }
        }
    }

    public void init(){
        mainActivity= (MainActivity) getActivity();
        imgButBack=view.findViewById(R.id.imgButBack);
        tvLunch=view.findViewById(R.id.tv11to13);
        tvNothing=view.findViewById(R.id.tvOtherTime);
        tvTonight=view.findViewById(R.id.tv17To19);
        rcvDataRevenue=view.findViewById(R.id.dataFoodRevenue);
        edIdRes=view.findViewById(R.id.edInputIdRes);
    }
}
