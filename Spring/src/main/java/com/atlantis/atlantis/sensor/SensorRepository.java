package com.atlantis.atlantis.sensor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Sensor findByUid(String uid);
}