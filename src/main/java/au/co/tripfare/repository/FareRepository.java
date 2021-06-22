package au.co.tripfare.repository;


import au.co.tripfare.entities.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepository extends JpaRepository<Fare, Integer> {

    // @Query(nativeQuery= true, value = "Select f from Fare f where f.fromstop = ?1 and f.tostop =?2")
    Fare findFirstByFromStopAndToStop(String fromstop, String tostop);

    @Query(value = "Select max(f.fareValue) from Fare f")
    Double findByMaxFareValue();

    @Query(value = "select max(f.fareValue) from Fare f where fromstop = ?1 ")
    Double findByMaxFareValueFromStop(String stopId);
}
