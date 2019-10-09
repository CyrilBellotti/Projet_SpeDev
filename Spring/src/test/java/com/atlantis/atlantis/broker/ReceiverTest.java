package com.atlantis.atlantis.broker;

import com.atlantis.atlantis.device.Device;
import com.atlantis.atlantis.device.DeviceRepository;
import com.atlantis.atlantis.sensor.Sensor;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;

class ReceiverTest {

    private final DeviceRepository deviceRepository = mock(DeviceRepository.class);
    private final Receiver receiver = new Receiver(deviceRepository);

    @Test
    void receiveMessage_shouldCreateDevice_whenDoesNotExist() {
        // Given
        Device device = new Device("1");
        Sensor sensor = new Sensor("value", "type", "uid");
        device.setSensors(Collections.singletonList(sensor));
        when(deviceRepository.findByUid(device.getUid())).thenReturn(null);

        // When
        receiver.receiveMessage(device);

        // Then
        verify(deviceRepository).save(device);
    }

    @Test
    void receiveMessage_shouldUpdateDevice_whenExists() {
        // Given
        Device deviceToUpdate = new Device("1");
        Device device = new Device("1");
        Sensor sensor = new Sensor("value", "type", "uid");
        device.setSensors(Collections.singletonList(sensor));
        when(deviceRepository.findByUid(device.getUid())).thenReturn(deviceToUpdate);

        // When
        receiver.receiveMessage(device);

        // Then
        deviceToUpdate.setSensors(Collections.singletonList(sensor));
        verify(deviceRepository).save(deviceToUpdate);
    }
}