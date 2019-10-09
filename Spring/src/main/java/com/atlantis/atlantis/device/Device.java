package com.atlantis.atlantis.device;

import com.atlantis.atlantis.sensor.Sensor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="devices")
public class Device {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String uid;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    private List<Sensor> sensors;

    public Device() {
    }

    public Device(String uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", uid='" + uid +
                ", sensors'" + sensors +
                '}';
    }
}
