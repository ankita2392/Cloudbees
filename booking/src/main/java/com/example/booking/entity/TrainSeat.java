package com.example.booking.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class TrainSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "section", nullable = false)
    private String section;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;


}
