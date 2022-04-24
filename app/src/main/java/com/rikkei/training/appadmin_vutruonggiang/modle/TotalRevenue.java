package com.rikkei.training.appadmin_vutruonggiang.modle;

public class TotalRevenue {
    String id;
    long total;

    public TotalRevenue() {
    }

    public TotalRevenue(String id, long total) {
        this.id = id;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
