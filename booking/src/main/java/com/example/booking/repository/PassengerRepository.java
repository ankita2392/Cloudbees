package com.example.booking.repository;

import com.example.booking.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long>  {

    Passenger getByEmail(String email);

    @Query(value = "SELECT ps.* FROM passenger ps INNER JOIN ticket t ON t.passenger_id = ps.id " +
            "INNER JOIN train_seat ts ON ts.id = t.train_seat_id " +
            "WHERE ts.id in (:trainSeatIds)", nativeQuery = true)
    List<Passenger> getByTrainSeatIds(@Param("trainSeatIds") List<Long> trainSeatIds);
}
