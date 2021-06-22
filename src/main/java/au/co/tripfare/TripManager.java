package au.co.tripfare;

import au.co.tripfare.model.Tap;
import au.co.tripfare.model.Trip;
import au.co.tripfare.repository.FareRepository;
import au.co.tripfare.service.FareService;
import au.co.tripfare.utils.TapType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TripManager {

    private final FareRepository fareRepository;
    private final FareService fareService;


    public TripManager(FareRepository fareRepository, FareService fareService) {
        this.fareRepository = fareRepository;
        this.fareService = fareService;
    }


    public List<Trip> processTripData(List<Tap> tapsData) {
        List<Trip> incompleteTrips = new ArrayList<>();
        List<Trip> processedTrips = new ArrayList<>();
        tapsData.stream().forEach(

                tap -> {
                    if ((tap.getTapType().trim().equalsIgnoreCase((TapType.ON.name())))) {
                        var trip = fareService.addtoIncompleteTrips(tap);
                        incompleteTrips.add(trip);
                    } else {
                        processedTrips.add(fareService.processTap(tap, incompleteTrips));
                    }
                });
        for (var trip : incompleteTrips
        ) {
            var fv = fareRepository.findByMaxFareValueFromStop(trip.getFromStop().trim().toLowerCase());
            trip.setChargeAmount(fv);
        }

        var finalisedTrips = Stream.concat(processedTrips.stream(), incompleteTrips.stream()).collect(Collectors.toList());
        return finalisedTrips;
    }

}
