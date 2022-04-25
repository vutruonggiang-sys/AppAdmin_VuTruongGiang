package com.rikkei.training.appadmin_vutruonggiang.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.adapter.AdapterRestaurant;
import com.rikkei.training.appadmin_vutruonggiang.modle.NhaHang;

import java.util.ArrayList;
import java.util.List;

public class FragmentRestaurant extends Fragment {

    private View view;
    private FloatingActionButton butAdd;
    private RecyclerView rcvDateRes;
    private MainActivity mainActivity;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    List<NhaHang> nhaHangList;
    List<String> idResList = new ArrayList<>();
    String idRes = "";
    ImageView imgButBack;

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        FragmentRestaurant fragment = new FragmentRestaurant();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        init();
        Bundle bundle = getArguments();
        idRes = idRes + bundle.getString("IdRes", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        nhaHangList = new ArrayList<>();
        databaseReference.child("nhaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable) {
                    NhaHang nhaHang = data.getValue(NhaHang.class);
                    nhaHangList.add(nhaHang);
                    idResList.add(nhaHang.getId());
                }
                AdapterRestaurant adapterRestaurant = new AdapterRestaurant(nhaHangList, mainActivity, idResList, idRes);
                rcvDateRes.setAdapter(adapterRestaurant);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mainActivity, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idRes.equals("admin")) {
                    FragmentEditRestaurant fragment = new FragmentEditRestaurant();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", "");
                    bundle.putString("nameRes", "");
                    bundle.putString("addressRes", "");
                    bundle.putString("open", "");
                    bundle.putString("close", "");
                    bundle.putString("idRes", "");
                    bundle.putStringArrayList("listIDRes", (ArrayList<String>) idResList);
                    fragment.setArguments(bundle);
                    mainActivity.getFragment(fragment);
                }
            }
        });
        imgButBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentHome fragmentHome = new FragmentHome();
                Bundle bundle = new Bundle();
                bundle.putString("IdRes", idRes);
                fragmentHome.setArguments(bundle);
                mainActivity.getFragment(fragmentHome);
            }
        });
        return view;
    }

    public void init() {
        mainActivity = (MainActivity) getActivity();
        butAdd = view.findViewById(R.id.butAddRes);
        rcvDateRes = view.findViewById(R.id.dataRestaurant);
        imgButBack = view.findViewById(R.id.imgButBack);
    }
}
