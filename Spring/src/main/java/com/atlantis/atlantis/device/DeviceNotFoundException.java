package com.atlantis.atlantis.device;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class DeviceNotFoundException extends RuntimeException {
    DeviceNotFoundException(String message) {
        super(message);
    }
}
