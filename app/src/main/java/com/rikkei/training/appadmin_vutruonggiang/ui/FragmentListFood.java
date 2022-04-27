package com.rikkei.training.appadmin_vutruonggiang.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.adapter.AdapterFood;
import com.rikkei.training.appadmin_vutruonggiang.modle.Food;
import com.rikkei.training.appadmin_vutruonggiang.modle.NhaHang;

import java.util.ArrayList;
import java.util.List;

public class FragmentListFood extends Fragment {

    private View view;
    private ImageView imgBack;
    private TextView tvFastFood;
    private TextView tvDrink;
    private TextView tvRice;
    private AppCompatButton butAdd;
    private RecyclerView rcvDataFood;
    MainActivity mainActivity;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    List<Food> foodList;
    ProcessFood processFood = new ProcessFood();
    List<String> idFoodList = new ArrayList<>();
    String idRes = "";

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        FragmentListFood fragment = new FragmentListFood();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_food, container, false);
        init();
        Bundle bundle = getArguments();
        if (bundle != null) {
            idRes = idRes + bundle.getString("IdRes", "");
        }
        foodList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
        rcvDataFood.setLayoutManager(layoutManager);
        databaseReference.child("food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable) {
                    Food food = data.getValue(Food.class);
                    foodList.add(food);
                    idFoodList.add(food.getId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mainActivity, "Fail Data", Toast.LENGTH_SHORT).show();
            }
        });
        tvFastFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Food> fastFoodList = processFood.doAnNhanh(foodList);
                AdapterFood adapterFood = new AdapterFood(fastFoodList, mainActivity, idFoodList, idRes);
                rcvDataFood.setAdapter(adapterFood);
            }
        });
        tvDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Food> drinkList = processFood.doUong(foodList);
                AdapterFood adapterFood = new AdapterFood(drinkList, mainActivity, idFoodList, idRes);
                rcvDataFood.setAdapter(adapterFood);
            }
        });
        tvRice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Food> comList = processFood.com(foodList);
                AdapterFood adapterFood = new AdapterFood(comList, mainActivity, idFoodList, idRes);
                rcvDataFood.setAdapter(adapterFood);
            }
        });
        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentEditFood fragment = new FragmentEditFood();
                Bundle bundle = new Bundle();
                bundle.putString("url", "");
                bundle.putString("nameFood", "");
                bundle.putString("detailFood", "");
                bundle.putString("id", "");
                bundle.putString("type", "");
                bundle.putString("idRes", "");
                bundle.putString("price", "");
                bundle.putString("IdRes",idRes);
                bundle.putStringArrayList("listIDFood", (ArrayList<String>) idFoodList);
                fragment.setArguments(bundle);
                mainActivity.getFragment(fragment);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentEditRestaurant fragmentHome = new FragmentEditRestaurant();
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
        imgBack = view.findViewById(R.id.imgButArrowBack);
        tvFastFood = view.findViewById(R.id.tvFastFood);
        tvDrink = view.findViewById(R.id.tvDrink);
        tvRice = view.findViewById(R.id.tvRice);
        butAdd = view.findViewById(R.id.butAdd);
        rcvDataFood = view.findViewById(R.id.dataFood);
    }
}
