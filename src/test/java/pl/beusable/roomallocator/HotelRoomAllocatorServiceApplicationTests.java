package pl.beusable.roomallocator;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.beusable.roomallocator.controller.RoomAllocationController;
import pl.beusable.roomallocator.service.RoomAllocationService;

@SpringBootTest
class HotelRoomAllocatorServiceApplicationTests {

    @Autowired
    private RoomAllocationController controller;

    @Autowired
    private RoomAllocationService service;

    @Test
    void contextLoads() {
        assertNotNull(controller);
        assertNotNull(service);
    }
}
