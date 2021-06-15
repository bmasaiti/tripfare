package au.co.tripfare.repository;


import au.co.tripfare.entities.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FareRepository extends JpaRepository<Fare, Integer> {
 //Select f from Fare f where f.fromStop ='stop1'  and f.toStop = 'stop2';

   @Query(nativeQuery= true, value = "Select f from Fare f where f.fromstop = :fromstop and f.tostop = :tostop")
    List<Fare> findByFromStopANDToStop(String fromstop, String tostop);

    @Query(nativeQuery = true, value = "Select max(farevalue) from Fare")
    Fare findMaxFare();

    @Query(nativeQuery = true, value = "Select max(farevalue) from Fare where fromstop = :stopId")
    Fare getMaxFareFromStop(String stopId);
}
