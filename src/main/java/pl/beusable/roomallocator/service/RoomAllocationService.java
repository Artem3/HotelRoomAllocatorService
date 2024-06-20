package pl.beusable.roomallocator.service;

import static java.util.Arrays.sort;
import static java.util.Arrays.stream;
import static java.util.Collections.reverseOrder;

import java.text.DecimalFormat;
import org.springframework.stereotype.Service;
import pl.beusable.roomallocator.dto.AllocationRequestDTO;
import pl.beusable.roomallocator.dto.AllocationResponseDTO;

@Service
public class RoomAllocationService {

    public AllocationResponseDTO allocate(AllocationRequestDTO request) {

        Double[] offers = parseStringArrayToDouble(request.guestOffers());
        sort(offers, reverseOrder());
        RoomAllocationData allocations = allocateRooms(request.premiumRooms(), request.economyRooms(), offers);

        return formatAndCreateResult(allocations);
    }

    private RoomAllocationData allocateRooms(int premiumRooms, int economyRooms, Double[] guestOffers) {
        int initialPremiumRooms = premiumRooms;
        int initialEconomyRooms = economyRooms;
        double premiumRevenue = 0.0;
        double economyRevenue = 0.0;
        double highestEconomyOffer = 0.0;

        for (int i = 0; i < guestOffers.length; i++) {
            double offer = guestOffers[i];
            if (offer >= 100 && premiumRooms > 0) {
                premiumRooms--;
                premiumRevenue += offer;
            } else if (offer < 100 && economyRooms > 0) {
                economyRooms--;
                economyRevenue += offer;
                highestEconomyOffer = Math.max(highestEconomyOffer, offer);
            } else if (offer < 100 && premiumRooms > 0 && economyRooms == 0) {
                premiumRooms--;
                premiumRevenue += highestEconomyOffer;
                economyRevenue -= highestEconomyOffer;
                highestEconomyOffer = offer;
                economyRevenue += offer;
                if (i == guestOffers.length - 1) {
                    economyRevenue = 0;
                    premiumRevenue += offer;
                }
            }
        }
        int premiumOccupancy = initialPremiumRooms - premiumRooms;
        int economyOccupancy = initialEconomyRooms - economyRooms;

        return new RoomAllocationData(
                initialPremiumRooms,
                initialEconomyRooms,
                premiumOccupancy,
                economyOccupancy,
                premiumRevenue,
                economyRevenue
        );
    }

    private Double[] parseStringArrayToDouble(String[] guestOffers) {
        return stream(guestOffers)
                .map(s -> {
                    try {
                        double value = Double.parseDouble(s);
                        if (value <= 0) {
                            throw new IllegalArgumentException("Value must be greater than zero: " + s);
                        }
                        return value;
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                                "Input array contains non-parsable string: " + s, e);
                    }
                })
                .toArray(Double[]::new);
    }

    private AllocationResponseDTO formatAndCreateResult(RoomAllocationData data) {
        String initialPremiumRooms = "Free Premium rooms: " + data.initialPremiumRooms();
        String initialEconomyRooms = "Free Economy rooms: " + data.initialEconomyRooms();

        DecimalFormat df = new DecimalFormat("0.##");
        String premiumRevenueStr = "EUR " + df.format(Math.round(data.premiumRevenue() * 1000) / 1000.0);
        String economyRevenueStr = "EUR " + df.format(Math.round(data.economyRevenue() * 1000) / 1000.0);

        String usagePremium = "Usage Premium: " + data.premiumOccupancy() + " (" + premiumRevenueStr + ")";
        String usageEconomy = "Usage Economy: " + data.economyOccupancy() + " (" + economyRevenueStr + ")";

        return new AllocationResponseDTO(initialPremiumRooms, initialEconomyRooms, usagePremium, usageEconomy);
    }
}
