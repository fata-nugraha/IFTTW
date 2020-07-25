package com.example.myroutines.data;

import com.example.myroutines.app.Constant;

public class Action {
    private int id;
    private String title;
    private String text;
    private int state;

    public Action(int id, String title, String text, int state) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getWifiState() {
        return state;
    }

    public void setStateOn() {
        this.state = Constant.WIFI_ON;
    }
    public void setStateOff() {
        this.state = Constant.WIFI_OFF;
    }
}
