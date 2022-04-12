package com.rikkei.training.appadmin_vutruonggiang.modle;


import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {
    public String id, url, name, detail, nhaHang, type;
    public float price, review;

    public Food(String id, String url, String name, String detail, String idNhaHang, String type, float price, float review) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.detail = detail;
        this.nhaHang = idNhaHang;
        this.type = type;
        this.price = price;
        this.review = review;
    }

    public Food(String url, String name, String detail, String type, float price, float review) {
        this.url = url;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.price = price;
        this.review = review;
    }

    public Food() {
    }

    protected Food(Parcel in) {
        id = in.readString();
        url = in.readString();
        name = in.readString();
        detail = in.readString();
        nhaHang = in.readString();
        type = in.readString();
        price = in.readFloat();
        review = in.readFloat();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIdNhaHang() {
        return nhaHang;
    }

    public void setIdNhaHang(String idNhaHang) {
        this.nhaHang = idNhaHang;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(detail);
        dest.writeString(nhaHang);
        dest.writeString(type);
        dest.writeFloat(price);
        dest.writeFloat(review);
    }
}
