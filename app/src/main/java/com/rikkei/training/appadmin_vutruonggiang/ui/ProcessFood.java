package com.rikkei.training.appadmin_vutruonggiang.ui;

import com.rikkei.training.appadmin_vutruonggiang.modle.Food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProcessFood {
    public ProcessFood() {
    }

    public List<Food> doAnNhanh(List<Food> foodList){
        List<Food> foodFast=new ArrayList<>();
        for(int i=0;i<foodList.size();i++){
            if(foodList.get(i).getType().equals("doannhanh")){
                foodFast.add(foodList.get(i));
            }
        }
        return foodFast;
    }
    public List<Food> doUong(List<Food> foodList){
        List<Food> foodDrink=new ArrayList<>();
        for(int i=0;i<foodList.size();i++){
            if(foodList.get(i).getType().equals("douong")){
                foodDrink.add(foodList.get(i));
            }
        }
        return foodDrink;
    }
    public List<Food> com(List<Food> foodList){
        List<Food> foodPrice=new ArrayList<>();
        for(int i=0;i<foodList.size();i++){
            if(foodList.get(i).getType().equals("com")){
                foodPrice.add(foodList.get(i));
            }
        }
        return foodPrice;
    }
}
