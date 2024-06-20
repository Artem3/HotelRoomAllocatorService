package pl.beusable.roomallocator.service;

public record RoomAllocationData(
        int initialPremiumRooms,
        int initialEconomyRooms,
        int premiumOccupancy,
        int economyOccupancy,
        double premiumRevenue,
        double economyRevenue
) {
}