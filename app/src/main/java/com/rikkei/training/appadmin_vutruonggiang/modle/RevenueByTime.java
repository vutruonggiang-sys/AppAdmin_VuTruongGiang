package com.rikkei.training.appadmin_vutruonggiang.modle;

public class RevenueByTime {
    String id;
    long lunch, nothing, tonight;

    public RevenueByTime() {
    }

    public RevenueByTime(String id, long lunch, long nothing, long tonight) {
        this.id = id;
        this.lunch = lunch;
        this.nothing = nothing;
        this.tonight = tonight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLunch() {
        return lunch;
    }

    public void setLunch(long lunch) {
        this.lunch = lunch;
    }

    public long getNothing() {
        return nothing;
    }

    public void setNothing(long nothing) {
        this.nothing = nothing;
    }

    public long getTonight() {
        return tonight;
    }

    public void setTonight(long tonight) {
        this.tonight = tonight;
    }
}
