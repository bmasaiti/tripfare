package au.co.tripfare.service;

import au.co.tripfare.model.Tap;
import au.co.tripfare.model.Trip;
import au.co.tripfare.repository.FareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.DateUtils;
import utils.TripStatus;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class FareServiceImpl implements FareService {
    private  static final int ROUNDTRIPTIME = 20; //estimated round trip on bus to get back to same stop

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
            unprocessedTrip.setEndTime(DateUtils.convertToLocalDateTimeViaInstant(tap.getTapTime()));
            incompleteTrips.remove(unprocessedTrip);
        }
        return unprocessedTrip;
    }


    public Trip addtoIncompleteTrips(Tap tap) {
        return Trip.builder()
                .busId(tap.getBusId())
                .companyId(tap.getCompanyId())
                .pan(tap.getPan())
                .FromStop(tap.getStopId()).startTime(DateUtils.convertToLocalDateTimeViaInstant(tap.getTapTime()))
                .tripStatus(TripStatus.INCOMPLETE)
                .build();


    }


    private TripStatus checkTripStatus(Trip fromStop, Tap toStop) {
        TripStatus status = TripStatus.COMPLETED;
        if (fromStop.getFromStop().equals(toStop.getStopId())) {
            if (ChronoUnit.MINUTES.between(DateUtils.convertToLocalDateTimeViaInstant(toStop.getTapTime()),fromStop.getStartTime()) < ROUNDTRIPTIME)
                status = TripStatus.CANCELLED;
        }
        return status;
    }


    private double calculateFare(String fromStop, String toStop, TripStatus tripStatus) {
        var fareValue = 0.0;
        if ((tripStatus.equals(TripStatus.COMPLETED)) && (fromStop.equals(toStop))){
            fareValue = fareRepository.findMaxFare().getFareValue();
        } else if ((tripStatus.equals(TripStatus.COMPLETED)) && !Objects.equals(fromStop, toStop)) {
            fareValue = fareRepository. findByFromStopANDToStop(fromStop, toStop).get(0).getFareValue();
        }
        return fareValue;
    }


}