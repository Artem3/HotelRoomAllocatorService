# Room Occupancy Service API

## Overview

Room Occupancy Service is a simple demo tool designed to optimize room allocation for hotels. It allows hotels to
maximize both their revenue and customer satisfaction by intelligently allocating rooms based on the price guests are
willing to pay.

## Features

- **Category-based Allocation**: Supports two categories of rooms: Premium and Economy.
- **Smart Upgrades**: Automatically upgrades guests to Premium rooms when Economy rooms are full, and Premium rooms are
  available, prioritizing higher-paying guests below EUR 100.

## Technologies used

- Java 17
- Maven
- Spring Boot 3.3.0

## Setup and Running

1. **Build the project:**

```mvn clean install```

2. **Run the application:**

```mvn spring-boot:run```

## Usage

Request to calculate the allocation of guests to rooms.

   ```bash
curl --location 'localhost:8080/api/v1/allocateRooms' \
--header 'Content-Type: application/json' \
--data '{
  "premiumRooms": 7,
  "economyRooms": 5,
  "guestOffers": ["23", "45", "155", "374", "22", "99.99", "100", "101", "115", "209"]
}'
   ```

Microservice response with inputs and outputs as well as revenue amounts.

Response:

   ```json
{
  "initialPremiumRooms": "Free Premium rooms: 7",
  "initialEconomyRooms": "Free Economy rooms: 5",
  "usagePremium": "Usage Premium: 6 (EUR 1054)",
  "usageEconomy": "Usage Economy: 4 (EUR 189.99)"
}
   ```

## Description of the key algorithm

The `allocateRooms` method optimizes the distribution of hotel rooms between premium and economy categories based on guest
payment offers. Here's how it functions:

  **Initialization:** Sets up counters for available premium and economy rooms, along with initial revenue for both categories.

  **Processing Offers:**
- **Premium Allocation:** Guests offering €100 or more are allocated to premium rooms if available, and their offer is added to the premium revenue.
- **Economy Allocation:** Guests offering less than €100 are allocated to economy rooms if available, and their offer is added to the economy revenue.
- **Upgrading to Premium:** When no economy rooms are left but premium rooms are still available, guests offering less than €100 may be upgraded to premium rooms. The highest previous economy offer is shifted to premium revenue.


  **Output:** Returns a `RoomAllocationData` object containing initial and occupied room counts, and total revenues for both room categories.

## Notes

The document **CodingChallengeBE_v2.pdf** attached to the initial email contains the requirements and some test cases. It seems to me that `Test 4` contains an error in the output section.
If we follow the requirements for software behaviour, then the expected results should be:
```text
Usage Premium: 7 (EUR 1153.99)
Usage Economy: 1 (EUR 45)
```
I implemented the algorithm based on this assumption. If it is not correct, a small adjustment of the code will be required. 

