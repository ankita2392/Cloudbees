package com.example.booking.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModifySeatRequest {

    @NotNull
    private Long ticketId;

    @NotNull
    private Integer newSeatNumber;
}
