package com.rikkei.training.appadmin_vutruonggiang.modle;


public class Food_Order{
    String name,id,url,idNhaHang;
    long amount;
    long price;

    public Food_Order(){}

    public Food_Order(long amount,String id,String name,long price,String url,String idNhaHang) {
        this.id=id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.url=url;
        this.idNhaHang=idNhaHang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdNhaHang() {
        return idNhaHang;
    }

    public void setIdNhaHang(String idNhaHang) {
        this.idNhaHang = idNhaHang;
    }
}
