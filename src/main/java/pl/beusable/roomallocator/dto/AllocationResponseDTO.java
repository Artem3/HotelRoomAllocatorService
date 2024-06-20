package pl.beusable.roomallocator.dto;

public record AllocationResponseDTO(
        String initialPremiumRooms,
        String premiumRevenue,
        String initialEconomyRooms,
        String economyRevenue
) {
}
