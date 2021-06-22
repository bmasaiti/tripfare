package au.co.tripfare;

import au.co.tripfare.model.Tap;
import au.co.tripfare.model.Trip;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
public class TripfareApplication {
    private static String sourceFile = "";
    private static String outputFile = "";
    @Autowired
    TripManager tripManager;

    public static void main(String[] args) {
        sourceFile = args[0];
        outputFile = args[1];
        SpringApplication.run(TripfareApplication.class, args);

        System.exit(0);

    }

    @Bean
    public CommandLineRunner CommandlineRunnerBean() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        return (args) -> {
            var inputstream = new FileInputStream(args[0]);
            var outputfile = new File(args[1]);
            List<Tap> taps = TripFileProcessor.readTapsFromFile(inputstream);
            List<Trip> trips = tripManager.processTripData(taps);
            TripFileProcessor.streamTripsToFile(trips, outputfile);

        };


    }
}
