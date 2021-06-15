package au.co.tripfare;

import au.co.tripfare.entities.Tap;
import au.co.tripfare.entities.Trip;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class TripfareApplication {


    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        SpringApplication.run(TripfareApplication.class, args);
        String inputfile = "~/taps.csv";
        String outputfile = "~/taps.csv";
        TripManager tripManager = new TripManager();
        List<Tap> taps = TripFileProcessor.readTapsFromFile(inputfile);
        System.out.println("size of the taps list:" + taps.size());
        List<Trip> trips = tripManager.processTripData(taps);
        System.out.println("size of the trips list:" + trips.size());
        TripFileProcessor.streamTripsToFile(trips, outputfile);


    }

}
