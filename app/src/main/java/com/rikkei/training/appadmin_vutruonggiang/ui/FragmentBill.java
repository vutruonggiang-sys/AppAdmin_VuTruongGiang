package com.rikkei.training.appadmin_vutruonggiang.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.rikkei.training.appadmin_vutruonggiang.adapter.AdapterRecyleViewGioHang;
import com.rikkei.training.appadmin_vutruonggiang.adapter.AdapterRecyleViewSumDaGiao;
import com.rikkei.training.appadmin_vutruonggiang.modle.ThongTinNguoiOrder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentBill extends Fragment {
    private View view;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    MainActivity mainActivity;
    ImageView imgBackHome;
    RecyclerView dataBill;
    List<ThongTinNguoiOrder> thongTinNguoiOrderList = new ArrayList<>();
    String idRes = "";
    AdapterRecyleViewSumDaGiao adapter;
    ImageView imgLateTime;
    Calendar calendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
    List<ThongTinNguoiOrder> thongTinNguoiOrderList1 = new ArrayList<>();
    List<ThongTinNguoiOrder> thongTinNguoiOrderListLateTime = new ArrayList<>();
    private boolean isTime = false;


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
        Bundle bundle = getArguments();
        idRes = idRes + bundle.getString("IdRes", "");
        databaseReference = firebaseDatabase.getReference();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
        dataBill.setLayoutManager(layoutManager);
        databaseReference.child("TotalBill").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable) {
                    ThongTinNguoiOrder thongTinNguoiOrder = data.getValue(ThongTinNguoiOrder.class);
                    thongTinNguoiOrderList.add(thongTinNguoiOrder);
                }
                if (idRes.equals("admin")) {
                    adapter = new AdapterRecyleViewSumDaGiao(thongTinNguoiOrderList, mainActivity);
                    dataBill.setAdapter(adapter);
                } else {
                    for (int i = 0; i < thongTinNguoiOrderList.size(); i++) {
                        if (thongTinNguoiOrderList.get(i).getIdRes().equals(idRes)) {
                            thongTinNguoiOrderList1.add(thongTinNguoiOrderList.get(i));
                        }
                        if (i == (thongTinNguoiOrderList.size() - 1)) {
                            adapter = new AdapterRecyleViewSumDaGiao(thongTinNguoiOrderList1, mainActivity);
                            dataBill.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imgLateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTime == false) {
                    long timeNow = System.currentTimeMillis();
                    if (idRes.equals("admin")) {
                        for (int i = 0; i < thongTinNguoiOrderList.size(); i++) {
                            long timeLate = 5 * 60 * 60 * 1000;
                            String empty = dateFormat.format((timeNow - timeLate));
                            try {
                                Date date = dateFormat.parse(empty);
                                Date dateOrder = dateFormat.parse(thongTinNguoiOrderList.get(i).getId());
                                if (date.after(dateOrder)) {
                                    thongTinNguoiOrderListLateTime.add(thongTinNguoiOrderList.get(i));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (i == thongTinNguoiOrderList.size()) {
                                adapter = new AdapterRecyleViewSumDaGiao(thongTinNguoiOrderListLateTime, mainActivity);
                                dataBill.setAdapter(adapter);
                            }
                        }
                    } else {
                        for (int i = 0; i < thongTinNguoiOrderList1.size(); i++) {
                            long timeLate = 5 * 60 * 60 * 1000;
                            String empty = dateFormat.format((timeNow - timeLate));
                            try {
                                Date date = dateFormat.parse(empty);
                                Date dateOrder = dateFormat.parse(thongTinNguoiOrderList1.get(i).getId());
                                if (date.after(dateOrder)) {
                                    thongTinNguoiOrderListLateTime.add(thongTinNguoiOrderList1.get(i));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (i == thongTinNguoiOrderList.size()) {
                                adapter = new AdapterRecyleViewSumDaGiao(thongTinNguoiOrderListLateTime, mainActivity);
                                dataBill.setAdapter(adapter);
                            }
                        }
                    }
                    isTime = true;
                }
            }
        });
        imgBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTime) {
                    if (idRes.equals("admin")) {
                        adapter = new AdapterRecyleViewSumDaGiao(thongTinNguoiOrderList, mainActivity);
                        dataBill.setAdapter(adapter);
                    } else {
                        adapter = new AdapterRecyleViewSumDaGiao(thongTinNguoiOrderList1, mainActivity);
                        dataBill.setAdapter(adapter);
                    }
                    isTime=false;
                } else {
                    FragmentHome fragmentHome = new FragmentHome();
                    Bundle bundle = new Bundle();
                    bundle.putString("IdRes", idRes);
                    fragmentHome.setArguments(bundle);
                    mainActivity.getFragment(fragmentHome);
                }
            }
        });
        return view;
    }

    public void init() {
        mainActivity = (MainActivity) getActivity();
        imgBackHome = view.findViewById(R.id.imgBack);
        dataBill = view.findViewById(R.id.rcvDataBill);
        imgLateTime = view.findViewById(R.id.imgLateTime);
    }
}
