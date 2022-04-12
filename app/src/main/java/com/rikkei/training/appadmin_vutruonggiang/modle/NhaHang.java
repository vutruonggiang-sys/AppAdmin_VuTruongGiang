package com.rikkei.training.appadmin_vutruonggiang.modle;

public class NhaHang {
    String id,address,name,url,close,open;
    long rating;
    long v,v1;
    public NhaHang(){
    }

    public NhaHang(String id, String address, String name, String url, String close, String open, long rating,long v,long v1) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.url = url;
        this.close = close;
        this.open = open;
        this.rating = rating;
        this.v=v;
        this.v1=v1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    public long getV1() {
        return v1;
    }

    public void setV1(long v1) {
        this.v1 = v1;
    }
}
