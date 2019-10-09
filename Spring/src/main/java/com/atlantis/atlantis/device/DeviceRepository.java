package com.atlantis.atlantis.device;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Device findByUid(String uid);
}
