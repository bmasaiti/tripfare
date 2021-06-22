package au.co.tripfare;

import au.co.tripfare.repository.FareRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@Disabled
@SpringBootTest(args = {"/Users/oathkeeper/DEV/tripfare/src/test/resources/input_taps.csv/", "/Users/oathkeeper/DEV/tripfare/src/test/resources/outtput_taps.csv"})
public class FareRepositoryIntergrationTest {

    @Autowired
    CommandLineRunner runner;

    @Autowired
    private FareRepository fareRepository;


//    @BeforeEach
//    void setup() throws Exception {
//        runner.run (  "/Users/oathkeeper/DEV/tripfare/src/test/resources/input_taps.csv/","/Users/oathkeeper/DEV/tripfare/src/test/resources/input_taps.csv") ;
//
//    }


    @Test
    public void retrieveFareValueForIncompleteTrip() throws Exception {
        var fareValue = fareRepository.findByMaxFareValue();
        assertNotNull(fareValue);
        //assertEquals();
    }


    @Test
    public void retrieveFareValueForReturnTripToStation() {
        var fareValue = fareRepository.findByMaxFareValueFromStop("stop1");
        assertNotNull(fareValue);
        //assertEquals();
    }

    @Test
    public void retrieveFareValueFromStopToAnother() {
        var fareValue = fareRepository.findFirstByFromStopAndToStop("stop1", "stop2");
        assertNotNull(fareValue);
        //assertEquals();
    }

}


