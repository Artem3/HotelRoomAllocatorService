package pl.beusable.roomallocator.service;

import org.springframework.stereotype.Service;
import pl.beusable.roomallocator.dto.AllocationRequestDTO;
import pl.beusable.roomallocator.dto.AllocationResponseDTO;

@Service
public class RoomAllocationService {

    public AllocationResponseDTO allocate(AllocationRequestDTO request) {
        return new AllocationResponseDTO(null, null, null, null);
    }
}
