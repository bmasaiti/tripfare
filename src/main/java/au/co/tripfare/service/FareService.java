package au.co.tripfare.service;

import au.co.tripfare.model.Tap;
import au.co.tripfare.model.Trip;

import java.util.List;


public interface FareService {

    Trip processTap(Tap tap, List<Trip> incompleteTrips);

    Trip addtoIncompleteTrips(Tap tap);
}
