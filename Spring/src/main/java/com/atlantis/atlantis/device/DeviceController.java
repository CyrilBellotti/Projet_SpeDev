package com.atlantis.atlantis.device;

import com.atlantis.atlantis.Api;
import com.atlantis.atlantis.sensor.Sensor;
import com.atlantis.atlantis.sensor.SensorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("devices")
public class DeviceController {

    private final DeviceRepository deviceRepository;
    private final Api api;
    private final DeviceApi deviceApi;

    public DeviceController(DeviceRepository deviceRepository, Api api, DeviceApi deviceApi) {
        this.deviceRepository = deviceRepository;
        this.api = api;
        this.deviceApi = deviceApi;
    }

    @CrossOrigin
    @GetMapping
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/id/{id}")
    public Device findById(@PathVariable Long id) {
        if (deviceRepository.findById(id).isPresent()) {
            return deviceRepository.findById(id).get();
        } else {
            throw new DeviceNotFoundException("Device with id " + id + " not found.");
        }
    }

    @CrossOrigin
    @GetMapping("/uid/{uid}")
    public Device findByUid(@PathVariable String uid) {
        return deviceRepository.findByUid(uid);
    }

    @CrossOrigin
    @PostMapping(path = "/action")
    public void runActionOnDevice(@RequestBody Action action) {
        deviceApi.runActionOnDevice(action);
    }

    @CrossOrigin
    @GetMapping(path = "/stat/sensor/{uid}/duree/{duree}/typeCalcul/{typeCalcul}")
    public void getStats(@PathVariable String uid, @PathVariable String typeCalcul, @PathVariable Double duree) {
        System.out.println("uid " + uid + " | typeCalcul : " + typeCalcul + " | duree : " + duree);
        // return [{value: number, date: Date}, {....}, {...}]
    }

    @CrossOrigin
    @GetMapping(path = "/sensor/{uid}/duration/{duree}/calculType/{calculType}")
    public List<Stat> getStats(@PathVariable String uid, @PathVariable Integer duree, @PathVariable String calculType) {
        return api.getValues(uid, duree, calculType);
    }

    @CrossOrigin
    @GetMapping(path = "/sensor/toto")
    public String getToto(String toto) {
        return api.getVal();
    }

    @CrossOrigin
    @PostMapping(path = "/updateMetric")
    public void updateMetric(@RequestBody Device device) {
        Device actualDevice = this.deviceRepository.findByUid(device.getUid());
        if (actualDevice == null) {
            this.deviceRepository.save(device);
        } else {
            actualDevice.setSensors(device.getSensors());
            this.deviceRepository.save(actualDevice);
        }
    }
}
