package com.atlantis.atlantis;

import com.atlantis.atlantis.device.Stat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "apicore", url = "http://localhost:5000/")
public interface Api {

    @GetMapping(value = "stats/sensor/{uid}/duration/{duree}/calculType/{calculType}")
    List<Stat> getValues(@PathVariable String uid, @PathVariable Integer duree, @PathVariable String calculType);

    @RequestMapping(method = RequestMethod.GET, value = "/api/stats/toto")
    String getVal();
}