package com.example.booking.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_station", nullable = false)
    private String from;

    @Column(name = "to_station", nullable = false)
    private String to;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TrainSeat> trainSeats;
}
