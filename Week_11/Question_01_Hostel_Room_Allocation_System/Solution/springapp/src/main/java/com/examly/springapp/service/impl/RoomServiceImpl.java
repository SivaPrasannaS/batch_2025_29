package com.examly.springapp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.RoomRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.exception.RoomFullException;
import com.examly.springapp.model.Room;
import com.examly.springapp.repository.RoomRepository;
import com.examly.springapp.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room createRoom(RoomRequest request) {
        Room room = new Room();
        room.setRoomNumber(request.getRoomNumber());
        room.setBlockName(request.getBlockName());
        room.setCapacity(request.getCapacity());
        room.setOccupants(0);
        Room saved = roomRepository.save(room);
        logger.info("Room created with id {} and number {}", saved.getId(), saved.getRoomNumber());
        return saved;
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        logger.info("Fetched {} rooms", rooms.size());
        return rooms;
    }

    @Override
    public Room getRoomById(Long id) {
        Room room = findRoom(id);
        logger.info("Fetched room with id {}", id);
        return room;
    }

    @Override
    public Room allocateBed(Long id) {
        Room room = findRoom(id);
        if (room.getOccupants() >= room.getCapacity()) {
            throw new RoomFullException("Room " + room.getRoomNumber() + " is full");
        }
        room.setOccupants(room.getOccupants() + 1);
        Room saved = roomRepository.save(room);
        logger.info("Allocated a bed in room {}. Occupants: {}/{}", saved.getRoomNumber(), saved.getOccupants(),
                saved.getCapacity());
        return saved;
    }

    private Room findRoom(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }
}
