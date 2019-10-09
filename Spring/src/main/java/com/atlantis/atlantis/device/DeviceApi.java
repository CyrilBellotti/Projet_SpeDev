package com.atlantis.atlantis.device;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "deviceApi", url = "http://localhost:5001/")
public interface DeviceApi {

    @PostMapping(path = "/action")
    String runActionOnDevice(@RequestBody Action action);

    @RequestMapping(method = RequestMethod.GET, value = "/api/stats/toto")
    String getVal();

}
