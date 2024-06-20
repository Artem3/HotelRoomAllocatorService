package pl.beusable.roomallocator.dto;

public record AllocationRequestDTO(
        int premiumRooms,
        int economyRooms,
        String[] guestOffers
) {
}
