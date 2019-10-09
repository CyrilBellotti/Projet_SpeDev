package com.atlantis.atlantis.sensor;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorControllerTest {

    private final SensorRepository sensorRepository = mock(SensorRepository.class);
    private final SensorController sensorController = new SensorController(sensorRepository);

    @Test
    void findAll_shouldReturnAListOfSensor() {
        // Given
        List<Sensor> sensors = Collections.singletonList(new Sensor("value", "type", "uid"));
        when(sensorRepository.findAll()).thenReturn(sensors);

        // When
        List<Sensor> result = sensorController.findAll();

        // Then
        assertEquals(sensors, result);
    }

    @Test
    void findById_shouldReturnASensor() {
        // Given
        Long id = 1L;
        Sensor sensor = new Sensor("value", "type", "uid");
        when(sensorRepository.findById(id)).thenReturn(Optional.of(sensor));

        // When
        Sensor result = sensorController.findById(id);

        // Then
        assertEquals(sensor, result);
    }
}