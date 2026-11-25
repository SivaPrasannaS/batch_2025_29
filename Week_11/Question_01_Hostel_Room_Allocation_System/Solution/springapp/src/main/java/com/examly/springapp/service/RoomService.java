package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.RoomRequest;
import com.examly.springapp.model.Room;

public interface RoomService {

    Room createRoom(RoomRequest request);

    List<Room> getAllRooms();

    Room getRoomById(Long id);

    Room allocateBed(Long id);
}
