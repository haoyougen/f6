package com.f6.vo;

import java.util.Date;

public class UserRoleVO extends UserRoleVOKey {
    private String mem;

    private String urCreate;

    private Date dateCreate;

    private String urAlter;

    private Date dateAlter;

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem == null ? null : mem.trim();
    }

    public String getUrCreate() {
        return urCreate;
    }

    public void setUrCreate(String urCreate) {
        this.urCreate = urCreate == null ? null : urCreate.trim();
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getUrAlter() {
        return urAlter;
    }

    public void setUrAlter(String urAlter) {
        this.urAlter = urAlter == null ? null : urAlter.trim();
    }

    public Date getDateAlter() {
        return dateAlter;
    }

    public void setDateAlter(Date dateAlter) {
        this.dateAlter = dateAlter;
    }
}