package com.example.booking.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String from;

    @NotBlank
    private String to;

    @NotNull
    private Double pricePaid;
}
