package au.co.tripfare.service;

import au.co.tripfare.entities.Tap;
import au.co.tripfare.entities.Trip;
import au.co.tripfare.repository.FareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.TripStatus;

import java.util.List;

 @Service
public class FareServiceImpl implements FareService {

   @Autowired
    FareRepository fareRepository;

    @Override
    public Trip processTap(Tap tap, List<Trip> incompleteTrips) {
        var unprocessedTrip = incompleteTrips.stream()
                .filter(trip -> trip.getPan().equalsIgnoreCase(tap.getPan()))
                .findFirst().orElse(null);

        if (null != unprocessedTrip) {
            var tripStatus = checkTripStatus(unprocessedTrip, tap);
            unprocessedTrip.setTripStatus(tripStatus);
            var fareValue = calculateFare(unprocessedTrip.getFromStop().trim().toLowerCase(), tap.getStopId().trim().toLowerCase(), tripStatus);
            unprocessedTrip.setChargeAmount(fareValue);
            unprocessedTrip.setToStop(tap.getStopId());
            unprocessedTrip.setEndTime(tap.getTapTime());
            incompleteTrips.remove(unprocessedTrip);
        }
        return unprocessedTrip;
    }


    public Trip addtoIncompleteTrips(Tap tap) {
        return Trip.builder()
                .busId(tap.getBusId())
                .companyId(tap.getCompanyId())
                .pan(tap.getPan())
                .FromStop(tap.getStopId()).startTime(tap.getTapTime())
                .tripStatus(TripStatus.INCOMPLETE)
                .build();


    }


    private TripStatus checkTripStatus(Trip fromStop, Tap toStop) {
        TripStatus status = TripStatus.COMPLETED;
        if (fromStop.getFromStop() == toStop.getStopId()) {
            if (toStop.getTapTime().getTime() - fromStop.getStartTime().getTime() < 600000)
                status = TripStatus.CANCELLED;
        }
        return status;
    }


    private double calculateFare(String fromStop, String toStop, TripStatus tripStatus) {
        double fareValue = 0.0;
        if ((tripStatus.equals(TripStatus.COMPLETED)) && fromStop == toStop) {
            fareValue = fareRepository.findMaxFare().getFareValue();
        } else if ((tripStatus.equals(TripStatus.COMPLETED)) && fromStop != toStop) {
            fareValue = fareRepository. findByFromStopANDToStop(fromStop, toStop).get(0).getFareValue();
        }
        return fareValue;
    }


}