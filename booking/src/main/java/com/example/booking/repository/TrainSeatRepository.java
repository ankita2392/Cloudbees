package com.example.booking.repository;

import com.example.booking.entity.TrainSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainSeatRepository extends JpaRepository<TrainSeat, Long>  {

    @Query(value = "SELECT ts.* FROM train_seat ts LEFT JOIN ticket t ON ts.id = t.train_seat_id " +
            "WHERE ts.train_id = :trainId AND t.id IS NULL ORDER BY ts.id", nativeQuery = true)
    List<TrainSeat> getEmptySeats(@Param("trainId") Long trainId);

    List<TrainSeat> getByTrainIdAndSection(Long trainId, String section);
}
