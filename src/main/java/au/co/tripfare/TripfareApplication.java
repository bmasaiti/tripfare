package au.co.tripfare;

import au.co.tripfare.model.Tap;
import au.co.tripfare.model.Trip;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class TripfareApplication {


    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        SpringApplication.run(TripfareApplication.class, args);
       // File inputFile = new File("/src/main/resources/input/taps.csv") ;
        String inputfile = "src/main/resources/input/taps.csv";
        String outputfile = "src/main/resources/output/taps.csv";
        TripManager tripManager = new TripManager();
        List<Tap> taps = TripFileProcessor.readTapsFromFile(inputfile);
        System.out.println("size of the taps list:" + taps.size());
        List<Trip> trips = tripManager.processTripData(taps);
        System.out.println("size of the trips list:" + trips.size());
        TripFileProcessor.streamTripsToFile(trips, outputfile);


    }

}
