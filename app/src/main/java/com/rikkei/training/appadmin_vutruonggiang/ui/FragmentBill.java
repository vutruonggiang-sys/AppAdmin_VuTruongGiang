package com.rikkei.training.appadmin_vutruonggiang.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.modle.ThongTinNguoiOrder;

import java.util.ArrayList;
import java.util.List;

public class FragmentBill extends Fragment {
    private View view;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    MainActivity mainActivity;
    ImageView imgBackHome;
    RecyclerView dataBill;
    List<ThongTinNguoiOrder> thongTinNguoiOrderList=new ArrayList<>();
    String idRes="";
    public static Fragment newInstance() {

        Bundle args = new Bundle();

        FragmentBill fragment = new FragmentBill();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_cart_bill, container, false);
        init();
        Bundle bundle=getArguments();
        idRes=idRes+bundle.getString("IdRes","");
        databaseReference=firebaseDatabase.getReference();
        databaseReference.child("thong_tin_nguoi_nhan_hang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    public void init(){
        mainActivity= (MainActivity) getActivity();
        imgBackHome=view.findViewById(R.id.imgBack);
        dataBill=view.findViewById(R.id.rcvDataBill);
    }
}
