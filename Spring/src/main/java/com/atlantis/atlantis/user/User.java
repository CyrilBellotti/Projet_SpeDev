package com.atlantis.atlantis.user;

import com.atlantis.atlantis.device.Device;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String name;
    private String mail;

    public User() { }

    public User(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }

    public List<Device> getDevice() {
        return device;
    }

    public void setDevice(List<Device> device) {
        this.device = device;
    }

    @ManyToMany(fetch = EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "users_devices",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "devices_id")
    )
    private List<Device> device;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name=" + name +
                ", mail=" + mail +
                '}';
    }
}
