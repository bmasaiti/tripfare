package au.co.tripfare;


import au.co.tripfare.entities.Fare;
import au.co.tripfare.model.Tap;
import au.co.tripfare.model.Trip;
import au.co.tripfare.repository.FareRepository;
import au.co.tripfare.service.FareService;
import au.co.tripfare.service.FareServiceImpl;
import au.co.tripfare.utils.TapType;
import au.co.tripfare.utils.TripStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

 @RunWith(MockitoJUnitRunner.class)
class FareServiceTest {
    FareRepository fareRepositoryMock = mock(FareRepository.class, withSettings().stubOnly());


    @InjectMocks
    FareService fareService = new FareServiceImpl(fareRepositoryMock);

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldReturnCompletedTripsWhenCalledTapOnandTapOffForSamePAn() {

        String stop1 = RandomStringUtils.randomAlphabetic(10);
        String stop2 = RandomStringUtils.randomAlphabetic(9);
        String companyId = RandomStringUtils.randomAlphabetic(8);
        String busId = RandomStringUtils.randomAlphabetic(7);
        String pan = RandomStringUtils.randomAlphabetic(6);

        List<Trip> incompleteTrips = new ArrayList<>();


        Tap tapOn = new Tap();
        tapOn.setBusId(busId);
        tapOn.setCompanyId(companyId);
        tapOn.setPan(pan);
        tapOn.setStopId(stop1);
        tapOn.setTapTime(Date.from(Instant.now()));
        tapOn.setTapType(TapType.ON.name());
        Tap tapOff = new Tap();
        tapOff.setBusId(busId);
        tapOff.setStopId(stop2);
        tapOff.setTapTime(Date.from(Instant.now()));
        tapOff.setCompanyId(companyId);
        tapOff.setPan(pan);
        tapOff.setTapType(TapType.OFF.name());

        Fare dummyFare = new Fare();
        dummyFare.setFareValue(5.60);
        dummyFare.setFromStop(stop1);
        dummyFare.setToStop(stop2);
        dummyFare.setId(1);

        when(fareRepositoryMock.findFirstByFromStopAndToStop(anyString(), anyString())).thenReturn(dummyFare);
        incompleteTrips.add(fareService.addtoIncompleteTrips(tapOn));
        var trip = fareService.processTap(tapOff, incompleteTrips);
        assertNotNull(trip);
        assertEquals(TripStatus.COMPLETED, trip.getTripStatus());
        assertEquals(5.60, trip.getChargeAmount());
        assertEquals(stop1, trip.getFromStop());
        assertEquals(stop2, trip.getToStop());
        assertEquals(pan, trip.getPan());


    }


}



  