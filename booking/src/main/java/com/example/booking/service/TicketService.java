package com.example.booking.service;

import com.example.booking.entity.*;
import com.example.booking.exception.EntityNotFoundException;
import com.example.booking.pojo.*;
import com.example.booking.repository.PassengerRepository;
import com.example.booking.repository.TicketRepository;
import com.example.booking.repository.TrainRepository;
import com.example.booking.repository.TrainSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class TicketService {

    private final PassengerRepository passengerRepository;
    private final TrainRepository trainRepository;
    private final TrainSeatRepository trainSeatRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(PassengerRepository passengerRepository,
                         TrainRepository trainRepository,
                         TrainSeatRepository trainSeatRepository,
                         TicketRepository ticketRepository) {
        this.passengerRepository = passengerRepository;
        this.trainRepository = trainRepository;
        this.trainSeatRepository = trainSeatRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public BookingResponse bookTicket(BookingRequest bookingRequest) {
        Passenger passenger = passengerRepository.getByEmail(bookingRequest.getEmail());
        if (passenger == null)
            throw new EntityNotFoundException("No passenger found with given email");

        Train train = trainRepository.getByFromAndTo(bookingRequest.getFrom(), bookingRequest.getTo())
                .stream().findFirst().orElse(null);
        if (train == null)
            throw new EntityNotFoundException("No train found between given from and to");

        TrainSeat trainSeat = findAvailableSeat(train);
        Ticket ticket = new Ticket();
        ticket.setTrainSeat(trainSeat);
        ticket.setPassenger(passenger);
        ticket.setPricePaid(bookingRequest.getPricePaid());
        ticketRepository.save(ticket);

        return BookingResponse.builder()
                .ticketId(ticket.getId())
                .pricePaid(bookingRequest.getPricePaid())
                .from(bookingRequest.getFrom())
                .to(bookingRequest.getTo())
                .trainId(train.getId())
                .seatNumber(trainSeat.getSeatNumber())
                .section(trainSeat.getSection())
                .passenger(PassengerView.builder()
                        .passengerId(passenger.getId())
                        .email(passenger.getEmail())
                        .firstName(passenger.getFirstName())
                        .lastName(passenger.getLastName())
                        .build())
                .build();
    }

    @Transactional
    public List<BookingResponse> getReceipt(Long passengerId) {
       List<Ticket> tickets = ticketRepository.getByPassenger_Id(passengerId);

       return tickets.stream().map(this::buildBookingResponse).toList();
    }

    @Transactional
    public List<TrainSectionInfoResponse> getPassengersBySection(Long trainId, String section) {
        List<TrainSeat> trainSeatList = trainSeatRepository.getByTrainIdAndSection(trainId, section);
        List<Passenger> passengerList = passengerRepository.getByTrainSeatIds(trainSeatList.stream().map(TrainSeat::getId).toList());

        return passengerList.stream().map(passenger -> this.buildTrainSectionInfoResponse(trainId, section, passenger)).toList();
    }


    @Transactional
    public void removePassenger(Long trainId, Long passengerId) {
        ticketRepository.deleteByTrainIdAndPassengerId(trainId, passengerId);
    }

    @Transactional
    public void modifySeat(ModifySeatRequest modifySeatRequest) {
        Ticket ticket = ticketRepository.getById(modifySeatRequest.getTicketId());
        List<TrainSeat> emptyTrainSeats = trainSeatRepository.getEmptySeats(ticket.getTrainSeat().getTrain().getId());


        for (TrainSeat trainSeat: emptyTrainSeats) {
            if (trainSeat.getSeatNumber().equals(modifySeatRequest.getNewSeatNumber())) {
                ticket.setTrainSeat(trainSeat);
                ticketRepository.save(ticket);
                return;
            }
        }

        throw new EntityNotFoundException("Requested seat is not empty");
    }

    private TrainSeat findAvailableSeat(Train train) {
        List<TrainSeat> emptyTrainSeats = trainSeatRepository.getEmptySeats(train.getId());
        if (CollectionUtils.isEmpty(emptyTrainSeats))
            throw new EntityNotFoundException("No seat available in the given train");

        return emptyTrainSeats.get(0);
    }

    private BookingResponse buildBookingResponse(Ticket ticket) {
        return BookingResponse.builder()
                .ticketId(ticket.getId())
                .pricePaid(ticket.getPricePaid())
                .trainId(ticket.getTrainSeat().getTrain().getId())
                .seatNumber(ticket.getTrainSeat().getSeatNumber())
                .section(ticket.getTrainSeat().getSection())
                .from(ticket.getTrainSeat().getTrain().getFrom())
                .to(ticket.getTrainSeat().getTrain().getTo())
                .passenger(PassengerView.builder()
                        .passengerId(ticket.getPassenger().getId())
                        .email(ticket.getPassenger().getEmail())
                        .firstName(ticket.getPassenger().getFirstName())
                        .lastName(ticket.getPassenger().getLastName())
                        .build())
                .build();
    }

    private TrainSectionInfoResponse buildTrainSectionInfoResponse(Long trainId, String section, Passenger passenger) {
        return TrainSectionInfoResponse.builder()
                .seatNumber(passenger.getTickets().stream()
                        .filter(ticket -> ticket.getTrainSeat().getTrain().getId().equals(trainId))
                        .findFirst().map(ticket -> ticket.getTrainSeat().getSeatNumber()).orElse(null))
                .section(section)
                .passenger(PassengerView.builder()
                        .passengerId(passenger.getId())
                        .email(passenger.getEmail())
                        .firstName(passenger.getFirstName())
                        .lastName(passenger.getLastName())
                        .build())
                .build();
    }
}
