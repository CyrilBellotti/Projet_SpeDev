package com.atlantis.atlantis.device;

public class Stat {

    private Integer value;
    private String date;

    public Stat() {
    }

    public Stat(Integer value, String date) {
        this.value = value;
        this.date = date;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "value=" + value +
                ", date='" + date + '\'' +
                '}';
    }
}
