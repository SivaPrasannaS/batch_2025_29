package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.LockerRequest;
import com.examly.springapp.model.Locker;

public interface LockerService {

    Locker allocateLocker(LockerRequest request);

    Locker getLockerDetails(String lockerNumber);

    List<Locker> getAllLockers();

    Locker openLocker(String lockerNumber);

    Locker closeLocker(String lockerNumber);
}
