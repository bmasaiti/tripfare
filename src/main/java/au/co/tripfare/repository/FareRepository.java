package au.co.tripfare.repository;


import au.co.tripfare.entities.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepository extends JpaRepository<Fare,Integer> {

    @Query(value = "Select f from Fare where f.fromStop = ?1 and f.toStop =?2")
    Fare findByFromStopANDToStop(String fromStop,String toStop);

    @Query(value = "Select max(fare) from Fare")
    Fare findMaxFare();

    @Query(value = "Select f from Fare f  Where f.fromStop = ?1")
            Fare getMaxFareFromStop(String stopId);
}
