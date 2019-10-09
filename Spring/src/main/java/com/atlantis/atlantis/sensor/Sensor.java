package com.atlantis.atlantis.sensor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;


@Entity
@Table(name="sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String value;
    private String type;
    private String uid;
    // private LocalDateTime updated_date;

    public Sensor() {
    }

    public Sensor(String value, String type, String uid) {
        this.value = value;
        this.type = type;
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getUid() {
        return uid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
