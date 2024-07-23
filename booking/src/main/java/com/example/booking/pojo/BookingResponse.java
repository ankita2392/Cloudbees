package com.example.booking.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long ticketId;
    private Integer seatNumber;
    private String section;
    private Long trainId;
    private String from;
    private String to;
    private Double pricePaid;
    private PassengerView passenger;

}
