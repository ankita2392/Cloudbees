package com.example.booking.repository;

import com.example.booking.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long>  {

    List<Train> getByFromAndTo(String from, String to);

}
