package com.atlantis.atlantis.broker;

import com.atlantis.atlantis.device.Device;
import com.atlantis.atlantis.device.DeviceRepository;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class Receiver {

    private static final Logger logger = getLogger(Receiver.class);

    private final DeviceRepository deviceRepository;

    public Receiver(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @RabbitListener(queues = "helloworld")
    public void receiveMessage(Device device) {
        logger.info("Received <" + device + ">");
        Device actualDevice = this.deviceRepository.findByUid(device.getUid());
        if (actualDevice == null) {
            this.deviceRepository.save(device);
        } else {
            actualDevice.setSensors(device.getSensors());
            this.deviceRepository.save(actualDevice);
        }
    }
}


