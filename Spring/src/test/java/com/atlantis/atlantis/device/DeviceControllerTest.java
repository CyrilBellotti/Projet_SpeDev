package com.atlantis.atlantis.device;

import com.atlantis.atlantis.Api;
import com.atlantis.atlantis.sensor.Sensor;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeviceControllerTest {

    private final DeviceRepository deviceRepository = mock(DeviceRepository.class);
    private final Api api = mock(Api.class);
    private final DeviceApi deviceApi = mock(DeviceApi.class);
    private final DeviceController deviceController = new DeviceController(deviceRepository, api, deviceApi);

    @Test
    void findAll_shouldReturnAListOfDevice() {
        // Given
        List<Device> devices = Collections.singletonList(new Device("1"));
        when(deviceRepository.findAll()).thenReturn(devices);

        // When
        List<Device> result = deviceController.findAll();

        // Then
        assertEquals(devices, result);
    }

    @Test
    void findById_shouldReturnADevice() {
        // Given
        Long id = 1L;
        Device device = new Device("1");
        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

        // When
        Device result = deviceController.findById(id);

        // Then
        assertEquals(device, result);
    }

    @Test
    void findById_shouldThrowDeviceNotFoundException_shouldDoesNotExist() {
        // Given
        Long id = 1L;

        // Then
        assertThrows(DeviceNotFoundException.class, () ->

                // When
                deviceController.findById(id)
        );
    }

    @Test
    void findByUid_shouldReturnADevice() {
        // Given
        Device device = new Device("1");
        when(deviceRepository.findByUid(device.getUid())).thenReturn(device);

        // When
        Device result = deviceController.findByUid(device.getUid());

        // Then
        assertEquals(device, result);
    }

    @Test
    void getStats_shouldReturnAListOfStat() {
        // Given
        String uid = "uid";
        Integer duree = 1;
        String calculType = "calculType";
        List<Stat> stats = Collections.singletonList(new Stat());
        when(api.getValues(uid, duree, calculType)).thenReturn(stats);

        // When
        List<Stat> result = deviceController.getStats(uid, duree, calculType);

        // Then
        assertEquals(stats, result);
    }

    @Test
    void updateMetric_shouldCreateDevice_whenDoesNotExist() {
        // Given
        Device device = new Device("1");
        Sensor sensor = new Sensor("value", "type", "uid");
        device.setSensors(Collections.singletonList(sensor));
        when(deviceRepository.findByUid(device.getUid())).thenReturn(null);

        // When
        deviceController.updateMetric(device);

        // Then
        verify(deviceRepository).save(device);
    }

    @Test
    void updateMetric_shouldUpdateDevice_whenExists() {
        // Given
        Device deviceToUpdate = new Device("1");
        Device device = new Device("1");
        Sensor sensor = new Sensor("value", "type", "uid");
        device.setSensors(Collections.singletonList(sensor));
        when(deviceRepository.findByUid(device.getUid())).thenReturn(deviceToUpdate);

        // When
        deviceController.updateMetric(device);

        // Then
        deviceToUpdate.setSensors(Collections.singletonList(sensor));
        verify(deviceRepository).save(deviceToUpdate);
    }
}