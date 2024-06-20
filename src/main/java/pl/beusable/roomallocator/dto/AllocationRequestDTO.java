package pl.beusable.roomallocator.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AllocationRequestDTO(

        @NotNull(message = "The number of premium rooms must be provided")
        @Min(value = 0, message = "The number of premium rooms must be zero or greater")
        int premiumRooms,

        @NotNull(message = "The number of economy rooms must be provided")
        @Min(value = 0, message = "The number of economy rooms must be zero or greater")
        int economyRooms,

        @NotEmpty(message = "The guest offers must not be empty")
        String[] guestOffers
) {
}
