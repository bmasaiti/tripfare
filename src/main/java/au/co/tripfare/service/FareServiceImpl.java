package au.co.tripfare.service;


import au.co.tripfare.model.Tap;
import au.co.tripfare.model.Trip;
import au.co.tripfare.repository.FareRepository;
import au.co.tripfare.utils.DateUtils;
import au.co.tripfare.utils.TripStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class FareServiceImpl implements FareService {
    private static final Logger LOG = LoggerFactory.getLogger(FareServiceImpl.class);

    private static final int ROUNDTRIPTIME = 20; //estimated round trip on bus to get back to same stop

    private final FareRepository fareRepository;

    public FareServiceImpl(FareRepository fareRepository) {
        this.fareRepository = fareRepository;
    }


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
        if (fromStop.getFromStop().equals(toStop.getStopId())
                && (ChronoUnit.MINUTES.between(DateUtils.convertToLocalDateTimeViaInstant(toStop.getTapTime()), fromStop.getStartTime())
                < ROUNDTRIPTIME)) {
            // if (ChronoUnit.MINUTES.between(DateUtils.convertToLocalDateTimeViaInstant(toStop.getTapTime()), fromStop.getStartTime()) < ROUNDTRIPTIME)
            status = TripStatus.CANCELLED;
        }
        return status;
    }


    private double calculateFare(String fromStop, String toStop, TripStatus tripStatus) {
        var fareValue = 0.0;
        try {
            if ((tripStatus.equals(TripStatus.COMPLETED)) && (fromStop.equals(toStop))) {
                fareValue = fareRepository.findByMaxFareValue();
            } else if ((tripStatus.equals(TripStatus.COMPLETED))) {
                var farr = fareRepository.findFirstByFromStopAndToStop(fromStop, toStop);
                fareValue = farr.getFareValue();
            }
        } catch (NullPointerException ex) {
            LOG.error("Fare not found in db", ex.getLocalizedMessage());
            ex.getLocalizedMessage();
        }
        return fareValue;
    }

}