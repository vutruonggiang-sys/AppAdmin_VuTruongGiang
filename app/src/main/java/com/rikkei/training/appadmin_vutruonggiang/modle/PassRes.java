package com.rikkei.training.appadmin_vutruonggiang.modle;

public class PassRes {
    String id;
    long pass;

    public PassRes(String id, long pass) {
        this.id = id;
        this.pass = pass;
    }

    public PassRes() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPass() {
        return pass;
    }

    public void setPass(long pass) {
        this.pass = pass;
    }
}
