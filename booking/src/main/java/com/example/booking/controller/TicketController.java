package com.example.booking.controller;

import com.example.booking.pojo.BookingRequest;
import com.example.booking.pojo.BookingResponse;
import com.example.booking.pojo.ModifySeatRequest;
import com.example.booking.pojo.TrainSectionInfoResponse;
import com.example.booking.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/bookTicket")
    public BookingResponse bookTicket(@RequestBody @Valid BookingRequest bookingRequest) {
        return ticketService.bookTicket(bookingRequest);
    }

    @GetMapping("/receipt/{passengerId}")
    public List<BookingResponse> getReceipt(@PathVariable Long passengerId) {
        return ticketService.getReceipt(passengerId);
    }


    @GetMapping("/section")
    public List<TrainSectionInfoResponse> gePassengersBySection(@RequestParam(required = true) Long trainId,
                                                                @RequestParam(required = false) String section) {
        return ticketService.getPassengersBySection(trainId, section);
    }


    @DeleteMapping("/remove")
    public void removePassenger(@RequestParam(required = true) Long trainId,
                                @RequestParam(required = true) Long passengerId) {
        ticketService.removePassenger(trainId, passengerId);
    }


    @PutMapping("/modifySeat")
    public void modifySeat(@RequestBody @Valid ModifySeatRequest modifySeatRequest) {
        ticketService.modifySeat(modifySeatRequest);
    }
}
