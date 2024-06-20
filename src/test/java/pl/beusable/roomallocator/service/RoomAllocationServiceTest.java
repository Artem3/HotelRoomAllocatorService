package pl.beusable.roomallocator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.beusable.roomallocator.dto.AllocationRequestDTO;
import pl.beusable.roomallocator.dto.AllocationResponseDTO;

@SpringBootTest
class RoomAllocationServiceTest {

    @Autowired
    private RoomAllocationService service;

    private static Stream<Arguments> provideTestCases() {
        final String[] GUEST_OFFERS = {"23", "45", "155", "374", "22", "99.99", "100", "101", "115", "209"};
        return Stream.of(
                Arguments.of(
                        new AllocationRequestDTO(3, 3, GUEST_OFFERS),
                        new AllocationResponseDTO("Free Premium rooms: 3", "Free Economy rooms: 3",
                                "Usage Premium: 3 (EUR 738)", "Usage Economy: 3 (EUR 167.99)")
                ),
                Arguments.of(
                        new AllocationRequestDTO(7, 5, GUEST_OFFERS),
                        new AllocationResponseDTO("Free Premium rooms: 7", "Free Economy rooms: 5",
                                "Usage Premium: 6 (EUR 1054)", "Usage Economy: 4 (EUR 189.99)")
                )
                ,
                Arguments.of(
                        new AllocationRequestDTO(2, 7, GUEST_OFFERS),
                        new AllocationResponseDTO("Free Premium rooms: 2", "Free Economy rooms: 7",
                                "Usage Premium: 2 (EUR 583)", "Usage Economy: 4 (EUR 189.99)")
                ),
                Arguments.of(
                        new AllocationRequestDTO(7, 1, GUEST_OFFERS),
                        new AllocationResponseDTO("Free Premium rooms: 7", "Free Economy rooms: 1",
                                "Usage Premium: 7 (EUR 1153.99)", "Usage Economy: 1 (EUR 45)")
                ),
                //---additional test---
                Arguments.of(
                        new AllocationRequestDTO(10, 0, GUEST_OFFERS),
                        new AllocationResponseDTO("Free Premium rooms: 10", "Free Economy rooms: 0",
                                "Usage Premium: 10 (EUR 1243.99)", "Usage Economy: 0 (EUR 0)")
                ),
                Arguments.of(
                        new AllocationRequestDTO(0, 10, GUEST_OFFERS),
                        new AllocationResponseDTO("Free Premium rooms: 0", "Free Economy rooms: 10",
                                "Usage Premium: 0 (EUR 0)", "Usage Economy: 4 (EUR 189.99)")
                )
        );
    }

    private static Stream<Arguments> exceptionTestCases() {
        return Stream.of(
                Arguments.of(
                        new AllocationRequestDTO(2, 2, new String[] {"150", "abc", "100"}),
                        "Input array contains non-parsable string: abc"
                ),
                Arguments.of(
                        new AllocationRequestDTO(1, 1, new String[] {"200", "-50"}),
                        "Value must be greater than zero: -50"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void testAllocate(AllocationRequestDTO request, AllocationResponseDTO expected) {
        AllocationResponseDTO result = service.allocate(request);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("exceptionTestCases")
    void testAllocateExceptions(AllocationRequestDTO request, String expectedMessage) {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> service.allocate(request),
                "Expected allocate() to throw IllegalArgumentException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains(expectedMessage),
                "Expected message to contain: [" + expectedMessage + "] but was: [" + thrown.getMessage() + "]");
    }
}