package pl.beusable.roomallocator.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.beusable.roomallocator.dto.AllocationRequestDTO;
import pl.beusable.roomallocator.dto.AllocationResponseDTO;
import pl.beusable.roomallocator.service.RoomAllocationService;

@RestController()
@RequestMapping("/api/v1")
public class RoomAllocationController {

    private final RoomAllocationService allocationService;

    public RoomAllocationController(RoomAllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping("/allocateRooms")
    public AllocationResponseDTO calculateRoomAllocation(@RequestBody @Valid AllocationRequestDTO request) {
        return allocationService.allocate(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
