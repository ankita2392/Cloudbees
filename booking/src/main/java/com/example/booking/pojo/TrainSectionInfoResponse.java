package com.example.booking.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainSectionInfoResponse {

    private PassengerView passenger;

    private Integer seatNumber;

    private String section;
}
