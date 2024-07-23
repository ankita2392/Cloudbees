package com.example.booking.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_seat_id", nullable = false)
    private TrainSeat trainSeat;

    @Column(name = "price_paid", nullable = false)
    private Double pricePaid;
}
