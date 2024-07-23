package com.example.booking.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerView {
    private Long passengerId;
    private String firstName;
    private String lastName;
    private String email;
}
