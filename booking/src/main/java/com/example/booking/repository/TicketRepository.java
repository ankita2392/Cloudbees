package com.example.booking.repository;

import com.example.booking.entity.Passenger;
import com.example.booking.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>  {

    Ticket getById(Long id);

    List<Ticket> getByPassenger_Id(Long passengerId);

    @Query(value = "DELETE FROM ticket WHERE id IN (SELECT t.id FROM ticket t INNER JOIN passenger ps ON t.passenger_id = ps.id " +
            "INNER JOIN train_seat ts ON ts.id = t.train_seat_id " +
            "WHERE ts.train_id = :trainId AND ps.id = :passengerId)", nativeQuery = true)
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteByTrainIdAndPassengerId(@Param("trainId") Long trainId, @Param("passengerId") Long passengerId);
}
