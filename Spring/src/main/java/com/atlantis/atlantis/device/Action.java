package com.atlantis.atlantis.device;

public class Action {

    private String sensor;
    private String actionEvent;

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getActionEvent() {
        return actionEvent;
    }

    public void setActionEvent(String actionEvent) {
        this.actionEvent = actionEvent;
    }

    public Action(String sensor, String actionEvent) {
        this.sensor = sensor;
        this.actionEvent = actionEvent;
    }

    @Override
    public String toString() {
        return "Action{" +
                "sensor='" + sensor + '\'' +
                ", actionEvent='" + actionEvent + '\'' +
                '}';
    }
}
