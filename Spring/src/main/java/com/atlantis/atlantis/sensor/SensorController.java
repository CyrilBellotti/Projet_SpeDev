package com.atlantis.atlantis.sensor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sensor")
public class SensorController {

    private final SensorRepository sensorRepository;

    public SensorController(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @GetMapping
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Sensor findById(@PathVariable Long id) {
        return sensorRepository.findById(id).get();
    }
}
