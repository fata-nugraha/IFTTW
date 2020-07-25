package com.example.myroutines.data;

import androidx.annotation.NonNull;

public class Routine {
    private int id;
    private int status;
    private Condition cond;
    private Action action;


    public Routine(int id, int status, Condition cond, Action action) {
        this.id = id;
        this.status = status;
        this.cond = cond;
        this.action = action;
    }


    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Condition getCond() {
        return cond;
    }

    public Action getAction() {
        return action;
    }

    @NonNull
    @Override
    public String toString() {

        return super.toString();
    }
}
