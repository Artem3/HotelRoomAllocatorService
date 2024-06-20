package pl.beusable.roomallocator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.beusable.roomallocator.dto.AllocationRequestDTO;
import pl.beusable.roomallocator.dto.AllocationResponseDTO;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomAllocationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRoomAllocation() {

        AllocationRequestDTO request =
                new AllocationRequestDTO(2, 3, new String[] {"125", "100", "90", "99", "45.1", "60"});
        ResponseEntity<AllocationResponseDTO> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/allocateRooms",
                request,
                AllocationResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Free Premium rooms: 2", response.getBody().initialPremiumRooms());
        assertEquals("Free Economy rooms: 3", response.getBody().initialEconomyRooms());
        assertEquals("Usage Premium: 2 (EUR 225)", response.getBody().usagePremium());
        assertEquals("Usage Economy: 3 (EUR 249)", response.getBody().usageEconomy());
    }
}