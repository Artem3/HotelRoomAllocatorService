package pl.beusable.roomallocator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.beusable.roomallocator.dto.AllocationRequestDTO;
import pl.beusable.roomallocator.dto.AllocationResponseDTO;
import pl.beusable.roomallocator.service.RoomAllocationService;

@WebMvcTest
class RoomAllocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomAllocationService allocationService;


    @Test
    public void whenPostRequestToAllocateRoomsAndValidRoomRequest_thenCorrectResponse() throws Exception {
        String allocationRequestJson =
                "{\"premiumRooms\": 3, \"economyRooms\": 3, \"guestOffers\": [\"100\", \"200\", \"150\"]}";
        AllocationResponseDTO response = new AllocationResponseDTO("", "", "", "");

        when(allocationService.allocate(any(AllocationRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/allocateRooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(allocationRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.initialPremiumRooms").exists())
                .andExpect(jsonPath("$.initialEconomyRooms").exists())
                .andExpect(jsonPath("$.usagePremium").exists())
                .andExpect(jsonPath("$.usageEconomy").exists());
    }
}