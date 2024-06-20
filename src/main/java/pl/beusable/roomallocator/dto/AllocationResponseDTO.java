package pl.beusable.roomallocator.dto;

public record AllocationResponseDTO(
        String initialPremiumRooms,
        String initialEconomyRooms,
        String usagePremium,
        String usageEconomy
) {
}
