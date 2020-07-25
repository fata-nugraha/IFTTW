package com.example.myroutines.data;

import java.time.LocalDateTime;

public class Condition {
    private int id;
    private LocalDateTime ldt;

    public Condition(int id, LocalDateTime ldt) {
        this.id = id;
        this.ldt = ldt;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt;
    }
}
