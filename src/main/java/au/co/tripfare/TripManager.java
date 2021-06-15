package au.co.tripfare;

import au.co.tripfare.entities.Tap;
import au.co.tripfare.entities.Trip;
import au.co.tripfare.repository.FareRepository;
import au.co.tripfare.service.FareService;
import au.co.tripfare.service.FareServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.TapType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TripManager {
    @Autowired
    FareRepository fareRepository;

    public List<Trip> processTripData(List<Tap> tapsData) {
        FareService fareService = new FareServiceImpl();
        List<Trip> incompleteTrips = new ArrayList<>();
        List<Trip> processedTrips = new ArrayList<>();
        tapsData.stream().forEach(

                tap -> {
                    if ((tap.getTapType().trim().equalsIgnoreCase((TapType.ON.name())))) {
                        incompleteTrips.add(fareService.addtoIncompleteTrips(tap));
                    } else {
                        processedTrips.add(fareService.processTap(tap, incompleteTrips));
                    }
                });
        for (var trip : incompleteTrips
        ) {
            trip.setChargeAmount(fareRepository.getMaxFareFromStop(trip.getFromStop()).
                    getFareValue());
        }

        var finalisedTrips = Stream.concat(processedTrips.stream(), incompleteTrips.stream()).collect(Collectors.toList());
        return finalisedTrips;
    }

}
