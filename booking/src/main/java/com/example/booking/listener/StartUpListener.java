package com.example.booking.listener;

import com.example.booking.entity.Passenger;
import com.example.booking.entity.Train;
import com.example.booking.entity.TrainSeat;
import com.example.booking.repository.PassengerRepository;
import com.example.booking.repository.TicketRepository;
import com.example.booking.repository.TrainRepository;
import com.example.booking.repository.TrainSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartUpListener {

    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private TrainSeatRepository trainSeatRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Bob");
        passenger.setLastName("Last Name");
        passenger.setEmail("bob.lastname@example.com");
        passengerRepository.save(passenger);


        Train train = new Train();
        train.setFrom("London");
        train.setTo("France");
        trainRepository.save(train);

        List<TrainSeat> trainSeatList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            TrainSeat trainSeat = new TrainSeat();
            trainSeat.setSeatNumber(i+1);
            trainSeat.setSection(i < 25 ? "A" : "B");
            trainSeat.setTrain(train);

            trainSeatList.add(trainSeat);
        }
        trainSeatRepository.saveAll(trainSeatList);
    }
}