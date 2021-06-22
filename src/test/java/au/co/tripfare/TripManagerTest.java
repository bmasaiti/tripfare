package au.co.tripfare;

import au.co.tripfare.model.Tap;
import au.co.tripfare.model.Trip;
import au.co.tripfare.repository.FareRepository;
import au.co.tripfare.service.FareService;
import au.co.tripfare.service.FareServiceImpl;
import au.co.tripfare.utils.TapType;
import au.co.tripfare.utils.TripStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@Disabled
@RunWith(MockitoJUnitRunner.class)
class TripManagerTest {

    FareRepository fareRepositoryMock = mock(FareRepository.class, withSettings().stubOnly());
    FareServiceImpl fareServiceMock = mock(FareServiceImpl.class, withSettings().stubOnly());
    
    @InjectMocks
    TripManager tripManager = new TripManager(fareRepositoryMock, fareServiceMock);

//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    public void shouldReturnAListOfTripsWhenCalledWithAListOfTaps() {

        String stop1 = RandomStringUtils.randomAlphabetic(10);
        String stop2 = RandomStringUtils.randomAlphabetic(9);
        String companyId = RandomStringUtils.randomAlphabetic(8);
        String busId = RandomStringUtils.randomAlphabetic(7);
        String pan = RandomStringUtils.randomAlphabetic(6);
        Tap tap1 = new Tap();
        tap1.setStopId(stop1);
        tap1.setTapType(TapType.ON.toString());
        tap1.setPan(pan);
        tap1.setCompanyId(companyId);
        tap1.setBusId(busId);
        tap1.setTapTime(Date.from(Instant.now()));
        List<Tap> taps = new ArrayList<>();
        taps.add(tap1);

        var trip1 = Trip.builder()
                .busId(busId)
                .companyId(companyId)
                .pan(pan)
                .tripStatus(TripStatus.COMPLETED)
                .ChargeAmount(5.63)
                .FromStop(stop1)
                .toStop(stop2)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .build();
        doReturn(trip1).when(fareServiceMock).processTap(anyObject(), anyList());
       // when(fareRepositoryMock.findByMaxFareValueFromStop(any())).thenReturn(5.63);

        doReturn(5.63).when(fareRepositoryMock).findByMaxFareValueFromStop(anyString());
       // when(fareService.processTap(anyObject(), anyList())).thenReturn(trip1);

        var finalisedTrips = tripManager.processTripData(taps);
        assertNotNull(finalisedTrips);


    }
}
